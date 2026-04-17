package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.ModelNotifRepository;
import com.africom.configmanage.service.ModelNotifService;
import com.africom.configmanage.service.dto.ModelNotifDTO;
import com.africom.configmanage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.africom.configmanage.domain.ModelNotif}.
 */
@RestController
@RequestMapping("/api")
public class ModelNotifResource {

    private final Logger log = LoggerFactory.getLogger(ModelNotifResource.class);

    private static final String ENTITY_NAME = "configManageModelNotif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelNotifService modelNotifService;

    private final ModelNotifRepository modelNotifRepository;

    public ModelNotifResource(ModelNotifService modelNotifService, ModelNotifRepository modelNotifRepository) {
        this.modelNotifService = modelNotifService;
        this.modelNotifRepository = modelNotifRepository;
    }

    /**
     * {@code POST  /model-notifs} : Create a new modelNotif.
     *
     * @param modelNotifDTO the modelNotifDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelNotifDTO, or with status {@code 400 (Bad Request)} if the modelNotif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-notifs")
    public ResponseEntity<ModelNotifDTO> createModelNotif(@Valid @RequestBody ModelNotifDTO modelNotifDTO) throws URISyntaxException {
        log.debug("REST request to save ModelNotif : {}", modelNotifDTO);
        if (modelNotifDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelNotif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModelNotifDTO result = modelNotifService.save(modelNotifDTO);
        return ResponseEntity
            .created(new URI("/api/model-notifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /model-notifs/:id} : Updates an existing modelNotif.
     *
     * @param id the id of the modelNotifDTO to save.
     * @param modelNotifDTO the modelNotifDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelNotifDTO,
     * or with status {@code 400 (Bad Request)} if the modelNotifDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelNotifDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-notifs/{id}")
    public ResponseEntity<ModelNotifDTO> updateModelNotif(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ModelNotifDTO modelNotifDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModelNotif : {}, {}", id, modelNotifDTO);
        if (modelNotifDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelNotifDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelNotifRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModelNotifDTO result = modelNotifService.update(modelNotifDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelNotifDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /model-notifs/:id} : Partial updates given fields of an existing modelNotif, field will ignore if it is null
     *
     * @param id the id of the modelNotifDTO to save.
     * @param modelNotifDTO the modelNotifDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelNotifDTO,
     * or with status {@code 400 (Bad Request)} if the modelNotifDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelNotifDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelNotifDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/model-notifs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModelNotifDTO> partialUpdateModelNotif(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ModelNotifDTO modelNotifDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModelNotif partially : {}, {}", id, modelNotifDTO);
        if (modelNotifDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelNotifDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelNotifRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModelNotifDTO> result = modelNotifService.partialUpdate(modelNotifDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelNotifDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /model-notifs} : get all the modelNotifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelNotifs in body.
     */
    @GetMapping("/model-notifs")
    public ResponseEntity<List<ModelNotifDTO>> getAllModelNotifs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ModelNotifs");
        Page<ModelNotifDTO> page = modelNotifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /model-notifs/:id} : get the "id" modelNotif.
     *
     * @param id the id of the modelNotifDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelNotifDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-notifs/{id}")
    public ResponseEntity<ModelNotifDTO> getModelNotif(@PathVariable UUID id) {
        log.debug("REST request to get ModelNotif : {}", id);
        Optional<ModelNotifDTO> modelNotifDTO = modelNotifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelNotifDTO);
    }

    /**
     * {@code DELETE  /model-notifs/:id} : delete the "id" modelNotif.
     *
     * @param id the id of the modelNotifDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-notifs/{id}")
    public ResponseEntity<Void> deleteModelNotif(@PathVariable UUID id) {
        log.debug("REST request to delete ModelNotif : {}", id);
        modelNotifService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/model-notifs/by-code/{code}")
    public ResponseEntity<ModelNotifDTO> getModelNotifByCode(@PathVariable String code) {
        log.debug("REST request to get ModelNotif by code : {}", code);
        Optional<ModelNotifDTO> modelNotifDTO = modelNotifService.findOneByCode(code);
        return ResponseUtil.wrapOrNotFound(modelNotifDTO);
    }
}
