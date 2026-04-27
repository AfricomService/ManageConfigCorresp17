package com.africom.configmanage.service;

import com.africom.configmanage.client.OrgaCareRestClient;
import com.africom.configmanage.domain.Typedocument;
import com.africom.configmanage.repository.TypedocumentRepository;
import com.africom.configmanage.service.dto.TypedocumentDTO;
import com.africom.configmanage.service.mapper.TypedocumentMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Typedocument}.
 */
@Service
@Transactional
public class TypedocumentService {

    private final Logger log = LoggerFactory.getLogger(TypedocumentService.class);

    private final TypedocumentRepository typedocumentRepository;

    private final TypedocumentMapper typedocumentMapper;

    private final OrgaCareRestClient orgaCareRestClient;


    public TypedocumentService(TypedocumentRepository typedocumentRepository, TypedocumentMapper typedocumentMapper, OrgaCareRestClient orgaCareRestClient ) {
        this.typedocumentRepository = typedocumentRepository;
        this.typedocumentMapper = typedocumentMapper;
        this.orgaCareRestClient = orgaCareRestClient;
    }

    /**
     * Save a typedocument.
     *
     * @param typedocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public TypedocumentDTO save(TypedocumentDTO typedocumentDTO) {
        log.debug("Request to save Typedocument : {}", typedocumentDTO);
        Typedocument typedocument = typedocumentMapper.toEntity(typedocumentDTO);
        typedocument = typedocumentRepository.save(typedocument);
        return typedocumentMapper.toDto(typedocument);
    }

    /**
     * Update a typedocument.
     *
     * @param typedocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public TypedocumentDTO update(TypedocumentDTO typedocumentDTO) {
        log.debug("Request to update Typedocument : {}", typedocumentDTO);
        Typedocument typedocument = typedocumentMapper.toEntity(typedocumentDTO);
        typedocument = typedocumentRepository.save(typedocument);
        return typedocumentMapper.toDto(typedocument);
    }

    /**
     * Partially update a typedocument.
     *
     * @param typedocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypedocumentDTO> partialUpdate(TypedocumentDTO typedocumentDTO) {
        log.debug("Request to partially update Typedocument : {}", typedocumentDTO);

        return typedocumentRepository
            .findById(typedocumentDTO.getId())
            .map(existingTypedocument -> {
                typedocumentMapper.partialUpdate(existingTypedocument, typedocumentDTO);

                return existingTypedocument;
            })
            .map(typedocumentRepository::save)
            .map(typedocumentMapper::toDto);
    }

    /**
     * Get all the typedocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypedocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Typedocuments");
        return typedocumentRepository.findAll(pageable).map(typedocumentMapper::toDto);
    }

    /**
     * Get one typedocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypedocumentDTO> findOne(Long id) {
        log.debug("Request to get Typedocument : {}", id);
        return typedocumentRepository.findById(id).map(typedocumentMapper::toDto);
    }

    /**
     * Delete the typedocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Typedocument : {}", id);
        typedocumentRepository.deleteById(id);
    }

    /**
     * Get all typedocuments matching the given correspType,
     * including those with no correspType (null).
     *
     * @param correspType the correspondence type to filter by (e.g. "Entrant", "Sortant", "Interne")
     * @return the list of matching TypedocumentDTOs
     */
    @Transactional(readOnly = true)
    public List<TypedocumentDTO> findByCorrespType(String correspType, String codeUniqueSociete) {
        log.debug("Request to get Typedocuments by correspType : {} and codeUniqueSociete : {}", correspType, codeUniqueSociete);
        if (codeUniqueSociete != null && !codeUniqueSociete.isEmpty()) {
            return typedocumentRepository
                .findByCorrespTypeAndSociete(correspType, codeUniqueSociete)
                .stream()
                .map(typedocumentMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
        }
        return typedocumentRepository
            .findByCorrespTypeOrCorrespTypeIsNull(correspType)
            .stream()
            .map(typedocumentMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }

    public List<Map<String, Object>> getAllSocietesIdAndRaisonSociale() {
        log.debug("Request to get all Societes (id + raisonSociale + codeSociete) from OrgaCare");
        return orgaCareRestClient.getAllSocietesIdAndRaisonSociale();
    }
}
