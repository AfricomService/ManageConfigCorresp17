package com.africom.configmanage.service;

import com.africom.configmanage.domain.Orgacare;
import com.africom.configmanage.repository.OrgacareRepository;
import com.africom.configmanage.service.dto.OrgacareCodeDTO;
import com.africom.configmanage.service.dto.OrgacareDTO;
import com.africom.configmanage.service.mapper.OrgacareMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Orgacare}.
 */
@Service
@Transactional
public class OrgacareService {

    private final Logger log = LoggerFactory.getLogger(OrgacareService.class);

    private final OrgacareRepository orgacareRepository;

    private final OrgacareMapper orgacareMapper;

    public OrgacareService(OrgacareRepository orgacareRepository, OrgacareMapper orgacareMapper) {
        this.orgacareRepository = orgacareRepository;
        this.orgacareMapper = orgacareMapper;
    }

    /**
     * Save a orgacare.
     *
     * @param orgacareDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgacareDTO save(OrgacareDTO orgacareDTO) {
        log.debug("Request to save Orgacare : {}", orgacareDTO);
        Orgacare orgacare = orgacareMapper.toEntity(orgacareDTO);
        orgacare = orgacareRepository.save(orgacare);
        return orgacareMapper.toDto(orgacare);
    }

    /**
     * Update a orgacare.
     *
     * @param orgacareDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgacareDTO update(OrgacareDTO orgacareDTO) {
        log.debug("Request to update Orgacare : {}", orgacareDTO);
        Orgacare orgacare = orgacareMapper.toEntity(orgacareDTO);
        orgacare = orgacareRepository.save(orgacare);
        return orgacareMapper.toDto(orgacare);
    }

    /**
     * Partially update a orgacare.
     *
     * @param orgacareDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrgacareDTO> partialUpdate(OrgacareDTO orgacareDTO) {
        log.debug("Request to partially update Orgacare : {}", orgacareDTO);

        return orgacareRepository
            .findById(orgacareDTO.getId())
            .map(existingOrgacare -> {
                orgacareMapper.partialUpdate(existingOrgacare, orgacareDTO);

                return existingOrgacare;
            })
            .map(orgacareRepository::save)
            .map(orgacareMapper::toDto);
    }

    /**
     * Get all the orgacares.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrgacareDTO> findAll() {
        log.debug("Request to get all Orgacares");
        return orgacareRepository.findAll().stream().map(orgacareMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one orgacare by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgacareDTO> findOne(Long id) {
        log.debug("Request to get Orgacare : {}", id);
        return orgacareRepository.findById(id).map(orgacareMapper::toDto);
    }

    /**
     * Delete the orgacare by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Orgacare : {}", id);
        orgacareRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<OrgacareDTO> findByOrgaCode(String orgaCode) {
        log.debug("Request to get Orgacare by orgaCode : {}", orgaCode);
        return orgacareRepository.findByOrgaCode(orgaCode)
            .map(orgacareMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<OrgacareCodeDTO> getAllOrganigrammesCodes() {
        log.debug("Request to get all Orgacare ids and orgaCodes");
        return orgacareRepository.findAll()
            .stream()
            .map(org -> new OrgacareCodeDTO(org.getId(), org.getOrgaCode()))
            .collect(Collectors.toList());
    }
}
