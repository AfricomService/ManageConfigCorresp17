package com.africom.configmanage.service;

import static org.checkerframework.checker.nullness.Opt.orElseThrow;

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

    public String getAndIncrementNextNumber(String type) throws Exception {
        // 1️⃣ Convertir type → enum
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

        ParentClassdef parent = parentClassdefRepository.findByType(correspType);
        ParentClassdefDTO parentDTO = parentClassdefMapper.toDto(parent);

        NumsequentielleDTO dto = parentDTO.getSequenceId();

        Numsequentielle numSeq = numsequentielleRepository
            .findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("Num sequence not found"));

        Long currentNumber = numSeq.getNextNumber();

        String format = numSeq.getFormat();
        String prefix = numSeq.getPrefix();

        int year = LocalDate.now().getYear();
        int shortYear = year % 100;

        // 2️⃣ Modèle FreeMarker
        Map<String, Object> model = new HashMap<>();
        model.put("prefix", prefix);
        model.put("year", shortYear);
        model.put("number", currentNumber);

        Template template = new Template("dynamicTemplate", new StringReader(format), freemarkerConfig);

        StringWriter writer = new StringWriter();
        template.process(model, writer);
        String generatedNumber = writer.toString();

        numSeq.setNextNumber(currentNumber + 1);
        numsequentielleRepository.save(numSeq);

        return generatedNumber;
    }
}
