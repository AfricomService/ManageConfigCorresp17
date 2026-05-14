package com.africom.configmanage.service;

import com.africom.configmanage.domain.ContactCompany;
import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.ContactCompanyRepository;
import com.africom.configmanage.repository.PersonCompanyRepository;
import com.africom.configmanage.service.dto.ContactCompanyDTO;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.service.mapper.ContactCompanyMapper;
import com.africom.configmanage.service.mapper.PersonCompanyMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

/**
 * Service Implementation for managing {@link ContactCompany}.
 */
@Service
@Transactional
public class ContactCompanyService {

    private final Logger log = LoggerFactory.getLogger(ContactCompanyService.class);

    private final ContactCompanyRepository contactCompanyRepository;

    private final ContactCompanyMapper contactCompanyMapper;

    private final PersonCompanyRepository personCompanyRepository;
    private final PersonCompanyMapper personCompanyMapper;

    public ContactCompanyService(ContactCompanyRepository contactCompanyRepository, ContactCompanyMapper contactCompanyMapper, PersonCompanyRepository personCompanyRepository,
                                 PersonCompanyMapper personCompanyMapper) {
        this.contactCompanyRepository = contactCompanyRepository;
        this.contactCompanyMapper = contactCompanyMapper;
        this.personCompanyRepository = personCompanyRepository;
        this.personCompanyMapper = personCompanyMapper;
    }

    /**
     * Save a contactCompany.
     *
     * @param contactCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactCompanyDTO save(ContactCompanyDTO contactCompanyDTO) {
        log.debug("Request to save ContactCompany : {}", contactCompanyDTO);
        // sanitize strings
        if (contactCompanyDTO.getNom() != null) {
            contactCompanyDTO.setNom(contactCompanyDTO.getNom().trim());
        }
        if (contactCompanyDTO.getMatrne() != null) {
            contactCompanyDTO.setMatrne(contactCompanyDTO.getMatrne().trim());
        }

        ContactCompany contactCompany = contactCompanyMapper.toEntity(contactCompanyDTO);
        // default new company state to Actif
        contactCompany.setEtat(UsualStatus.Actif);
        contactCompany = contactCompanyRepository.save(contactCompany);
        return contactCompanyMapper.toDto(contactCompany);
    }

    /**
     * Update a contactCompany.
     *
     * @param contactCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactCompanyDTO update(ContactCompanyDTO contactCompanyDTO) {
        log.debug("Request to update ContactCompany : {}", contactCompanyDTO);
        // sanitize strings
        if (contactCompanyDTO.getNom() != null) {
            contactCompanyDTO.setNom(contactCompanyDTO.getNom().trim());
        }
        if (contactCompanyDTO.getMatrne() != null) {
            contactCompanyDTO.setMatrne(contactCompanyDTO.getMatrne().trim());
        }

        ContactCompany contactCompany = contactCompanyMapper.toEntity(contactCompanyDTO);
        contactCompany = contactCompanyRepository.save(contactCompany);
        return contactCompanyMapper.toDto(contactCompany);
    }

    /**
     * Partially update a contactCompany.
     *
     * @param contactCompanyDTO the contactCompanyDTO to update partially.
     * @return the persisted entity.
     */
    public Optional<ContactCompanyDTO> partialUpdate(ContactCompanyDTO contactCompanyDTO) {
        log.debug("Request to partially update ContactCompany : {}", contactCompanyDTO);

        // sanitize if nom/matrne provided (trim)
        if (contactCompanyDTO.getNom() != null) {
            contactCompanyDTO.setNom(contactCompanyDTO.getNom().trim());
        }
        if (contactCompanyDTO.getMatrne() != null) {
            contactCompanyDTO.setMatrne(contactCompanyDTO.getMatrne().trim());
        }

        return contactCompanyRepository
            .findById(contactCompanyDTO.getId())
            .map(existingContactCompany -> {
                contactCompanyMapper.partialUpdate(existingContactCompany, contactCompanyDTO);

                return existingContactCompany;
            })
            .map(contactCompanyRepository::save)
            .map(contactCompanyMapper::toDto);
    }

    /**
     * Get all the contactCompanies (non filtré).
     *
     * @param pageable the pagination information.
     * @return the page of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactCompanies");
        return contactCompanyRepository.findAll(pageable).map(contactCompanyMapper::toDto);
    }

    /**
     * Get all the contactCompanies whose etat != Delated.
     *
     * @param pageable the pagination information.
     * @return the page of entities (only active / non Delated).
     */
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> findAllActive(Pageable pageable) {
        log.debug("Request to get all active (non-Delated) ContactCompanies");
        return contactCompanyRepository.findAllByEtatNot(UsualStatus.Delated, pageable).map(contactCompanyMapper::toDto);
    }

    /**
     * Get one contactCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactCompanyDTO> findOne(Long id) {
        log.debug("Request to get ContactCompany : {}", id);
        return contactCompanyRepository.findById(id).map(contactCompanyMapper::toDto);
    }

    /**
     * Delete the contactCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactCompany : {}", id);
        contactCompanyRepository.deleteById(id);
    }

    /**
     * Récupère la liste paginée des PersonCompany liés à un ContactCompany (par id).
     * Exclut en base les personnes dont etat == Delated pour meilleure performance.
     *
     * @param contactCompanyId l'id du ContactCompany
     * @param pageable pagination
     * @return page de PersonCompanyDTO (sans Delated)
     */
    @Transactional(readOnly = true)
    public Page<PersonCompanyDTO> findPersonCompaniesByContactCompanyId(Long contactCompanyId, Pageable pageable) {
        log.debug("Request to get PersonCompanies by ContactCompany id : {} (paginated, excluding Delated)", contactCompanyId);
        return personCompanyRepository
            .findByContactCompanyIdAndEtatNot(contactCompanyId, UsualStatus.Delated, pageable)
            .map(personCompanyMapper::toDto);
    }

    /**
     * Variante non paginée (List) — récupère uniquement les PersonCompany dont etat != Delated (filtrage en base).
     */
    @Transactional(readOnly = true)
    public List<PersonCompanyDTO> findPersonCompaniesByContactCompanyId(Long contactCompanyId) {
        log.debug("Request to get PersonCompanies (list) by ContactCompany id : {} (excluding Delated)", contactCompanyId);
        return personCompanyRepository
            .findByContactCompanyIdAndEtatNot(contactCompanyId, UsualStatus.Delated)
            .stream()
            .map(personCompanyMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ContactCompanyDTO> markAsDelatedWithPersons(Long contactCompanyId) {
        log.debug("Request to mark ContactCompany (id={}) as Delated and its PersonCompany as Delated", contactCompanyId);

        return contactCompanyRepository.findById(contactCompanyId)
            .map(contactCompany -> {
                // mettre à jour l'état du ContactCompany
                contactCompany.setEtat(UsualStatus.Delated);

                // récupérer toutes les personnes liées et les marquer Delated
                // ici on récupère toutes les personnes (y compris déjà Delated) pour s'assurer que toutes sont marquées
                List<PersonCompany> persons = personCompanyRepository.findByContactCompanyId(contactCompanyId);
                if (persons != null && !persons.isEmpty()) {
                    persons.forEach(p -> p.setEtat(UsualStatus.Delated));
                    personCompanyRepository.saveAll(persons);
                }

                // sauvegarder le contact et renvoyer le DTO
                ContactCompany saved = contactCompanyRepository.save(contactCompany);
                return contactCompanyMapper.toDto(saved);
            });
    }

    // changement de l'etat
    @Transactional
    public Optional<ContactCompanyDTO> toggleActivationWithPersons(Long contactCompanyId) {
        log.debug("Request to toggle activation state for ContactCompany id={}", contactCompanyId);

        return contactCompanyRepository.findById(contactCompanyId)
            .map(contactCompany -> {
                UsualStatus current = contactCompany.getEtat();

                // Interdire le toggle si Delated
                if (current == UsualStatus.Delated) {
                    log.warn("Cannot toggle state for Delated ContactCompany id={}", contactCompanyId);
                    return null;
                }

                // Actif -> Désactivated
                if (current == UsualStatus.Actif) {
                    contactCompany.setEtat(UsualStatus.Desactivated);

                    // 🔴 ON désactive aussi les personnes (sauf Delated)
                    List<PersonCompany> persons =
                        personCompanyRepository.findByContactCompanyIdAndEtatNot(
                            contactCompanyId,
                            UsualStatus.Delated
                        );

                    if (!persons.isEmpty()) {
                        persons.forEach(p -> p.setEtat(UsualStatus.Desactivated));
                        personCompanyRepository.saveAll(persons);
                    }
                }

                // Désactivated -> Actif
                else if (current == UsualStatus.Desactivated) {
                    contactCompany.setEtat(UsualStatus.Actif);
                    // 🟢 AUCUNE modification sur les PersonCompany
                }

                ContactCompany saved = contactCompanyRepository.save(contactCompany);
                return contactCompanyMapper.toDto(saved);
            });
    }


    /**
     * Recherche multi-champs sur ContactCompany (tous les champs textuels sauf id)
     * et sur les PersonCompany associés (nom, pays, adresse, tel, fax, email, whatsapp).
     *
     * @param q the query string (token). If null/empty returns (active) list depending on includeDelated.
     * @param includeDelated include Delated entities if true
     * @param pageable pagination
     * @return page of ContactCompanyDTO
     *
     * NOTE: this method assumes the ContactCompany entity has a collection named "personCompanies".
     * If the name differs, adapt the join() call accordingly.
     */
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> searchContactCompanies(String q, boolean includeDelated, Pageable pageable) {
        log.debug("Request to search ContactCompany with query='{}', includeDelated={}", q, includeDelated);

        if (q == null || q.trim().isEmpty()) {
            if (includeDelated) {
                return contactCompanyRepository.findAll(pageable).map(contactCompanyMapper::toDto);
            } else {
                return contactCompanyRepository.findAllByEtatNot(UsualStatus.Delated, pageable).map(contactCompanyMapper::toDto);
            }
        }

        final String token = q.trim().toLowerCase();
        final String likePattern = "%" + token + "%";

        Specification<ContactCompany> spec = (root, query, cb) -> {
            // distinct to avoid duplicates when subquery "exists" could match multiple persons
            query.distinct(true);

            // company predicates (coalesce to avoid nulls)
            Predicate nomP = cb.like(cb.lower(cb.coalesce(root.get("nom"), "")), likePattern);
            Predicate nomAbregeP = cb.like(cb.lower(cb.coalesce(root.get("nomabrege"), "")), likePattern);
            Predicate matrneP = cb.like(cb.lower(cb.coalesce(root.get("matrne"), "")), likePattern);
            Predicate paysP = cb.like(cb.lower(cb.coalesce(root.get("pays"), "")), likePattern);
            Predicate adresseP = cb.like(cb.lower(cb.coalesce(root.get("adresse"), "")), likePattern);
            Predicate telP = cb.like(cb.lower(cb.coalesce(root.get("tel"), "")), likePattern);
            Predicate faxP = cb.like(cb.lower(cb.coalesce(root.get("fax"), "")), likePattern);
            Predicate emailP = cb.like(cb.lower(cb.coalesce(root.get("email"), "")), likePattern);
            Predicate webpageP = cb.like(cb.lower(cb.coalesce(root.get("webpage"), "")), likePattern);

            Predicate companyAny = cb.or(nomP, nomAbregeP, matrneP, paysP, adresseP, telP, faxP, emailP, webpageP);

            // Exclude Delated entities by default for company
            Predicate notDelatedForCompany = includeDelated ? cb.conjunction() : cb.notEqual(root.get("etat"), UsualStatus.Delated);
            Predicate companyClause = cb.and(companyAny, notDelatedForCompany);

            // --- SUBQUERY on PersonCompany instead of join ---
            Subquery<Long> sub = query.subquery(Long.class);
            javax.persistence.criteria.Root<PersonCompany> p = sub.from(PersonCompany.class);
            sub.select(p.get("id"));

            Predicate personBelongs = cb.equal(p.get("contactCompanyId"), root.get("id"));

            Predicate pNom = cb.like(cb.lower(cb.coalesce(p.get("nom"), "")), likePattern);
            Predicate pPays = cb.like(cb.lower(cb.coalesce(p.get("pays"), "")), likePattern);
            Predicate pAdresse = cb.like(cb.lower(cb.coalesce(p.get("adresse"), "")), likePattern);
            Predicate pTel = cb.like(cb.lower(cb.coalesce(p.get("tel"), "")), likePattern);
            Predicate pFax = cb.like(cb.lower(cb.coalesce(p.get("fax"), "")), likePattern);
            Predicate pEmail = cb.like(cb.lower(cb.coalesce(p.get("email"), "")), likePattern);
            Predicate pWhatsapp = cb.like(cb.lower(cb.coalesce(p.get("whatsapp"), "")), likePattern);

            Predicate personAny = cb.or(pNom, pPays, pAdresse, pTel, pFax, pEmail, pWhatsapp);

            Predicate notDelatedForPerson = includeDelated ? cb.conjunction() : cb.notEqual(p.get("etat"), UsualStatus.Delated);

            sub.where(cb.and(personBelongs, personAny, notDelatedForPerson));

            return cb.or(companyClause, cb.exists(sub));
        };

        return contactCompanyRepository.findAll(spec, pageable).map(contactCompanyMapper::toDto);
    }

    /**
     * Génère un fichier Excel (.xlsx) contenant :
     * - feuille "Companies" : les ContactCompany (sans id)
     * - feuille "Persons"   : les PersonCompany liées (sans id ni contactCompanyId), avec contactCompanyNom pour le lien
     *
     * @return tableau de bytes du fichier Excel
     * @throws IOException si erreur IO
     */
    @Transactional(readOnly = true)
    public byte[] exportAllContactCompaniesToExcelWithoutIds() throws IOException {
        // Récupérer toutes les companies actives (non Delated)
        List<ContactCompanyDTO> companies = this.findAllActive(Pageable.unpaged()).getContent();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // --- sheet Companies (sans id) ---
            Sheet compSheet = workbook.createSheet("Companies");
            int compRowIdx = 0;
            Row headerComp = compSheet.createRow(compRowIdx++);
            String[] compHeaders = new String[] {
                "nom", "nomabrege", "matrne", "pays", "adresse", "tel", "fax", "email", "webpage", "etat"
            };
            for (int i = 0; i < compHeaders.length; i++) {
                Cell c = headerComp.createCell(i);
                c.setCellValue(compHeaders[i]);
            }

            for (ContactCompanyDTO cDto : companies) {
                Row r = compSheet.createRow(compRowIdx++);
                int col = 0;
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getNom()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getNomabrege()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getMatrne()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getPays()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getAdresse()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getTel()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getFax()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getEmail()));
                r.createCell(col++).setCellValue(nullToEmpty(cDto.getWebpage()));
                r.createCell(col++).setCellValue(cDto.getEtat() == null ? "" : cDto.getEtat().name());
            }

            // --- sheet Persons (sans id, sans contactCompanyId ; on ajoute contactCompanyNom) ---
            Sheet personSheet = workbook.createSheet("Persons");
            int personRowIdx = 0;
            Row headerPerson = personSheet.createRow(personRowIdx++);
            String[] personHeaders = new String[] {
                "contactCompanyNom", "nom", "pays", "adresse", "tel", "fax", "email", "whatsapp", "etat"
            };
            for (int i = 0; i < personHeaders.length; i++) {
                headerPerson.createCell(i).setCellValue(personHeaders[i]);
            }

            for (ContactCompanyDTO cDto : companies) {
                // récupérer les personnes liées (ta méthode non paginée)
                List<PersonCompanyDTO> persons = this.findPersonCompaniesByContactCompanyId(cDto.getId());
                if (persons != null) {
                    for (PersonCompanyDTO p : persons) {
                        Row r = personSheet.createRow(personRowIdx++);
                        int col = 0;
                        // on place d'abord le nom de la société pour garder le lien sans exporter l'id
                        r.createCell(col++).setCellValue(nullToEmpty(cDto.getNom()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getNom()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getPays()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getAdresse()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getTel()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getFax()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getEmail()));
                        r.createCell(col++).setCellValue(nullToEmpty(p.getWhatsapp()));
                        r.createCell(col++).setCellValue(p.getEtat() == null ? "" : p.getEtat().name());
                    }
                }
            }

            // Ajuster la largeur des colonnes
            autosizeAllColumns(compSheet, 10);
            autosizeAllColumns(personSheet, 9);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // --- helpers privés ---
    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    private static void autosizeAllColumns(Sheet sheet, int nbCols) {
        for (int i = 0; i < nbCols; i++) {
            try {
                sheet.autoSizeColumn(i);
            } catch (Exception ignored) {
                // ignore pour robustesse
            }
        }
    }

    // NOUVEAU — version paginée
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> findAllActivesList(Pageable pageable) {
        log.debug("Request to get paged active ContactCompanies (etat == Actif)");
        return contactCompanyRepository
            .findAllByEtat(UsualStatus.Actif, pageable)
            .map(contactCompanyMapper::toDto);
    }

    /**
     * Recherche paginée des ContactCompany actives (etat == Actif)
     * par nom ou matrne (insensible à la casse).
     * Si query est vide, retourne toutes les actives paginées.
     *
     * @param query   texte de recherche (nom ou matrne), peut être vide
     * @param pageable pagination
     * @return page de ContactCompanyDTO
     */
    @Transactional(readOnly = true)
    public Page<ContactCompanyDTO> searchActivesContactCompanies(String query, Pageable pageable) {
        log.debug("Request to search active ContactCompanies with query='{}' ", query);

        if (query == null || query.trim().isEmpty()) {
            // pas de recherche : retourner toutes les actives paginées
            return contactCompanyRepository
                .findAllByEtat(UsualStatus.Actif, pageable)
                .map(contactCompanyMapper::toDto);
        }

        String q = query.trim();
        return contactCompanyRepository
            .findByEtatAndNomContainingIgnoreCaseOrEtatAndMatrneContainingIgnoreCase(
                UsualStatus.Actif, q,
                UsualStatus.Actif, q,
                pageable
            )
            .map(contactCompanyMapper::toDto);
    }

    /** Génère un fichier modèle Excel vide avec les colonnes attendues */
    public byte[] generateImportTemplate() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Societes");
            String[] headers = {
                "nom*", "nomabrege", "matrne", "pays",
                "adresse", "tel", "fax", "email", "webpage"
            };
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            autosizeAllColumns(sheet, headers.length);
            workbook.write(out);
            return out.toByteArray();
        }
    }

    /** Lit le fichier Excel importé et crée les sociétés */
    @Transactional
    public Map<String, Object> importContactCompaniesFromExcel(
        org.springframework.web.multipart.MultipartFile file
    ) throws IOException {

        int created = 0, skipped = 0;
        List<String> errors = new java.util.ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Ligne 0 = en-têtes, on commence à 1
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String nom = getCellString(row, 0);
                if (nom == null || nom.isBlank()) {
                    skipped++;
                    continue; // nom obligatoire
                }

                // Vérifier doublon
                if (contactCompanyRepository.findByNomIgnoreCase(nom)
                    .filter(e -> e.getEtat() != UsualStatus.Delated)
                    .isPresent()) {
                    errors.add("Ligne " + (i + 1) + " : \"" + nom + "\" existe déjà — ignorée.");
                    skipped++;
                    continue;
                }

                ContactCompany company = new ContactCompany();
                company.setNom(nom.trim());
                company.setNomabrege(getCellString(row, 1));
                company.setMatrne(getCellString(row, 2));
                company.setPays(getCellString(row, 3));
                company.setAdresse(getCellString(row, 4));
                company.setTel(getCellString(row, 5));
                company.setFax(getCellString(row, 6));
                company.setEmail(getCellString(row, 7));
                company.setWebpage(getCellString(row, 8));
                company.setEtat(UsualStatus.Actif);

                contactCompanyRepository.save(company);
                created++;
            }
        }

        Map<String, Object> result = new java.util.LinkedHashMap<>();
        result.put("created", created);
        result.put("skipped", skipped);
        result.put("errors", errors);
        return result;
    }

    // Helper pour lire une cellule comme String
    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return null;
        cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
        String val = cell.getStringCellValue();
        return (val == null || val.isBlank()) ? null : val.trim();
    }


}
