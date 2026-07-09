package com.africom.configmanage.service;

import com.africom.configmanage.domain.DetailInfo;
import com.africom.configmanage.repository.DetailInfoRepository;
import com.africom.configmanage.service.dto.DetailInfoDTO;
import com.africom.configmanage.service.mapper.DetailInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetailInfo}.
 */
@Service
@Transactional
public class DetailInfoService {

    private final Logger log = LoggerFactory.getLogger(DetailInfoService.class);

    private final DetailInfoRepository detailInfoRepository;

    private final DetailInfoMapper detailInfoMapper;

    public DetailInfoService(DetailInfoRepository detailInfoRepository, DetailInfoMapper detailInfoMapper) {
        this.detailInfoRepository = detailInfoRepository;
        this.detailInfoMapper = detailInfoMapper;
    }

    /**
     * Save a detailInfo.
     *
     * @param detailInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public DetailInfoDTO save(DetailInfoDTO detailInfoDTO) {
        log.debug("Request to save DetailInfo : {}", detailInfoDTO);
        DetailInfo detailInfo = detailInfoMapper.toEntity(detailInfoDTO);
        detailInfo = detailInfoRepository.save(detailInfo);
        return detailInfoMapper.toDto(detailInfo);
    }

    /**
     * Partially update a detailInfo.
     *
     * @param detailInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetailInfoDTO> partialUpdate(DetailInfoDTO detailInfoDTO) {
        log.debug("Request to partially update DetailInfo : {}", detailInfoDTO);

        return detailInfoRepository
            .findById(detailInfoDTO.getId())
            .map(existingDetailInfo -> {
                detailInfoMapper.partialUpdate(existingDetailInfo, detailInfoDTO);

                return existingDetailInfo;
            })
            .map(detailInfoRepository::save)
            .map(detailInfoMapper::toDto);
    }

    /**
     * Get all the detailInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetailInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailInfos");
        return detailInfoRepository.findAll(pageable).map(detailInfoMapper::toDto);
    }

    /**
     * Get one detailInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetailInfoDTO> findOne(Long id) {
        log.debug("Request to get DetailInfo : {}", id);
        return detailInfoRepository.findById(id).map(detailInfoMapper::toDto);
    }

    /**
     * Delete the detailInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetailInfo : {}", id);
        detailInfoRepository.deleteById(id);
    }
}
