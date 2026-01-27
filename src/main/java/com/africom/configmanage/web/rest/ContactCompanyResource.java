package com.africom.configmanage.web.rest;

import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.ContactCompanyRepository;
import com.africom.configmanage.service.ContactCompanyService;
import com.africom.configmanage.service.dto.ContactCompanyDTO;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.africom.configmanage.domain.ContactCompany}.
 */
@RestController
@RequestMapping("/api")
public class ContactCompanyResource {

    private final Logger log = LoggerFactory.getLogger(ContactCompanyResource.class);

    private static final String ENTITY_NAME = "configManageContactCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactCompanyService contactCompanyService;

    private final ContactCompanyRepository contactCompanyRepository;

    public ContactCompanyResource(ContactCompanyService contactCompanyService, ContactCompanyRepository contactCompanyRepository) {
        this.contactCompanyService = contactCompanyService;
        this.contactCompanyRepository = contactCompanyRepository;
    }

    /**
     * {@code POST  /contact-companies} : Create a new contactCompany.
     *
     * @param contactCompanyDTO the contactCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactCompanyDTO, or with status {@code 400 (Bad Request)} if the contactCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-companies")
    public ResponseEntity<ContactCompanyDTO> createContactCompany(@Valid @RequestBody ContactCompanyDTO contactCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContactCompany : {}", contactCompanyDTO);
        if (contactCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // Vérifier unicité du nom (ignore case). On considère conflit si une entité non Delated existe avec ce nom.
        contactCompanyRepository.findByNomIgnoreCase(contactCompanyDTO.getNom()).ifPresent(existing -> {
            if (existing.getEtat() == null || existing.getEtat() != UsualStatus.Delated) {
                throw new BadRequestAlertException("A ContactCompany with this name already exists", ENTITY_NAME, "nameexists");
            }
            // Si existing.getEtat() == Delated : on laisse créer (business decision).
        });

        // --- NOUVEAU: Vérifier unicité du matrne (si renseigné non vide) ---
        if (contactCompanyDTO.getMatrne() != null && !contactCompanyDTO.getMatrne().trim().isEmpty()) {
            String matrneTrim = contactCompanyDTO.getMatrne().trim();
            contactCompanyRepository.findByMatrneIgnoreCase(matrneTrim).ifPresent(existing -> {
                if (existing.getEtat() == null || existing.getEtat() != UsualStatus.Delated) {
                    throw new BadRequestAlertException("A ContactCompany with this matrne already exists", ENTITY_NAME, "matrneexists");
                }
                // si existing Delated on autorise la création (cohérent avec la logique nom)
            });
        }

        ContactCompanyDTO result = contactCompanyService.save(contactCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/contact-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-companies/:id} : Updates an existing contactCompany.
     *
     * @param id the id of the contactCompanyDTO to save.
     * @param contactCompanyDTO the contactCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the contactCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-companies/{id}")
    public ResponseEntity<ContactCompanyDTO> updateContactCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactCompanyDTO contactCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContactCompany : {}, {}", id, contactCompanyDTO);
        if (contactCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // Vérifier unicité du nom : si une autre entité (id différent) porte déjà ce nom => erreur
        contactCompanyRepository.findByNomIgnoreCase(contactCompanyDTO.getNom()).ifPresent(existing -> {
            if (!Objects.equals(existing.getId(), id) && (existing.getEtat() == null || existing.getEtat() != UsualStatus.Delated)) {
                throw new BadRequestAlertException("A ContactCompany with this name already exists", ENTITY_NAME, "nameexists");
            }
        });

        // --- NOUVEAU: Vérifier unicité du matrne (si renseigné non vide) ---
        if (contactCompanyDTO.getMatrne() != null && !contactCompanyDTO.getMatrne().trim().isEmpty()) {
            String matrneTrim = contactCompanyDTO.getMatrne().trim();
            contactCompanyRepository.findByMatrneIgnoreCase(matrneTrim).ifPresent(existing -> {
                if (!Objects.equals(existing.getId(), id) && (existing.getEtat() == null || existing.getEtat() != UsualStatus.Delated)) {
                    throw new BadRequestAlertException("A ContactCompany with this matrne already exists", ENTITY_NAME, "matrneexists");
                }
            });
        }

        ContactCompanyDTO result = contactCompanyService.update(contactCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-companies/:id} : Partial updates given fields of an existing contactCompany, field will ignore if it is null
     *
     * @param id the id of the contactCompanyDTO to save.
     * @param contactCompanyDTO the contactCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the contactCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-companies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactCompanyDTO> partialUpdateContactCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactCompanyDTO contactCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactCompany partially : {}, {}", id, contactCompanyDTO);
        if (contactCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // --- NOUVEAU: si matrne est fourni dans la requête (non null et non vide) -> vérifier unicité ---
        if (contactCompanyDTO.getMatrne() != null && !contactCompanyDTO.getMatrne().trim().isEmpty()) {
            String matrneTrim = contactCompanyDTO.getMatrne().trim();
            contactCompanyRepository.findByMatrneIgnoreCase(matrneTrim).ifPresent(existing -> {
                if (!Objects.equals(existing.getId(), id) && (existing.getEtat() == null || existing.getEtat() != UsualStatus.Delated)) {
                    throw new BadRequestAlertException("A ContactCompany with this matrne already exists", ENTITY_NAME, "matrneexists");
                }
            });
        }

        Optional<ContactCompanyDTO> result = contactCompanyService.partialUpdate(contactCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-companies} : get all the contactCompanies (only those not Delated).
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactCompanies in body.
     */
    @GetMapping("/contact-companies")
    public ResponseEntity<List<Map<String, Object>>> getAllContactCompanies(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ContactCompanies (with persons) - only non-Delated");

        ObjectMapper objectMapper = new ObjectMapper();

        // Récupère seulement les ContactCompany dont etat != Delated
        Page<ContactCompanyDTO> page = contactCompanyService.findAllActive(pageable);

        List<Map<String, Object>> content = page.getContent()
            .stream()
            .map(contactDto -> {
                // convertir ContactCompanyDTO -> Map
                Map<String, Object> map =
                    objectMapper.convertValue(contactDto, new TypeReference<LinkedHashMap<String, Object>>() {});

                // récupérer les personnes liées (liste non paginée)
                List<PersonCompanyDTO> persons = contactCompanyService.findPersonCompaniesByContactCompanyId(contactDto.getId());

                // filtrer les personnes Delated
                List<PersonCompanyDTO> filteredPersons = persons.stream()
                    .filter(p -> p.getEtat() == null || p.getEtat() != UsualStatus.Delated)
                    .collect(Collectors.toList());

                // ajout dynamique (sans DTO)
                map.put("personCompanies", filteredPersons);

                return map;
            })
            .collect(Collectors.toList());

        Page<Map<String, Object>> pageWithPersons =
            new PageImpl<>(content, pageable, page.getTotalElements());

        HttpHeaders headers =
            PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(),
                pageWithPersons
            );

        return ResponseEntity.ok().headers(headers).body(pageWithPersons.getContent());
    }

    /**
     * Endpoint d'export Excel (sans ids) :
     * GET /api/contact-companies/export/excel
     */
    @GetMapping("/contact-companies/export/excel")
    public ResponseEntity<byte[]> exportContactCompaniesToExcel() {
        log.debug("REST request to export ContactCompanies to Excel");
        try {
            byte[] bytes = contactCompanyService.exportAllContactCompaniesToExcelWithoutIds();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Carnet_d_Adresses.xlsx");
            headers.setContentLength(bytes.length);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Failed to generate Excel file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating Excel file");
        }
    }

    /**
     * NEW: {@code GET  /contact-companies/search} : search companies by query (multi-field + persons).
     *
     * @param query the free text to search (required)
     * @param includeDelated optional, default false. If true, include Delated entities.
     * @param pageable pagination info
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ContactCompanyDTO in body.
     */
    @GetMapping("/contact-companies/search")
    public ResponseEntity<List<ContactCompanyDTO>> searchContactCompanies(
        @RequestParam("query") String query,
        @RequestParam(value = "includeDelated", required = false, defaultValue = "false") boolean includeDelated,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search ContactCompanies with query={}, includeDelated={}", query, includeDelated);
        Page<ContactCompanyDTO> page = contactCompanyService.searchContactCompanies(query, includeDelated, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-companies/:id} : get the "id" contactCompany.
     *
     * @param id the id of the contactCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-companies/{id}")
    public ResponseEntity<ContactCompanyDTO> getContactCompany(@PathVariable Long id) {
        log.debug("REST request to get ContactCompany : {}", id);
        Optional<ContactCompanyDTO> contactCompanyDTO = contactCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactCompanyDTO);
    }

    /**
     * {@code DELETE  /contact-companies/:id} : delete the "id" contactCompany.
     *
     * @param id the id of the contactCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-companies/{id}")
    public ResponseEntity<Void> deleteContactCompany(@PathVariable Long id) {
        log.debug("REST request to delete ContactCompany : {}", id);
        contactCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // endpoint paginé : GET /api/contact-companies/{id}/person-companies
    @GetMapping("/contact-companies/{id}/person-companies")
    public ResponseEntity<List<PersonCompanyDTO>> getPersonCompaniesByContactCompany(
        @PathVariable Long id,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PersonCompanies for ContactCompany : {}", id);

        // Vérifier que le ContactCompany existe (cohérent avec le reste du controller)
        if (!contactCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Page<PersonCompanyDTO> page = contactCompanyService.findPersonCompaniesByContactCompanyId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    // endpoint non paginé (liste complète) : GET /api/contact-companies/{id}/person-companies/all
    @GetMapping("/contact-companies/{id}/person-companies/all")
    public ResponseEntity<List<PersonCompanyDTO>> getAllPersonCompaniesByContactCompany(@PathVariable Long id) {
        log.debug("REST request to get all PersonCompanies for ContactCompany : {}", id);

        if (!contactCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        List<PersonCompanyDTO> list = contactCompanyService.findPersonCompaniesByContactCompanyId(id);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/contact-companies/{id}/delate")
    public ResponseEntity<ContactCompanyDTO> delateContactCompany(@PathVariable Long id) {
        log.debug("REST request to mark ContactCompany (id={}) and its persons as Delated", id);

        // cohérence avec les autres endpoints du controller : vérifier d'abord que l'entité existe
        if (!contactCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactCompanyDTO> result = contactCompanyService.markAsDelatedWithPersons(id);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())
        );
    }

    @PutMapping("/contact-companies/{id}/toggle-activation")
    public ResponseEntity<ContactCompanyDTO> toggleActivation(@PathVariable Long id) {
        log.debug("REST request to toggle activation for ContactCompany : {}", id);

        // Vérifier que le ContactCompany existe
        if (!contactCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // Récupérer le DTO pour vérifier l'état actuel
        Optional<ContactCompanyDTO> maybe = contactCompanyService.findOne(id);
        if (maybe.isEmpty()) {
            // improbable car existsById true, mais on gère quand même
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactCompanyDTO dto = maybe.get();
        if (dto.getEtat() == UsualStatus.Delated) {
            throw new BadRequestAlertException("Cannot change state of a Delated entity", ENTITY_NAME, "stateinvalid");
        }

        Optional<ContactCompanyDTO> result = contactCompanyService.toggleActivationWithPersons(id);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())
        );
    }
}
