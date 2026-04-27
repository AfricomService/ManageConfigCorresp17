package com.africom.configmanage.service;

import com.africom.configmanage.domain.ModelNotif;
import com.africom.configmanage.repository.ModelNotifRepository;
import com.africom.configmanage.service.dto.ModelNotifDTO;
import com.africom.configmanage.service.mapper.ModelNotifMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModelNotif}.
 */
@Service
@Transactional
public class ModelNotifService {

    private final Logger log = LoggerFactory.getLogger(ModelNotifService.class);

    private final ModelNotifRepository modelNotifRepository;

    private final ModelNotifMapper modelNotifMapper;

    public ModelNotifService(ModelNotifRepository modelNotifRepository, ModelNotifMapper modelNotifMapper) {
        this.modelNotifRepository = modelNotifRepository;
        this.modelNotifMapper = modelNotifMapper;
    }

    /**
     * Save a modelNotif.
     *
     * @param modelNotifDTO the entity to save.
     * @return the persisted entity.
     */
    public ModelNotifDTO save(ModelNotifDTO modelNotifDTO) {
        log.debug("Request to save ModelNotif : {}", modelNotifDTO);
        ModelNotif modelNotif = modelNotifMapper.toEntity(modelNotifDTO);
        modelNotif = modelNotifRepository.save(modelNotif);
        return modelNotifMapper.toDto(modelNotif);
    }

    /**
     * Update a modelNotif.
     *
     * @param modelNotifDTO the entity to save.
     * @return the persisted entity.
     */
    public ModelNotifDTO update(ModelNotifDTO modelNotifDTO) {
        log.debug("Request to update ModelNotif : {}", modelNotifDTO);
        return modelNotifRepository
            .findById(modelNotifDTO.getId())
            .map(existingModelNotif -> {
                modelNotifMapper.partialUpdate(existingModelNotif, modelNotifDTO);
                // Force explicite de saveCopy car MapStruct peut l'ignorer si null->Boolean
                existingModelNotif.setSaveCopy(modelNotifDTO.getSaveCopy());
                return existingModelNotif;
            })
            .map(modelNotifRepository::save)
            .map(modelNotifMapper::toDto)
            .orElseThrow(() -> new RuntimeException("ModelNotif not found"));
    }

    /**
     * Partially update a modelNotif.
     *
     * @param modelNotifDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ModelNotifDTO> partialUpdate(ModelNotifDTO modelNotifDTO) {
        log.debug("Request to partially update ModelNotif : {}", modelNotifDTO);

        return modelNotifRepository
            .findById(modelNotifDTO.getId())
            .map(existingModelNotif -> {
                modelNotifMapper.partialUpdate(existingModelNotif, modelNotifDTO);

                return existingModelNotif;
            })
            .map(modelNotifRepository::save)
            .map(modelNotifMapper::toDto);
    }

    /**
     * Get all the modelNotifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
//    @Transactional(readOnly = true)
//    public Page<ModelNotifDTO> findAll(Pageable pageable) {
//        log.debug("Request to get all ModelNotifs");
//        return modelNotifRepository.findAll(pageable).map(modelNotifMapper::toDto);
//    }
    @Transactional(readOnly = true)
    public Page<ModelNotifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModelNotifs");
        return modelNotifRepository
            .findByFamilleIgnoreCase("email", pageable)  // ← filtre ici
            .map(modelNotifMapper::toDto);
    }

    /**
     * Get one modelNotif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModelNotifDTO> findOne(UUID id) {
        log.debug("Request to get ModelNotif : {}", id);
        return modelNotifRepository.findById(id).map(modelNotifMapper::toDto);
    }

    /**
     * Delete the modelNotif by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ModelNotif : {}", id);
        modelNotifRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ModelNotifDTO> findOneByCode(String code) {
        log.debug("Request to get ModelNotif by code : {}", code);
        return modelNotifRepository.findOneByCode(code).map(modelNotifMapper::toDto);
    }
}
