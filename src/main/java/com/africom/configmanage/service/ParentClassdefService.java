package com.africom.configmanage.service;

import com.africom.configmanage.domain.Numsequentielle;
import com.africom.configmanage.domain.ParentClassdef;
import com.africom.configmanage.domain.enumeration.CorrespType;
import com.africom.configmanage.repository.NumsequentielleRepository;
import com.africom.configmanage.repository.ParentClassdefRepository;
import com.africom.configmanage.service.dto.NumsequentielleDTO;
import com.africom.configmanage.service.dto.ParentClassdefDTO;
import com.africom.configmanage.service.mapper.ParentClassdefMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParentClassdef}.
 */
@Service
@Transactional
public class ParentClassdefService {

    private final Logger log = LoggerFactory.getLogger(ParentClassdefService.class);

    private final ParentClassdefRepository parentClassdefRepository;
    private final NumsequentielleRepository numsequentielleRepository;

    private final Configuration freemarkerConfig;

    private final ParentClassdefMapper parentClassdefMapper;

    public ParentClassdefService(
        ParentClassdefRepository parentClassdefRepository,
        NumsequentielleRepository numsequentielleRepository,
        Configuration freemarkerConfig,
        ParentClassdefMapper parentClassdefMapper
    ) {
        this.parentClassdefRepository = parentClassdefRepository;
        this.numsequentielleRepository = numsequentielleRepository;
        this.freemarkerConfig = freemarkerConfig;

        this.parentClassdefMapper = parentClassdefMapper;
    }

    /**
     * Save a parentClassdef.
     *
     * @param parentClassdefDTO the entity to save.
     * @return the persisted entity.
     */
    public ParentClassdefDTO save(ParentClassdefDTO parentClassdefDTO) {
        log.debug("Request to save ParentClassdef : {}", parentClassdefDTO);
        ParentClassdef parentClassdef = parentClassdefMapper.toEntity(parentClassdefDTO);
        parentClassdef = parentClassdefRepository.save(parentClassdef);
        return parentClassdefMapper.toDto(parentClassdef);
    }

    /**
     * Update a parentClassdef.
     *
     * @param parentClassdefDTO the entity to save.
     * @return the persisted entity.
     */
    public ParentClassdefDTO update(ParentClassdefDTO parentClassdefDTO) {
        log.debug("Request to update ParentClassdef : {}", parentClassdefDTO);
        ParentClassdef parentClassdef = parentClassdefMapper.toEntity(parentClassdefDTO);
        parentClassdef = parentClassdefRepository.save(parentClassdef);
        return parentClassdefMapper.toDto(parentClassdef);
    }

    /**
     * Partially update a parentClassdef.
     *
     * @param parentClassdefDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParentClassdefDTO> partialUpdate(ParentClassdefDTO parentClassdefDTO) {
        log.debug("Request to partially update ParentClassdef : {}", parentClassdefDTO);

        return parentClassdefRepository
            .findById(parentClassdefDTO.getId())
            .map(existingParentClassdef -> {
                parentClassdefMapper.partialUpdate(existingParentClassdef, parentClassdefDTO);

                return existingParentClassdef;
            })
            .map(parentClassdefRepository::save)
            .map(parentClassdefMapper::toDto);
    }

    /**
     * Get all the parentClassdefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParentClassdefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParentClassdefs");
        return parentClassdefRepository.findAll(pageable).map(parentClassdefMapper::toDto);
    }

    /**
     * Get one parentClassdef by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParentClassdefDTO> findOne(Long id) {
        log.debug("Request to get ParentClassdef : {}", id);
        return parentClassdefRepository.findById(id).map(parentClassdefMapper::toDto);
    }

    /**
     * Delete the parentClassdef by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParentClassdef : {}", id);
        parentClassdefRepository.deleteById(id);
    }

    public String getAndIncrementNextNumber(String type, String codeUniqueSociete) throws Exception {
        // 1. Convertir type → enum
        CorrespType correspType;
        switch (type) {
            case "INCOMING":
                correspType = CorrespType.INCOMING;
                break;
            case "OUTGOING":
                correspType = CorrespType.OUTGOING;
                break;
            case "INTERNAL":
                correspType = CorrespType.INTERNAL;
                break;
            default:
                correspType = CorrespType.INCOMING;
                break;
        }

        // 2. Récupérer le ParentClassdef pour ce type → lire sa formule FreeMarker
        ParentClassdef parent = parentClassdefRepository.findByType(correspType);
        if (parent == null) {
            throw new RuntimeException("ParentClassdef introuvable pour le type : " + type);
        }

        String formule = parent.getFormule();
        if (formule == null || formule.isBlank()) {
            throw new RuntimeException("Formule non définie pour le ParentClassdef de type : " + type);
        }

        // 3. Évaluer la formule FreeMarker pour obtenir le codeNumSeq
        //    La formule contient par exemple : ${correspTypeCode}-${societeCode}
        //    Résultat attendu : AR-AF, SR-GDC, etc.
        Map<String, Object> formulaModel = new HashMap<>();

        // Mapper CorrespType → code court
        String correspTypeCode;
        switch (correspType) {
            case INCOMING:
                correspTypeCode = "AR";
                break;
            case OUTGOING:
                correspTypeCode = "SR";
                break;
            case INTERNAL:
                correspTypeCode = "IN";
                break;
            default:
                correspTypeCode = "AR";
                break;
        }
        formulaModel.put("correspTypeCode", correspTypeCode);
        formulaModel.put("societeCode", codeUniqueSociete != null ? codeUniqueSociete : "");

        Template formulaTemplate = new Template("formulaTemplate", new StringReader(formule), freemarkerConfig);
        StringWriter formulaWriter = new StringWriter();
        formulaTemplate.process(formulaModel, formulaWriter);
        String resolvedCodeNumSeq = formulaWriter.toString().trim();

        // 4. Chercher la Numsequentielle par codeNumSeq
        Numsequentielle numSeq = numsequentielleRepository
            .findByCodeNumSeq(resolvedCodeNumSeq)
            .orElseThrow(() -> new RuntimeException("Numsequentielle introuvable pour codeNumSeq : " + resolvedCodeNumSeq));

        // 5. Générer le numéro avec le format de la Numsequentielle
        Long currentNumber = numSeq.getNextNumber();
        String format = numSeq.getFormat();

        int year = LocalDate.now().getYear();
        int shortYear = year % 100;

        Map<String, Object> numModel = new HashMap<>();
        numModel.put("prefix", resolvedCodeNumSeq); // le codeNumSeq sert de prefix affiché
        numModel.put("year", shortYear);
        numModel.put("number", currentNumber);

        Template numTemplate = new Template("numTemplate", new StringReader(format), freemarkerConfig);
        StringWriter numWriter = new StringWriter();
        numTemplate.process(numModel, numWriter);
        String generatedNumber = numWriter.toString();

        // 6. Incrémenter
        numSeq.setNextNumber(currentNumber + 1);
        numsequentielleRepository.save(numSeq);

        return generatedNumber;
    }
}
