package com.africom.configmanage.web.rest;

import static com.africom.configmanage.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.ModelNotif;
import com.africom.configmanage.repository.ModelNotifRepository;
import com.africom.configmanage.service.dto.ModelNotifDTO;
import com.africom.configmanage.service.mapper.ModelNotifMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ModelNotifResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModelNotifResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILLE = "AAAAAAAAAA";
    private static final String UPDATED_FAMILLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_FORMULA = "BBBBBBBBBB";

    private static final String DEFAULT_SENDER_EMAIL_FORMULE = "AAAAAAAAAA";
    private static final String UPDATED_SENDER_EMAIL_FORMULE = "BBBBBBBBBB";

    private static final String DEFAULT_RECEPIENT_EMAIL_FORMULE = "AAAAAAAAAA";
    private static final String UPDATED_RECEPIENT_EMAIL_FORMULE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/model-notifs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ModelNotifRepository modelNotifRepository;

    @Autowired
    private ModelNotifMapper modelNotifMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModelNotifMockMvc;

    private ModelNotif modelNotif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelNotif createEntity(EntityManager em) {
        ModelNotif modelNotif = new ModelNotif()
            .name(DEFAULT_NAME)
            .famille(DEFAULT_FAMILLE)
            .code(DEFAULT_CODE)
            .content(DEFAULT_CONTENT)
            .subjectFormula(DEFAULT_SUBJECT_FORMULA)
            .senderEmailFormule(DEFAULT_SENDER_EMAIL_FORMULE)
            .recepientEmailFormule(DEFAULT_RECEPIENT_EMAIL_FORMULE)
            .createdDate(DEFAULT_CREATED_DATE);
        return modelNotif;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelNotif createUpdatedEntity(EntityManager em) {
        ModelNotif modelNotif = new ModelNotif()
            .name(UPDATED_NAME)
            .famille(UPDATED_FAMILLE)
            .code(UPDATED_CODE)
            .content(UPDATED_CONTENT)
            .subjectFormula(UPDATED_SUBJECT_FORMULA)
            .senderEmailFormule(UPDATED_SENDER_EMAIL_FORMULE)
            .recepientEmailFormule(UPDATED_RECEPIENT_EMAIL_FORMULE)
            .createdDate(UPDATED_CREATED_DATE);
        return modelNotif;
    }

    @BeforeEach
    public void initTest() {
        modelNotif = createEntity(em);
    }

    @Test
    @Transactional
    void createModelNotif() throws Exception {
        int databaseSizeBeforeCreate = modelNotifRepository.findAll().size();
        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);
        restModelNotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelNotifDTO)))
            .andExpect(status().isCreated());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeCreate + 1);
        ModelNotif testModelNotif = modelNotifList.get(modelNotifList.size() - 1);
        assertThat(testModelNotif.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModelNotif.getFamille()).isEqualTo(DEFAULT_FAMILLE);
        assertThat(testModelNotif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModelNotif.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testModelNotif.getSubjectFormula()).isEqualTo(DEFAULT_SUBJECT_FORMULA);
        assertThat(testModelNotif.getSenderEmailFormule()).isEqualTo(DEFAULT_SENDER_EMAIL_FORMULE);
        assertThat(testModelNotif.getRecepientEmailFormule()).isEqualTo(DEFAULT_RECEPIENT_EMAIL_FORMULE);
        assertThat(testModelNotif.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createModelNotifWithExistingId() throws Exception {
        // Create the ModelNotif with an existing ID
        modelNotifRepository.saveAndFlush(modelNotif);
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        int databaseSizeBeforeCreate = modelNotifRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelNotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelNotifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelNotifRepository.findAll().size();
        // set the field null
        modelNotif.setName(null);

        // Create the ModelNotif, which fails.
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        restModelNotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelNotifDTO)))
            .andExpect(status().isBadRequest());

        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSenderEmailFormuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelNotifRepository.findAll().size();
        // set the field null
        modelNotif.setSenderEmailFormule(null);

        // Create the ModelNotif, which fails.
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        restModelNotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelNotifDTO)))
            .andExpect(status().isBadRequest());

        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelNotifRepository.findAll().size();
        // set the field null
        modelNotif.setCreatedDate(null);

        // Create the ModelNotif, which fails.
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        restModelNotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelNotifDTO)))
            .andExpect(status().isBadRequest());

        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllModelNotifs() throws Exception {
        // Initialize the database
        modelNotifRepository.saveAndFlush(modelNotif);

        // Get all the modelNotifList
        restModelNotifMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelNotif.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].famille").value(hasItem(DEFAULT_FAMILLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].subjectFormula").value(hasItem(DEFAULT_SUBJECT_FORMULA)))
            .andExpect(jsonPath("$.[*].senderEmailFormule").value(hasItem(DEFAULT_SENDER_EMAIL_FORMULE)))
            .andExpect(jsonPath("$.[*].recepientEmailFormule").value(hasItem(DEFAULT_RECEPIENT_EMAIL_FORMULE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }

    @Test
    @Transactional
    void getModelNotif() throws Exception {
        // Initialize the database
        modelNotifRepository.saveAndFlush(modelNotif);

        // Get the modelNotif
        restModelNotifMockMvc
            .perform(get(ENTITY_API_URL_ID, modelNotif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modelNotif.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.famille").value(DEFAULT_FAMILLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.subjectFormula").value(DEFAULT_SUBJECT_FORMULA))
            .andExpect(jsonPath("$.senderEmailFormule").value(DEFAULT_SENDER_EMAIL_FORMULE))
            .andExpect(jsonPath("$.recepientEmailFormule").value(DEFAULT_RECEPIENT_EMAIL_FORMULE))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingModelNotif() throws Exception {
        // Get the modelNotif
        restModelNotifMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModelNotif() throws Exception {
        // Initialize the database
        modelNotifRepository.saveAndFlush(modelNotif);

        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();

        // Update the modelNotif
        ModelNotif updatedModelNotif = modelNotifRepository.findById(modelNotif.getId()).get();
        // Disconnect from session so that the updates on updatedModelNotif are not directly saved in db
        em.detach(updatedModelNotif);
        updatedModelNotif
            .name(UPDATED_NAME)
            .famille(UPDATED_FAMILLE)
            .code(UPDATED_CODE)
            .content(UPDATED_CONTENT)
            .subjectFormula(UPDATED_SUBJECT_FORMULA)
            .senderEmailFormule(UPDATED_SENDER_EMAIL_FORMULE)
            .recepientEmailFormule(UPDATED_RECEPIENT_EMAIL_FORMULE)
            .createdDate(UPDATED_CREATED_DATE);
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(updatedModelNotif);

        restModelNotifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modelNotifDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelNotifDTO))
            )
            .andExpect(status().isOk());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
        ModelNotif testModelNotif = modelNotifList.get(modelNotifList.size() - 1);
        assertThat(testModelNotif.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModelNotif.getFamille()).isEqualTo(UPDATED_FAMILLE);
        assertThat(testModelNotif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModelNotif.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testModelNotif.getSubjectFormula()).isEqualTo(UPDATED_SUBJECT_FORMULA);
        assertThat(testModelNotif.getSenderEmailFormule()).isEqualTo(UPDATED_SENDER_EMAIL_FORMULE);
        assertThat(testModelNotif.getRecepientEmailFormule()).isEqualTo(UPDATED_RECEPIENT_EMAIL_FORMULE);
        assertThat(testModelNotif.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingModelNotif() throws Exception {
        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();
        modelNotif.setId(UUID.randomUUID());

        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelNotifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modelNotifDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelNotifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModelNotif() throws Exception {
        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();
        modelNotif.setId(UUID.randomUUID());

        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelNotifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelNotifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModelNotif() throws Exception {
        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();
        modelNotif.setId(UUID.randomUUID());

        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelNotifMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelNotifDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModelNotifWithPatch() throws Exception {
        // Initialize the database
        modelNotifRepository.saveAndFlush(modelNotif);

        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();

        // Update the modelNotif using partial update
        ModelNotif partialUpdatedModelNotif = new ModelNotif();
        partialUpdatedModelNotif.setId(modelNotif.getId());

        partialUpdatedModelNotif.name(UPDATED_NAME).code(UPDATED_CODE).recepientEmailFormule(UPDATED_RECEPIENT_EMAIL_FORMULE);

        restModelNotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModelNotif.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModelNotif))
            )
            .andExpect(status().isOk());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
        ModelNotif testModelNotif = modelNotifList.get(modelNotifList.size() - 1);
        assertThat(testModelNotif.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModelNotif.getFamille()).isEqualTo(DEFAULT_FAMILLE);
        assertThat(testModelNotif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModelNotif.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testModelNotif.getSubjectFormula()).isEqualTo(DEFAULT_SUBJECT_FORMULA);
        assertThat(testModelNotif.getSenderEmailFormule()).isEqualTo(DEFAULT_SENDER_EMAIL_FORMULE);
        assertThat(testModelNotif.getRecepientEmailFormule()).isEqualTo(UPDATED_RECEPIENT_EMAIL_FORMULE);
        assertThat(testModelNotif.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateModelNotifWithPatch() throws Exception {
        // Initialize the database
        modelNotifRepository.saveAndFlush(modelNotif);

        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();

        // Update the modelNotif using partial update
        ModelNotif partialUpdatedModelNotif = new ModelNotif();
        partialUpdatedModelNotif.setId(modelNotif.getId());

        partialUpdatedModelNotif
            .name(UPDATED_NAME)
            .famille(UPDATED_FAMILLE)
            .code(UPDATED_CODE)
            .content(UPDATED_CONTENT)
            .subjectFormula(UPDATED_SUBJECT_FORMULA)
            .senderEmailFormule(UPDATED_SENDER_EMAIL_FORMULE)
            .recepientEmailFormule(UPDATED_RECEPIENT_EMAIL_FORMULE)
            .createdDate(UPDATED_CREATED_DATE);

        restModelNotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModelNotif.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModelNotif))
            )
            .andExpect(status().isOk());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
        ModelNotif testModelNotif = modelNotifList.get(modelNotifList.size() - 1);
        assertThat(testModelNotif.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModelNotif.getFamille()).isEqualTo(UPDATED_FAMILLE);
        assertThat(testModelNotif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModelNotif.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testModelNotif.getSubjectFormula()).isEqualTo(UPDATED_SUBJECT_FORMULA);
        assertThat(testModelNotif.getSenderEmailFormule()).isEqualTo(UPDATED_SENDER_EMAIL_FORMULE);
        assertThat(testModelNotif.getRecepientEmailFormule()).isEqualTo(UPDATED_RECEPIENT_EMAIL_FORMULE);
        assertThat(testModelNotif.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingModelNotif() throws Exception {
        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();
        modelNotif.setId(UUID.randomUUID());

        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelNotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modelNotifDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelNotifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModelNotif() throws Exception {
        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();
        modelNotif.setId(UUID.randomUUID());

        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelNotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelNotifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModelNotif() throws Exception {
        int databaseSizeBeforeUpdate = modelNotifRepository.findAll().size();
        modelNotif.setId(UUID.randomUUID());

        // Create the ModelNotif
        ModelNotifDTO modelNotifDTO = modelNotifMapper.toDto(modelNotif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelNotifMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modelNotifDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModelNotif in the database
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModelNotif() throws Exception {
        // Initialize the database
        modelNotifRepository.saveAndFlush(modelNotif);

        int databaseSizeBeforeDelete = modelNotifRepository.findAll().size();

        // Delete the modelNotif
        restModelNotifMockMvc
            .perform(delete(ENTITY_API_URL_ID, modelNotif.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModelNotif> modelNotifList = modelNotifRepository.findAll();
        assertThat(modelNotifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
