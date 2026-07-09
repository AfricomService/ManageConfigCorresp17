package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.DetailInfoRepository;
import com.africom.configmanage.service.DetailInfoService;
import com.africom.configmanage.service.dto.DetailInfoDTO;
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
 * REST controller for managing {@link com.africom.configmanage.domain.DetailInfo}.
 */
@RestController
@RequestMapping("/api")
public class DetailInfoResource {

    private final Logger log = LoggerFactory.getLogger(DetailInfoResource.class);

    private static final String ENTITY_NAME = "configManageDetailInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailInfoService detailInfoService;

    private final DetailInfoRepository detailInfoRepository;

    public DetailInfoResource(DetailInfoService detailInfoService, DetailInfoRepository detailInfoRepository) {
        this.detailInfoService = detailInfoService;
        this.detailInfoRepository = detailInfoRepository;
    }

    /**
     * {@code POST  /detail-infos} : Create a new detailInfo.
     *
     * @param detailInfoDTO the detailInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailInfoDTO, or with status {@code 400 (Bad Request)} if the detailInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-infos")
    public ResponseEntity<DetailInfoDTO> createDetailInfo(@RequestBody DetailInfoDTO detailInfoDTO) throws URISyntaxException {
        log.debug("REST request to save DetailInfo : {}", detailInfoDTO);
        if (detailInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailInfoDTO result = detailInfoService.save(detailInfoDTO);
        return ResponseEntity
            .created(new URI("/api/detail-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-infos/:id} : Updates an existing detailInfo.
     *
     * @param id the id of the detailInfoDTO to save.
     * @param detailInfoDTO the detailInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailInfoDTO,
     * or with status {@code 400 (Bad Request)} if the detailInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-infos/{id}")
    public ResponseEntity<DetailInfoDTO> updateDetailInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailInfoDTO detailInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetailInfo : {}, {}", id, detailInfoDTO);
        if (detailInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailInfoDTO result = detailInfoService.save(detailInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-infos/:id} : Partial updates given fields of an existing detailInfo, field will ignore if it is null
     *
     * @param id the id of the detailInfoDTO to save.
     * @param detailInfoDTO the detailInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailInfoDTO,
     * or with status {@code 400 (Bad Request)} if the detailInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detailInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailInfoDTO> partialUpdateDetailInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailInfoDTO detailInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailInfo partially : {}, {}", id, detailInfoDTO);
        if (detailInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailInfoDTO> result = detailInfoService.partialUpdate(detailInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-infos} : get all the detailInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailInfos in body.
     */
    @GetMapping("/detail-infos")
    public ResponseEntity<List<DetailInfoDTO>> getAllDetailInfos(Pageable pageable) {
        log.debug("REST request to get a page of DetailInfos");
        Page<DetailInfoDTO> page = detailInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-infos/:id} : get the "id" detailInfo.
     *
     * @param id the id of the detailInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-infos/{id}")
    public ResponseEntity<DetailInfoDTO> getDetailInfo(@PathVariable Long id) {
        log.debug("REST request to get DetailInfo : {}", id);
        Optional<DetailInfoDTO> detailInfoDTO = detailInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailInfoDTO);
    }

    /**
     * {@code DELETE  /detail-infos/:id} : delete the "id" detailInfo.
     *
     * @param id the id of the detailInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-infos/{id}")
    public ResponseEntity<Void> deleteDetailInfo(@PathVariable Long id) {
        log.debug("REST request to delete DetailInfo : {}", id);
        detailInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
