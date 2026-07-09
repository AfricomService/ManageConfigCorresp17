package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.TypeDocDetailInfoRepository;
import com.africom.configmanage.service.TypeDocDetailInfoService;
import com.africom.configmanage.service.dto.DetailInfoDTO;
import com.africom.configmanage.service.dto.TypeDocDetailInfoDTO;
import com.africom.configmanage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.africom.configmanage.domain.TypeDocDetailInfo}.
 */
@RestController
@RequestMapping("/api")
public class TypeDocDetailInfoResource {

    private final Logger log = LoggerFactory.getLogger(TypeDocDetailInfoResource.class);

    private static final String ENTITY_NAME = "configManageTypeDocDetailInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeDocDetailInfoService typeDocDetailInfoService;

    private final TypeDocDetailInfoRepository typeDocDetailInfoRepository;

    public TypeDocDetailInfoResource(
        TypeDocDetailInfoService typeDocDetailInfoService,
        TypeDocDetailInfoRepository typeDocDetailInfoRepository
    ) {
        this.typeDocDetailInfoService = typeDocDetailInfoService;
        this.typeDocDetailInfoRepository = typeDocDetailInfoRepository;
    }

    /**
     * {@code POST  /type-doc-detail-infos} : Create a new typeDocDetailInfo.
     *
     * @param typeDocDetailInfoDTO the typeDocDetailInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeDocDetailInfoDTO, or with status {@code 400 (Bad Request)} if the typeDocDetailInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-doc-detail-infos")
    public ResponseEntity<TypeDocDetailInfoDTO> createTypeDocDetailInfo(@RequestBody TypeDocDetailInfoDTO typeDocDetailInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save TypeDocDetailInfo : {}", typeDocDetailInfoDTO);
        if (typeDocDetailInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeDocDetailInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDocDetailInfoDTO result = typeDocDetailInfoService.save(typeDocDetailInfoDTO);
        return ResponseEntity
            .created(new URI("/api/type-doc-detail-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-doc-detail-infos/:id} : Updates an existing typeDocDetailInfo.
     *
     * @param id the id of the typeDocDetailInfoDTO to save.
     * @param typeDocDetailInfoDTO the typeDocDetailInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDocDetailInfoDTO,
     * or with status {@code 400 (Bad Request)} if the typeDocDetailInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeDocDetailInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-doc-detail-infos/{id}")
    public ResponseEntity<TypeDocDetailInfoDTO> updateTypeDocDetailInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeDocDetailInfoDTO typeDocDetailInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeDocDetailInfo : {}, {}", id, typeDocDetailInfoDTO);
        if (typeDocDetailInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeDocDetailInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeDocDetailInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeDocDetailInfoDTO result = typeDocDetailInfoService.save(typeDocDetailInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDocDetailInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-doc-detail-infos/:id} : Partial updates given fields of an existing typeDocDetailInfo, field will ignore if it is null
     *
     * @param id the id of the typeDocDetailInfoDTO to save.
     * @param typeDocDetailInfoDTO the typeDocDetailInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDocDetailInfoDTO,
     * or with status {@code 400 (Bad Request)} if the typeDocDetailInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeDocDetailInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeDocDetailInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-doc-detail-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeDocDetailInfoDTO> partialUpdateTypeDocDetailInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeDocDetailInfoDTO typeDocDetailInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeDocDetailInfo partially : {}, {}", id, typeDocDetailInfoDTO);
        if (typeDocDetailInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeDocDetailInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeDocDetailInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeDocDetailInfoDTO> result = typeDocDetailInfoService.partialUpdate(typeDocDetailInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDocDetailInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-doc-detail-infos} : get all the typeDocDetailInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeDocDetailInfos in body.
     */
    @GetMapping("/type-doc-detail-infos")
    public ResponseEntity<List<TypeDocDetailInfoDTO>> getAllTypeDocDetailInfos(Pageable pageable) {
        log.debug("REST request to get a page of TypeDocDetailInfos");
        Page<TypeDocDetailInfoDTO> page = typeDocDetailInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-doc-detail-infos/:id} : get the "id" typeDocDetailInfo.
     *
     * @param id the id of the typeDocDetailInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeDocDetailInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-doc-detail-infos/{id}")
    public ResponseEntity<TypeDocDetailInfoDTO> getTypeDocDetailInfo(@PathVariable Long id) {
        log.debug("REST request to get TypeDocDetailInfo : {}", id);
        Optional<TypeDocDetailInfoDTO> typeDocDetailInfoDTO = typeDocDetailInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDocDetailInfoDTO);
    }

    /**
     * {@code POST  /type-documents/:typeDocId/detail-infos} : affecte une liste de DetailInfo à un Typedocument.
     * Remplace toute affectation existante pour ce Typedocument.
     *
     * @param typeDocId l'id du Typedocument.
     * @param detailInfoIds la liste des ids de DetailInfo à affecter.
     * @return la liste des affectations créées.
     */
    @PostMapping("/type-documents/{typeDocId}/detail-infos")
    public ResponseEntity<List<TypeDocDetailInfoDTO>> affecterDetailInfos(
        @PathVariable Long typeDocId,
        @RequestBody List<Long> detailInfoIds
    ) {
        log.debug("REST request to affecter DetailInfos {} to Typedocument {}", detailInfoIds, typeDocId);
        return ResponseEntity.ok(typeDocDetailInfoService.affecter(typeDocId, detailInfoIds));
    }

    /**
     * {@code GET  /type-documents/:typeDocId/detail-infos} : récupère les DetailInfo affectés à un Typedocument.
     *
     * @param typeDocId l'id du Typedocument.
     * @return la liste des DetailInfo affectés.
     */
    @GetMapping("/type-documents/{typeDocId}/detail-infos")
    public ResponseEntity<List<DetailInfoDTO>> getDetailInfosForTypeDoc(@PathVariable Long typeDocId) {
        log.debug("REST request to get DetailInfos for Typedocument {}", typeDocId);
        return ResponseEntity.ok(typeDocDetailInfoService.findDetailInfosByTypeDocId(typeDocId));
    }

    /**
     * {@code DELETE  /type-doc-detail-infos/:id} : delete the "id" typeDocDetailInfo.

    /**
     * {@code DELETE  /type-doc-detail-infos/:id} : delete the "id" typeDocDetailInfo.
     *
     * @param id the id of the typeDocDetailInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-doc-detail-infos/{id}")
    public ResponseEntity<Void> deleteTypeDocDetailInfo(@PathVariable Long id) {
        log.debug("REST request to delete TypeDocDetailInfo : {}", id);
        typeDocDetailInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
