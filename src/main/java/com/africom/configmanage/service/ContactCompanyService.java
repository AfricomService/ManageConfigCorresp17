package com.africom.configmanage.service;

import com.africom.configmanage.domain.ContactCompany;
import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.ContactCompanyRepository;
import com.africom.configmanage.repository.PersonCompanyRepository;
import com.africom.configmanage.service.dto.ContactCompanyDTO;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.service.mapper.ContactCompanyMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.africom.configmanage.service.mapper.PersonCompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactCompany}.
 */
@Service
@Transactional
public class ContactCompanyService {

    private final Logger log = LoggerFactory.getLogger(ContactCompanyService.class);

    private final ContactCompanyRepository contactCompanyRepository;

    private final ContactCompanyMapper contactCompanyMapper;

    private final PersonCompanyRepository personCompanyRepository;
    private final PersonCompanyMapper personCompanyMapper;

    public ContactCompanyService(ContactCompanyRepository contactCompanyRepository, ContactCompanyMapper contactCompanyMapper, PersonCompanyRepository personCompanyRepository,
                                 PersonCompanyMapper personCompanyMapper) {
        this.contactCompanyRepository = contactCompanyRepository;
        this.contactCompanyMapper = contactCompanyMapper;
        this.personCompanyRepository = personCompanyRepository;
        this.personCompanyMapper = personCompanyMapper;
    }

    /**
     * Save a contactCompany.
     *
     * @param contactCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactCompanyDTO save(ContactCompanyDTO contactCompanyDTO) {
        log.debug("Request to save ContactCompany : {}", contactCompanyDTO);
        ContactCompany contactCompany = contactCompanyMapper.toEntity(contactCompanyDTO);
        contactCompany = contactCompanyRepository.save(contactCompany);
        return contactCompanyMapper.toDto(contactCompany);
    }

    /**
     * Update a contactCompany.
     *
     * @param contactCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactCompanyDTO update(ContactCompanyDTO contactCompanyDTO) {
        log.debug("Request to update ContactCompany : {}", contactCompanyDTO);
        ContactCompany contactCompany = contactCompanyMapper.toEntity(contactCompanyDTO);
        contactCompany = contactCompanyRepository.save(contactCompany);
        return contactCompanyMapper.toDto(contactCompany);
    }

    /**
     * Partially update a contactCompany.
     *
     * @param contactCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContactCompanyDTO> partialUpdate(ContactCompanyDTO contactCompanyDTO) {
        log.debug("Request to partially update ContactCompany : {}", contactCompanyDTO);

        return contactCompanyRepository
            .findById(contactCompanyDTO.getId())
            .map(existingContactCompany -> {
                contactCompanyMapper.partialUpdate(existingContactCompany, contactCompanyDTO);

                return existingContactCompany;
            })
            .map(contactCompanyRepository::save)
            .map(contactCompanyMapper::toDto);
    }

    /**
     * Get all the contactCompanies (non filtré).
     *
     * @param pageable the pagination information.
     * @return the page of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactCompanies");
        return contactCompanyRepository.findAll(pageable).map(contactCompanyMapper::toDto);
    }

    /**
     * Get all the contactCompanies whose etat != Delated.
     *
     * @param pageable the pagination information.
     * @return the page of entities (only active / non Delated).
     */
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> findAllActive(Pageable pageable) {
        log.debug("Request to get all active (non-Delated) ContactCompanies");
        return contactCompanyRepository.findAllByEtatNot(UsualStatus.Delated, pageable).map(contactCompanyMapper::toDto);
    }

    /**
     * Get one contactCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactCompanyDTO> findOne(Long id) {
        log.debug("Request to get ContactCompany : {}", id);
        return contactCompanyRepository.findById(id).map(contactCompanyMapper::toDto);
    }

    /**
     * Delete the contactCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactCompany : {}", id);
        contactCompanyRepository.deleteById(id);
    }

    /**
     * Récupère la liste paginée des PersonCompany liés à un ContactCompany (par id).
     *
     * @param contactCompanyId l'id du ContactCompany
     * @param pageable pagination
     * @return page de PersonCompanyDTO
     */
    @Transactional(readOnly = true)
    public Page<PersonCompanyDTO> findPersonCompaniesByContactCompanyId(Long contactCompanyId, Pageable pageable) {
        log.debug("Request to get PersonCompanies by ContactCompany id : {}", contactCompanyId);
        return personCompanyRepository.findByContactCompanyId(contactCompanyId, pageable)
            .map(personCompanyMapper::toDto);
    }

    /**
     * Variante non paginée (List)
     */
    @Transactional(readOnly = true)
    public List<PersonCompanyDTO> findPersonCompaniesByContactCompanyId(Long contactCompanyId) {
        log.debug("Request to get PersonCompanies (list) by ContactCompany id : {}", contactCompanyId);
        return personCompanyRepository.findByContactCompanyId(contactCompanyId)
            .stream()
            .map(personCompanyMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ContactCompanyDTO> markAsDelatedWithPersons(Long contactCompanyId) {
        log.debug("Request to mark ContactCompany (id={}) as Delated and its PersonCompany as Delated", contactCompanyId);

        return contactCompanyRepository.findById(contactCompanyId)
            .map(contactCompany -> {
                // mettre à jour l'état du ContactCompany
                contactCompany.setEtat(UsualStatus.Delated);

                // récupérer toutes les personnes liées et les marquer Delated
                List<PersonCompany> persons = personCompanyRepository.findByContactCompanyId(contactCompanyId);
                if (persons != null && !persons.isEmpty()) {
                    persons.forEach(p -> p.setEtat(UsualStatus.Delated));
                    personCompanyRepository.saveAll(persons);
                }

                // sauvegarder le contact et renvoyer le DTO
                ContactCompany saved = contactCompanyRepository.save(contactCompany);
                return contactCompanyMapper.toDto(saved);
            });
    }
}
