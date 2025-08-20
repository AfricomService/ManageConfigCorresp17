package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.MyreportRepository;
import com.africom.configmanage.service.MyreportService;
import com.africom.configmanage.service.dto.MyreportDTO;
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
 * REST controller for managing {@link com.africom.configmanage.domain.Myreport}.
 */
@RestController
@RequestMapping("/api")
public class MyreportResource {

    private final Logger log = LoggerFactory.getLogger(MyreportResource.class);

    private static final String ENTITY_NAME = "configManageMyreport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyreportService myreportService;

    private final MyreportRepository myreportRepository;

    public MyreportResource(MyreportService myreportService, MyreportRepository myreportRepository) {
        this.myreportService = myreportService;
        this.myreportRepository = myreportRepository;
    }

    /**
     * {@code POST  /myreports} : Create a new myreport.
     *
     * @param myreportDTO the myreportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myreportDTO, or with status {@code 400 (Bad Request)} if the myreport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/myreports")
    public ResponseEntity<MyreportDTO> createMyreport(@RequestBody MyreportDTO myreportDTO) throws URISyntaxException {
        log.debug("REST request to save Myreport : {}", myreportDTO);
        if (myreportDTO.getId() != null) {
            throw new BadRequestAlertException("A new myreport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyreportDTO result = myreportService.save(myreportDTO);
        return ResponseEntity
            .created(new URI("/api/myreports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /myreports/:id} : Updates an existing myreport.
     *
     * @param id the id of the myreportDTO to save.
     * @param myreportDTO the myreportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myreportDTO,
     * or with status {@code 400 (Bad Request)} if the myreportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myreportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/myreports/{id}")
    public ResponseEntity<MyreportDTO> updateMyreport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MyreportDTO myreportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Myreport : {}, {}", id, myreportDTO);
        if (myreportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myreportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myreportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MyreportDTO result = myreportService.update(myreportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myreportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /myreports/:id} : Partial updates given fields of an existing myreport, field will ignore if it is null
     *
     * @param id the id of the myreportDTO to save.
     * @param myreportDTO the myreportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myreportDTO,
     * or with status {@code 400 (Bad Request)} if the myreportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the myreportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the myreportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/myreports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MyreportDTO> partialUpdateMyreport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MyreportDTO myreportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Myreport partially : {}, {}", id, myreportDTO);
        if (myreportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myreportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myreportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MyreportDTO> result = myreportService.partialUpdate(myreportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myreportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /myreports} : get all the myreports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myreports in body.
     */
    @GetMapping("/myreports")
    public ResponseEntity<List<MyreportDTO>> getAllMyreports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Myreports");
        Page<MyreportDTO> page = myreportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /myreports/:id} : get the "id" myreport.
     *
     * @param id the id of the myreportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myreportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/myreports/{id}")
    public ResponseEntity<MyreportDTO> getMyreport(@PathVariable Long id) {
        log.debug("REST request to get Myreport : {}", id);
        Optional<MyreportDTO> myreportDTO = myreportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myreportDTO);
    }

    /**
     * {@code DELETE  /myreports/:id} : delete the "id" myreport.
     *
     * @param id the id of the myreportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/myreports/{id}")
    public ResponseEntity<Void> deleteMyreport(@PathVariable Long id) {
        log.debug("REST request to delete Myreport : {}", id);
        myreportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
