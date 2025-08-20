package com.africom.configmanage.service;

import com.africom.configmanage.domain.Myreport;
import com.africom.configmanage.repository.MyreportRepository;
import com.africom.configmanage.service.dto.MyreportDTO;
import com.africom.configmanage.service.mapper.MyreportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Myreport}.
 */
@Service
@Transactional
public class MyreportService {

    private final Logger log = LoggerFactory.getLogger(MyreportService.class);

    private final MyreportRepository myreportRepository;

    private final MyreportMapper myreportMapper;

    public MyreportService(MyreportRepository myreportRepository, MyreportMapper myreportMapper) {
        this.myreportRepository = myreportRepository;
        this.myreportMapper = myreportMapper;
    }

    /**
     * Save a myreport.
     *
     * @param myreportDTO the entity to save.
     * @return the persisted entity.
     */
    public MyreportDTO save(MyreportDTO myreportDTO) {
        log.debug("Request to save Myreport : {}", myreportDTO);
        Myreport myreport = myreportMapper.toEntity(myreportDTO);
        myreport = myreportRepository.save(myreport);
        return myreportMapper.toDto(myreport);
    }

    /**
     * Update a myreport.
     *
     * @param myreportDTO the entity to save.
     * @return the persisted entity.
     */
    public MyreportDTO update(MyreportDTO myreportDTO) {
        log.debug("Request to update Myreport : {}", myreportDTO);
        Myreport myreport = myreportMapper.toEntity(myreportDTO);
        myreport = myreportRepository.save(myreport);
        return myreportMapper.toDto(myreport);
    }

    /**
     * Partially update a myreport.
     *
     * @param myreportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MyreportDTO> partialUpdate(MyreportDTO myreportDTO) {
        log.debug("Request to partially update Myreport : {}", myreportDTO);

        return myreportRepository
            .findById(myreportDTO.getId())
            .map(existingMyreport -> {
                myreportMapper.partialUpdate(existingMyreport, myreportDTO);

                return existingMyreport;
            })
            .map(myreportRepository::save)
            .map(myreportMapper::toDto);
    }

    /**
     * Get all the myreports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MyreportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Myreports");
        return myreportRepository.findAll(pageable).map(myreportMapper::toDto);
    }

    /**
     * Get one myreport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MyreportDTO> findOne(Long id) {
        log.debug("Request to get Myreport : {}", id);
        return myreportRepository.findById(id).map(myreportMapper::toDto);
    }

    /**
     * Delete the myreport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Myreport : {}", id);
        myreportRepository.deleteById(id);
    }
}
