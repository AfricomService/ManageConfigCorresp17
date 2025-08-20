package com.africom.configmanage.service;

import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.repository.PersonCompanyRepository;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.service.mapper.PersonCompanyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonCompany}.
 */
@Service
@Transactional
public class PersonCompanyService {

    private final Logger log = LoggerFactory.getLogger(PersonCompanyService.class);

    private final PersonCompanyRepository personCompanyRepository;

    private final PersonCompanyMapper personCompanyMapper;

    public PersonCompanyService(PersonCompanyRepository personCompanyRepository, PersonCompanyMapper personCompanyMapper) {
        this.personCompanyRepository = personCompanyRepository;
        this.personCompanyMapper = personCompanyMapper;
    }

    /**
     * Save a personCompany.
     *
     * @param personCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonCompanyDTO save(PersonCompanyDTO personCompanyDTO) {
        log.debug("Request to save PersonCompany : {}", personCompanyDTO);
        PersonCompany personCompany = personCompanyMapper.toEntity(personCompanyDTO);
        personCompany = personCompanyRepository.save(personCompany);
        return personCompanyMapper.toDto(personCompany);
    }

    /**
     * Update a personCompany.
     *
     * @param personCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonCompanyDTO update(PersonCompanyDTO personCompanyDTO) {
        log.debug("Request to update PersonCompany : {}", personCompanyDTO);
        PersonCompany personCompany = personCompanyMapper.toEntity(personCompanyDTO);
        personCompany = personCompanyRepository.save(personCompany);
        return personCompanyMapper.toDto(personCompany);
    }

    /**
     * Partially update a personCompany.
     *
     * @param personCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonCompanyDTO> partialUpdate(PersonCompanyDTO personCompanyDTO) {
        log.debug("Request to partially update PersonCompany : {}", personCompanyDTO);

        return personCompanyRepository
            .findById(personCompanyDTO.getId())
            .map(existingPersonCompany -> {
                personCompanyMapper.partialUpdate(existingPersonCompany, personCompanyDTO);

                return existingPersonCompany;
            })
            .map(personCompanyRepository::save)
            .map(personCompanyMapper::toDto);
    }

    /**
     * Get all the personCompanies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonCompanies");
        return personCompanyRepository.findAll(pageable).map(personCompanyMapper::toDto);
    }

    /**
     * Get one personCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonCompanyDTO> findOne(Long id) {
        log.debug("Request to get PersonCompany : {}", id);
        return personCompanyRepository.findById(id).map(personCompanyMapper::toDto);
    }

    /**
     * Delete the personCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonCompany : {}", id);
        personCompanyRepository.deleteById(id);
    }
}
