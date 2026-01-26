package com.africom.configmanage.service;

import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.ContactCompanyRepository;
import com.africom.configmanage.repository.PersonCompanyRepository;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.service.mapper.PersonCompanyMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service Implementation for managing {@link PersonCompany}.
 */
@Service
@Transactional
public class PersonCompanyService {

    private final Logger log = LoggerFactory.getLogger(PersonCompanyService.class);

    private final PersonCompanyRepository personCompanyRepository;

    private final PersonCompanyMapper personCompanyMapper;
    private final ContactCompanyRepository contactCompanyRepository;


    public PersonCompanyService(PersonCompanyRepository personCompanyRepository, PersonCompanyMapper personCompanyMapper, ContactCompanyRepository contactCompanyRepository) {
        this.personCompanyRepository = personCompanyRepository;
        this.personCompanyMapper = personCompanyMapper;
        this.contactCompanyRepository = contactCompanyRepository;
    }

    /**
     * Save a personCompany.
     *
     * @param personCompanyDTO the entity to save.
     * @return the persisted entity.
     */
//    public PersonCompanyDTO save(PersonCompanyDTO personCompanyDTO) {
//        log.debug("Request to save PersonCompany : {}", personCompanyDTO);
//        PersonCompany personCompany = personCompanyMapper.toEntity(personCompanyDTO);
//        personCompany = personCompanyRepository.save(personCompany);
//        return personCompanyMapper.toDto(personCompany);
//    }
    public PersonCompanyDTO save(PersonCompanyDTO personCompanyDTO) {
        log.debug("Request to save PersonCompany : {}", personCompanyDTO);

        // Validation: si un contactCompanyId est fourni, vérifier qu'il existe
        if (personCompanyDTO.getContactCompanyId() != null) {
            Long ccId = personCompanyDTO.getContactCompanyId();
            boolean exists = contactCompanyRepository.existsById(ccId);
            if (!exists) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ContactCompany not found with id: " + ccId
                );
            }
        }

        PersonCompany personCompany = personCompanyMapper.toEntity(personCompanyDTO);

        // Forcer l'état Actif uniquement pour une création (id == null)
        if (personCompany.getId() == null) {
            personCompany.setEtat(UsualStatus.Actif);
        }

        personCompany = personCompanyRepository.save(personCompany);
        return personCompanyMapper.toDto(personCompany);
    }


    /**
     * Update a personCompany.
     *
     * @param personCompanyDTO the entity to save.
     * @return the persisted entity.
     */
//    public PersonCompanyDTO update(PersonCompanyDTO personCompanyDTO) {
//        log.debug("Request to update PersonCompany : {}", personCompanyDTO);
//        PersonCompany personCompany = personCompanyMapper.toEntity(personCompanyDTO);
//        personCompany = personCompanyRepository.save(personCompany);
//        return personCompanyMapper.toDto(personCompany);
//    }
    public PersonCompanyDTO update(PersonCompanyDTO personCompanyDTO) {
        log.debug("Request to update PersonCompany : {}", personCompanyDTO);

        if (personCompanyDTO.getContactCompanyId() != null) {
            Long ccId = personCompanyDTO.getContactCompanyId();
            boolean exists = contactCompanyRepository.existsById(ccId);
            if (!exists) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ContactCompany not found with id: " + ccId
                );
            }
        }

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
//    public Optional<PersonCompanyDTO> partialUpdate(PersonCompanyDTO personCompanyDTO) {
//        log.debug("Request to partially update PersonCompany : {}", personCompanyDTO);
//
//        return personCompanyRepository
//            .findById(personCompanyDTO.getId())
//            .map(existingPersonCompany -> {
//                personCompanyMapper.partialUpdate(existingPersonCompany, personCompanyDTO);
//
//                return existingPersonCompany;
//            })
//            .map(personCompanyRepository::save)
//            .map(personCompanyMapper::toDto);
//    }
    public Optional<PersonCompanyDTO> partialUpdate(PersonCompanyDTO personCompanyDTO) {
        log.debug("Request to partially update PersonCompany : {}", personCompanyDTO);

        return personCompanyRepository
            .findById(personCompanyDTO.getId())
            .map(existingPersonCompany -> {
                // validate contactCompanyId if present in DTO
                if (personCompanyDTO.getContactCompanyId() != null) {
                    Long ccId = personCompanyDTO.getContactCompanyId();
                    boolean exists = contactCompanyRepository.existsById(ccId);
                    if (!exists) {
                        throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "ContactCompany not found with id: " + ccId
                        );
                    }
                }

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

    public PersonCompanyDTO markAsDeleted(Long id) {
        log.debug("Request to mark PersonCompany as Delated : {}", id);

        return personCompanyRepository.findById(id).map(personCompany -> {
            // Si déjà Delated, on ne fait que retourner le DTO
            if (personCompany.getEtat() != UsualStatus.Delated) {
                personCompany.setEtat(UsualStatus.Delated);
                personCompany = personCompanyRepository.save(personCompany);
            }
            return personCompanyMapper.toDto(personCompany);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PersonCompany not found with id: " + id));
    }

    public PersonCompanyDTO toggleEtat(Long id) {
        log.debug("Request to toggle etat for PersonCompany : {}", id);

        return personCompanyRepository.findById(id)
            .map(personCompany -> {
                UsualStatus current = personCompany.getEtat();

                if (current == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PersonCompany has no state to toggle");
                }

                if (current == UsualStatus.Actif) {
                    personCompany.setEtat(UsualStatus.Desactivated);
                } else if (current == UsualStatus.Desactivated) {
                    personCompany.setEtat(UsualStatus.Actif);
                } else {
                    // Delated (ou autre valeur) — on ne togglera pas
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot toggle state for Delated entity");
                }

                personCompany = personCompanyRepository.save(personCompany);
                return personCompanyMapper.toDto(personCompany);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PersonCompany not found with id: " + id));
    }
}
