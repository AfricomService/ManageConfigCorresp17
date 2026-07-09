package com.africom.configmanage.service;

import com.africom.configmanage.domain.TypeDocDetailInfo;
import com.africom.configmanage.repository.DetailInfoRepository;
import com.africom.configmanage.repository.TypeDocDetailInfoRepository;
import com.africom.configmanage.service.dto.DetailInfoDTO;
import com.africom.configmanage.service.dto.TypeDocDetailInfoDTO;
import com.africom.configmanage.service.mapper.DetailInfoMapper;
import com.africom.configmanage.service.mapper.TypeDocDetailInfoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeDocDetailInfo}.
 */
@Service
@Transactional
public class TypeDocDetailInfoService {

    private final Logger log = LoggerFactory.getLogger(TypeDocDetailInfoService.class);

    private final TypeDocDetailInfoRepository typeDocDetailInfoRepository;

    private final TypeDocDetailInfoMapper typeDocDetailInfoMapper;

    private final DetailInfoRepository detailInfoRepository;

    private final DetailInfoMapper detailInfoMapper;

    public TypeDocDetailInfoService(
        TypeDocDetailInfoRepository typeDocDetailInfoRepository,
        TypeDocDetailInfoMapper typeDocDetailInfoMapper,
        DetailInfoRepository detailInfoRepository,
        DetailInfoMapper detailInfoMapper
    ) {
        this.typeDocDetailInfoRepository = typeDocDetailInfoRepository;
        this.typeDocDetailInfoMapper = typeDocDetailInfoMapper;
        this.detailInfoRepository = detailInfoRepository;
        this.detailInfoMapper = detailInfoMapper;
    }

    /**
     * Save a typeDocDetailInfo.
     *
     * @param typeDocDetailInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeDocDetailInfoDTO save(TypeDocDetailInfoDTO typeDocDetailInfoDTO) {
        log.debug("Request to save TypeDocDetailInfo : {}", typeDocDetailInfoDTO);
        TypeDocDetailInfo typeDocDetailInfo = typeDocDetailInfoMapper.toEntity(typeDocDetailInfoDTO);
        typeDocDetailInfo = typeDocDetailInfoRepository.save(typeDocDetailInfo);
        return typeDocDetailInfoMapper.toDto(typeDocDetailInfo);
    }

    /**
     * Partially update a typeDocDetailInfo.
     *
     * @param typeDocDetailInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypeDocDetailInfoDTO> partialUpdate(TypeDocDetailInfoDTO typeDocDetailInfoDTO) {
        log.debug("Request to partially update TypeDocDetailInfo : {}", typeDocDetailInfoDTO);

        return typeDocDetailInfoRepository
            .findById(typeDocDetailInfoDTO.getId())
            .map(existingTypeDocDetailInfo -> {
                typeDocDetailInfoMapper.partialUpdate(existingTypeDocDetailInfo, typeDocDetailInfoDTO);

                return existingTypeDocDetailInfo;
            })
            .map(typeDocDetailInfoRepository::save)
            .map(typeDocDetailInfoMapper::toDto);
    }

    /**
     * Get all the typeDocDetailInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeDocDetailInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeDocDetailInfos");
        return typeDocDetailInfoRepository.findAll(pageable).map(typeDocDetailInfoMapper::toDto);
    }

    /**
     * Get one typeDocDetailInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeDocDetailInfoDTO> findOne(Long id) {
        log.debug("Request to get TypeDocDetailInfo : {}", id);
        return typeDocDetailInfoRepository.findById(id).map(typeDocDetailInfoMapper::toDto);
    }

    /**
     * Remplace toutes les affectations d'un Typedocument par la nouvelle liste de detailInfoIds.
     *
     * @param typeDocId l'id du Typedocument.
     * @param detailInfoIds la liste des ids de DetailInfo à affecter.
     * @return la liste des affectations créées.
     */
    public List<TypeDocDetailInfoDTO> affecter(Long typeDocId, List<Long> detailInfoIds) {
        log.debug("Request to affecter DetailInfos {} to Typedocument {}", detailInfoIds, typeDocId);

        typeDocDetailInfoRepository.deleteByTypeDocId(typeDocId);

        List<TypeDocDetailInfo> toSave = detailInfoIds
            .stream()
            .map(detailInfoId -> {
                TypeDocDetailInfo entity = new TypeDocDetailInfo();
                entity.setTypeDocId(typeDocId);
                entity.setDetailInfoId(detailInfoId);
                return entity;
            })
            .collect(Collectors.toList());

        return typeDocDetailInfoRepository
            .saveAll(toSave)
            .stream()
            .map(typeDocDetailInfoMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Récupère les DetailInfo affectés à un Typedocument donné.
     *
     * @param typeDocId l'id du Typedocument.
     * @return la liste des DetailInfo affectés.
     */
    @Transactional(readOnly = true)
    public List<DetailInfoDTO> findDetailInfosByTypeDocId(Long typeDocId) {
        log.debug("Request to get DetailInfos for Typedocument : {}", typeDocId);

        List<Long> detailInfoIds = typeDocDetailInfoRepository
            .findByTypeDocId(typeDocId)
            .stream()
            .map(TypeDocDetailInfo::getDetailInfoId)
            .collect(Collectors.toList());

        return detailInfoRepository
            .findAllById(detailInfoIds)
            .stream()
            .map(detailInfoMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Delete the typeDocDetailInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeDocDetailInfo : {}", id);
        typeDocDetailInfoRepository.deleteById(id);
    }
}
