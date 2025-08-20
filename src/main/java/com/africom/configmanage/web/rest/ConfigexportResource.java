package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.ConfigexportRepository;
import com.africom.configmanage.service.ConfigexportService;
import com.africom.configmanage.service.dto.ConfigexportDTO;
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
 * REST controller for managing {@link com.africom.configmanage.domain.Configexport}.
 */
@RestController
@RequestMapping("/api")
public class ConfigexportResource {

    private final Logger log = LoggerFactory.getLogger(ConfigexportResource.class);

    private static final String ENTITY_NAME = "configManageConfigexport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigexportService configexportService;

    private final ConfigexportRepository configexportRepository;

    public ConfigexportResource(ConfigexportService configexportService, ConfigexportRepository configexportRepository) {
        this.configexportService = configexportService;
        this.configexportRepository = configexportRepository;
    }

    /**
     * {@code POST  /configexports} : Create a new configexport.
     *
     * @param configexportDTO the configexportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configexportDTO, or with status {@code 400 (Bad Request)} if the configexport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/configexports")
    public ResponseEntity<ConfigexportDTO> createConfigexport(@RequestBody ConfigexportDTO configexportDTO) throws URISyntaxException {
        log.debug("REST request to save Configexport : {}", configexportDTO);
        if (configexportDTO.getId() != null) {
            throw new BadRequestAlertException("A new configexport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigexportDTO result = configexportService.save(configexportDTO);
        return ResponseEntity
            .created(new URI("/api/configexports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configexports/:id} : Updates an existing configexport.
     *
     * @param id the id of the configexportDTO to save.
     * @param configexportDTO the configexportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configexportDTO,
     * or with status {@code 400 (Bad Request)} if the configexportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configexportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/configexports/{id}")
    public ResponseEntity<ConfigexportDTO> updateConfigexport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigexportDTO configexportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Configexport : {}, {}", id, configexportDTO);
        if (configexportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configexportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configexportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfigexportDTO result = configexportService.update(configexportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configexportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /configexports/:id} : Partial updates given fields of an existing configexport, field will ignore if it is null
     *
     * @param id the id of the configexportDTO to save.
     * @param configexportDTO the configexportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configexportDTO,
     * or with status {@code 400 (Bad Request)} if the configexportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the configexportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the configexportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/configexports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfigexportDTO> partialUpdateConfigexport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigexportDTO configexportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Configexport partially : {}, {}", id, configexportDTO);
        if (configexportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configexportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configexportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfigexportDTO> result = configexportService.partialUpdate(configexportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configexportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /configexports} : get all the configexports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configexports in body.
     */
    @GetMapping("/configexports")
    public ResponseEntity<List<ConfigexportDTO>> getAllConfigexports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Configexports");
        Page<ConfigexportDTO> page = configexportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /configexports/:id} : get the "id" configexport.
     *
     * @param id the id of the configexportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configexportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/configexports/{id}")
    public ResponseEntity<ConfigexportDTO> getConfigexport(@PathVariable Long id) {
        log.debug("REST request to get Configexport : {}", id);
        Optional<ConfigexportDTO> configexportDTO = configexportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configexportDTO);
    }

    /**
     * {@code DELETE  /configexports/:id} : delete the "id" configexport.
     *
     * @param id the id of the configexportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/configexports/{id}")
    public ResponseEntity<Void> deleteConfigexport(@PathVariable Long id) {
        log.debug("REST request to delete Configexport : {}", id);
        configexportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
