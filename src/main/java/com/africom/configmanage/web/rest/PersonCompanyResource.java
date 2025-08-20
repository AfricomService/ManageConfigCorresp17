package com.africom.configmanage.web.rest;

import com.africom.configmanage.repository.PersonCompanyRepository;
import com.africom.configmanage.service.PersonCompanyService;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.africom.configmanage.domain.PersonCompany}.
 */
@RestController
@RequestMapping("/api")
public class PersonCompanyResource {

    private final Logger log = LoggerFactory.getLogger(PersonCompanyResource.class);

    private static final String ENTITY_NAME = "configManagePersonCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonCompanyService personCompanyService;

    private final PersonCompanyRepository personCompanyRepository;

    public PersonCompanyResource(PersonCompanyService personCompanyService, PersonCompanyRepository personCompanyRepository) {
        this.personCompanyService = personCompanyService;
        this.personCompanyRepository = personCompanyRepository;
    }

    /**
     * {@code POST  /person-companies} : Create a new personCompany.
     *
     * @param personCompanyDTO the personCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personCompanyDTO, or with status {@code 400 (Bad Request)} if the personCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-companies")
    public ResponseEntity<PersonCompanyDTO> createPersonCompany(@Valid @RequestBody PersonCompanyDTO personCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save PersonCompany : {}", personCompanyDTO);
        if (personCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new personCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonCompanyDTO result = personCompanyService.save(personCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/person-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-companies/:id} : Updates an existing personCompany.
     *
     * @param id the id of the personCompanyDTO to save.
     * @param personCompanyDTO the personCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the personCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-companies/{id}")
    public ResponseEntity<PersonCompanyDTO> updatePersonCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonCompanyDTO personCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonCompany : {}, {}", id, personCompanyDTO);
        if (personCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonCompanyDTO result = personCompanyService.update(personCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-companies/:id} : Partial updates given fields of an existing personCompany, field will ignore if it is null
     *
     * @param id the id of the personCompanyDTO to save.
     * @param personCompanyDTO the personCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the personCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-companies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonCompanyDTO> partialUpdatePersonCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonCompanyDTO personCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonCompany partially : {}, {}", id, personCompanyDTO);
        if (personCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonCompanyDTO> result = personCompanyService.partialUpdate(personCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /person-companies} : get all the personCompanies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personCompanies in body.
     */
    @GetMapping("/person-companies")
    public ResponseEntity<List<PersonCompanyDTO>> getAllPersonCompanies(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PersonCompanies");
        Page<PersonCompanyDTO> page = personCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-companies/:id} : get the "id" personCompany.
     *
     * @param id the id of the personCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-companies/{id}")
    public ResponseEntity<PersonCompanyDTO> getPersonCompany(@PathVariable Long id) {
        log.debug("REST request to get PersonCompany : {}", id);
        Optional<PersonCompanyDTO> personCompanyDTO = personCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personCompanyDTO);
    }

    /**
     * {@code DELETE  /person-companies/:id} : delete the "id" personCompany.
     *
     * @param id the id of the personCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-companies/{id}")
    public ResponseEntity<Void> deletePersonCompany(@PathVariable Long id) {
        log.debug("REST request to delete PersonCompany : {}", id);
        personCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
