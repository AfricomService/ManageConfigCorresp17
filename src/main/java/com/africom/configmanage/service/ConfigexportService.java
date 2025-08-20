package com.africom.configmanage.service;

import com.africom.configmanage.domain.Configexport;
import com.africom.configmanage.repository.ConfigexportRepository;
import com.africom.configmanage.service.dto.ConfigexportDTO;
import com.africom.configmanage.service.mapper.ConfigexportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Configexport}.
 */
@Service
@Transactional
public class ConfigexportService {

    private final Logger log = LoggerFactory.getLogger(ConfigexportService.class);

    private final ConfigexportRepository configexportRepository;

    private final ConfigexportMapper configexportMapper;

    public ConfigexportService(ConfigexportRepository configexportRepository, ConfigexportMapper configexportMapper) {
        this.configexportRepository = configexportRepository;
        this.configexportMapper = configexportMapper;
    }

    /**
     * Save a configexport.
     *
     * @param configexportDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfigexportDTO save(ConfigexportDTO configexportDTO) {
        log.debug("Request to save Configexport : {}", configexportDTO);
        Configexport configexport = configexportMapper.toEntity(configexportDTO);
        configexport = configexportRepository.save(configexport);
        return configexportMapper.toDto(configexport);
    }

    /**
     * Update a configexport.
     *
     * @param configexportDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfigexportDTO update(ConfigexportDTO configexportDTO) {
        log.debug("Request to update Configexport : {}", configexportDTO);
        Configexport configexport = configexportMapper.toEntity(configexportDTO);
        configexport = configexportRepository.save(configexport);
        return configexportMapper.toDto(configexport);
    }

    /**
     * Partially update a configexport.
     *
     * @param configexportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConfigexportDTO> partialUpdate(ConfigexportDTO configexportDTO) {
        log.debug("Request to partially update Configexport : {}", configexportDTO);

        return configexportRepository
            .findById(configexportDTO.getId())
            .map(existingConfigexport -> {
                configexportMapper.partialUpdate(existingConfigexport, configexportDTO);

                return existingConfigexport;
            })
            .map(configexportRepository::save)
            .map(configexportMapper::toDto);
    }

    /**
     * Get all the configexports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigexportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Configexports");
        return configexportRepository.findAll(pageable).map(configexportMapper::toDto);
    }

    /**
     * Get one configexport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfigexportDTO> findOne(Long id) {
        log.debug("Request to get Configexport : {}", id);
        return configexportRepository.findById(id).map(configexportMapper::toDto);
    }

    /**
     * Delete the configexport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Configexport : {}", id);
        configexportRepository.deleteById(id);
    }
}
