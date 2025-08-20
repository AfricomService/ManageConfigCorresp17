package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.TypedocumentRepository;
import com.africom.configmanage.service.TypedocumentService;
import com.africom.configmanage.service.dto.TypedocumentDTO;
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
 * REST controller for managing {@link com.africom.configmanage.domain.Typedocument}.
 */
@RestController
@RequestMapping("/api")
public class TypedocumentResource {

    private final Logger log = LoggerFactory.getLogger(TypedocumentResource.class);

    private static final String ENTITY_NAME = "configManageTypedocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypedocumentService typedocumentService;

    private final TypedocumentRepository typedocumentRepository;

    public TypedocumentResource(TypedocumentService typedocumentService, TypedocumentRepository typedocumentRepository) {
        this.typedocumentService = typedocumentService;
        this.typedocumentRepository = typedocumentRepository;
    }

    /**
     * {@code POST  /typedocuments} : Create a new typedocument.
     *
     * @param typedocumentDTO the typedocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typedocumentDTO, or with status {@code 400 (Bad Request)} if the typedocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/typedocuments")
    public ResponseEntity<TypedocumentDTO> createTypedocument(@RequestBody TypedocumentDTO typedocumentDTO) throws URISyntaxException {
        log.debug("REST request to save Typedocument : {}", typedocumentDTO);
        if (typedocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new typedocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypedocumentDTO result = typedocumentService.save(typedocumentDTO);
        return ResponseEntity
            .created(new URI("/api/typedocuments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /typedocuments/:id} : Updates an existing typedocument.
     *
     * @param id the id of the typedocumentDTO to save.
     * @param typedocumentDTO the typedocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typedocumentDTO,
     * or with status {@code 400 (Bad Request)} if the typedocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typedocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/typedocuments/{id}")
    public ResponseEntity<TypedocumentDTO> updateTypedocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypedocumentDTO typedocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Typedocument : {}, {}", id, typedocumentDTO);
        if (typedocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typedocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typedocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypedocumentDTO result = typedocumentService.update(typedocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typedocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /typedocuments/:id} : Partial updates given fields of an existing typedocument, field will ignore if it is null
     *
     * @param id the id of the typedocumentDTO to save.
     * @param typedocumentDTO the typedocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typedocumentDTO,
     * or with status {@code 400 (Bad Request)} if the typedocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typedocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typedocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/typedocuments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypedocumentDTO> partialUpdateTypedocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypedocumentDTO typedocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Typedocument partially : {}, {}", id, typedocumentDTO);
        if (typedocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typedocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typedocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypedocumentDTO> result = typedocumentService.partialUpdate(typedocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typedocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /typedocuments} : get all the typedocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typedocuments in body.
     */
    @GetMapping("/typedocuments")
    public ResponseEntity<List<TypedocumentDTO>> getAllTypedocuments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Typedocuments");
        Page<TypedocumentDTO> page = typedocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /typedocuments/:id} : get the "id" typedocument.
     *
     * @param id the id of the typedocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typedocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/typedocuments/{id}")
    public ResponseEntity<TypedocumentDTO> getTypedocument(@PathVariable Long id) {
        log.debug("REST request to get Typedocument : {}", id);
        Optional<TypedocumentDTO> typedocumentDTO = typedocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typedocumentDTO);
    }

    /**
     * {@code DELETE  /typedocuments/:id} : delete the "id" typedocument.
     *
     * @param id the id of the typedocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/typedocuments/{id}")
    public ResponseEntity<Void> deleteTypedocument(@PathVariable Long id) {
        log.debug("REST request to delete Typedocument : {}", id);
        typedocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
