package com.africom.configmanage.web.rest;

import com.africom.configmanage.client.DepartementTreeFeignDTO;
import com.africom.configmanage.client.OrgaCareRestClient;
import com.africom.configmanage.repository.OrgacareRepository;
import com.africom.configmanage.service.OrgacareService;
import com.africom.configmanage.service.dto.OrgacareDTO;
import com.africom.configmanage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.africom.configmanage.domain.Orgacare}.
 */
@RestController
@RequestMapping("/api")
public class OrgacareResource {

    private final Logger log = LoggerFactory.getLogger(OrgacareResource.class);

    private static final String ENTITY_NAME = "configManageOrgacare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgacareService orgacareService;

    private final OrgacareRepository orgacareRepository;
    private final OrgaCareRestClient orgaCareRestClient;

    public OrgacareResource(OrgacareService orgacareService, OrgacareRepository orgacareRepository, OrgaCareRestClient orgaCareRestClient) {
        this.orgacareService = orgacareService;
        this.orgacareRepository = orgacareRepository;
        this.orgaCareRestClient = orgaCareRestClient;
    }

    /**
     * {@code POST  /orgacares} : Create a new orgacare.
     *
     * @param orgacareDTO the orgacareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgacareDTO, or with status {@code 400 (Bad Request)} if the orgacare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orgacares")
    public ResponseEntity<OrgacareDTO> createOrgacare(@RequestBody OrgacareDTO orgacareDTO) throws URISyntaxException {
        log.debug("REST request to save Orgacare : {}", orgacareDTO);
        if (orgacareDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgacare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgacareDTO result = orgacareService.save(orgacareDTO);
        return ResponseEntity
            .created(new URI("/api/orgacares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orgacares/:id} : Updates an existing orgacare.
     *
     * @param id the id of the orgacareDTO to save.
     * @param orgacareDTO the orgacareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgacareDTO,
     * or with status {@code 400 (Bad Request)} if the orgacareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgacareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orgacares/{id}")
    public ResponseEntity<OrgacareDTO> updateOrgacare(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrgacareDTO orgacareDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Orgacare : {}, {}", id, orgacareDTO);
        if (orgacareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgacareDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgacareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrgacareDTO result = orgacareService.update(orgacareDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgacareDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /orgacares/:id} : Partial updates given fields of an existing orgacare, field will ignore if it is null
     *
     * @param id the id of the orgacareDTO to save.
     * @param orgacareDTO the orgacareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgacareDTO,
     * or with status {@code 400 (Bad Request)} if the orgacareDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orgacareDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgacareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orgacares/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrgacareDTO> partialUpdateOrgacare(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrgacareDTO orgacareDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Orgacare partially : {}, {}", id, orgacareDTO);
        if (orgacareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgacareDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgacareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrgacareDTO> result = orgacareService.partialUpdate(orgacareDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgacareDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /orgacares} : get all the orgacares.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgacares in body.
     */
    @GetMapping("/orgacares")
    public List<OrgacareDTO> getAllOrgacares() {
        log.debug("REST request to get all Orgacares");
        return orgacareService.findAll();
    }

    /**
     * {@code GET  /orgacares/:id} : get the "id" orgacare.
     *
     * @param id the id of the orgacareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgacareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orgacares/{id}")
    public ResponseEntity<OrgacareDTO> getOrgacare(@PathVariable Long id) {
        log.debug("REST request to get Orgacare : {}", id);
        Optional<OrgacareDTO> orgacareDTO = orgacareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgacareDTO);
    }

    /**
     * {@code DELETE  /orgacares/:id} : delete the "id" orgacare.
     *
     * @param id the id of the orgacareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orgacares/{id}")
    public ResponseEntity<Void> deleteOrgacare(@PathVariable Long id) {
        log.debug("REST request to delete Orgacare : {}", id);
        orgacareService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/DepartementsFromOrgaCare")
    public List<DepartementTreeFeignDTO> getAllCorrespsDeps(@RequestParam(name = "orgaCode") String orgaCode) {
        return orgaCareRestClient.getDepartementHierarchy(orgaCode);
    }
}
