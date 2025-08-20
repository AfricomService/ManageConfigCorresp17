package com.africom.configmanage.service;

import com.africom.configmanage.domain.Numsequentielle;
import com.africom.configmanage.repository.NumsequentielleRepository;
import com.africom.configmanage.service.dto.NumsequentielleDTO;
import com.africom.configmanage.service.mapper.NumsequentielleMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Numsequentielle}.
 */
@Service
@Transactional
public class NumsequentielleService {

    private final Logger log = LoggerFactory.getLogger(NumsequentielleService.class);

    private final NumsequentielleRepository numsequentielleRepository;

    private final NumsequentielleMapper numsequentielleMapper;

    public NumsequentielleService(NumsequentielleRepository numsequentielleRepository, NumsequentielleMapper numsequentielleMapper) {
        this.numsequentielleRepository = numsequentielleRepository;
        this.numsequentielleMapper = numsequentielleMapper;
    }

    /**
     * Save a numsequentielle.
     *
     * @param numsequentielleDTO the entity to save.
     * @return the persisted entity.
     */
    public NumsequentielleDTO save(NumsequentielleDTO numsequentielleDTO) {
        log.debug("Request to save Numsequentielle : {}", numsequentielleDTO);
        Numsequentielle numsequentielle = numsequentielleMapper.toEntity(numsequentielleDTO);
        numsequentielle = numsequentielleRepository.save(numsequentielle);
        return numsequentielleMapper.toDto(numsequentielle);
    }

    /**
     * Update a numsequentielle.
     *
     * @param numsequentielleDTO the entity to save.
     * @return the persisted entity.
     */
    public NumsequentielleDTO update(NumsequentielleDTO numsequentielleDTO) {
        log.debug("Request to update Numsequentielle : {}", numsequentielleDTO);
        Numsequentielle numsequentielle = numsequentielleMapper.toEntity(numsequentielleDTO);
        numsequentielle = numsequentielleRepository.save(numsequentielle);
        return numsequentielleMapper.toDto(numsequentielle);
    }

    /**
     * Partially update a numsequentielle.
     *
     * @param numsequentielleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NumsequentielleDTO> partialUpdate(NumsequentielleDTO numsequentielleDTO) {
        log.debug("Request to partially update Numsequentielle : {}", numsequentielleDTO);

        return numsequentielleRepository
            .findById(numsequentielleDTO.getId())
            .map(existingNumsequentielle -> {
                numsequentielleMapper.partialUpdate(existingNumsequentielle, numsequentielleDTO);

                return existingNumsequentielle;
            })
            .map(numsequentielleRepository::save)
            .map(numsequentielleMapper::toDto);
    }

    /**
     * Get all the numsequentielles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NumsequentielleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Numsequentielles");
        return numsequentielleRepository.findAll(pageable).map(numsequentielleMapper::toDto);
    }

    /**
     * Get one numsequentielle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NumsequentielleDTO> findOne(UUID id) {
        log.debug("Request to get Numsequentielle : {}", id);
        return numsequentielleRepository.findById(id).map(numsequentielleMapper::toDto);
    }

    /**
     * Delete the numsequentielle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Numsequentielle : {}", id);
        numsequentielleRepository.deleteById(id);
    }
}
