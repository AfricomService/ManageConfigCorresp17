package com.africom.configmanage.web.rest;

import static com.africom.configmanage.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.Configexport;
import com.africom.configmanage.repository.ConfigexportRepository;
import com.africom.configmanage.service.dto.ConfigexportDTO;
import com.africom.configmanage.service.mapper.ConfigexportMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link ConfigexportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigexportResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final byte[] DEFAULT_EXCEL_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EXCEL_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EXCEL_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EXCEL_FILE_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONFIG_MAPPING_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_MAPPING_JSON = "BBBBBBBBBB";

    private static final String DEFAULT_READ_ACCESS = "AAAAAAAAAA";
    private static final String UPDATED_READ_ACCESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/configexports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigexportRepository configexportRepository;

    @Autowired
    private ConfigexportMapper configexportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigexportMockMvc;

    private Configexport configexport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configexport createEntity(EntityManager em) {
        Configexport configexport = new Configexport()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE)
            .excelFile(DEFAULT_EXCEL_FILE)
            .excelFileContentType(DEFAULT_EXCEL_FILE_CONTENT_TYPE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .configMappingJson(DEFAULT_CONFIG_MAPPING_JSON)
            .readAccess(DEFAULT_READ_ACCESS);
        return configexport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Configexport createUpdatedEntity(EntityManager em) {
        Configexport configexport = new Configexport()
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE)
            .excelFile(UPDATED_EXCEL_FILE)
            .excelFileContentType(UPDATED_EXCEL_FILE_CONTENT_TYPE)
            .dateCreated(UPDATED_DATE_CREATED)
            .configMappingJson(UPDATED_CONFIG_MAPPING_JSON)
            .readAccess(UPDATED_READ_ACCESS);
        return configexport;
    }

    @BeforeEach
    public void initTest() {
        configexport = createEntity(em);
    }

    @Test
    @Transactional
    void createConfigexport() throws Exception {
        int databaseSizeBeforeCreate = configexportRepository.findAll().size();
        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);
        restConfigexportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeCreate + 1);
        Configexport testConfigexport = configexportList.get(configexportList.size() - 1);
        assertThat(testConfigexport.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testConfigexport.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testConfigexport.getExcelFile()).isEqualTo(DEFAULT_EXCEL_FILE);
        assertThat(testConfigexport.getExcelFileContentType()).isEqualTo(DEFAULT_EXCEL_FILE_CONTENT_TYPE);
        assertThat(testConfigexport.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testConfigexport.getConfigMappingJson()).isEqualTo(DEFAULT_CONFIG_MAPPING_JSON);
        assertThat(testConfigexport.getReadAccess()).isEqualTo(DEFAULT_READ_ACCESS);
    }

    @Test
    @Transactional
    void createConfigexportWithExistingId() throws Exception {
        // Create the Configexport with an existing ID
        configexport.setId(1L);
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        int databaseSizeBeforeCreate = configexportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigexportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfigexports() throws Exception {
        // Initialize the database
        configexportRepository.saveAndFlush(configexport);

        // Get all the configexportList
        restConfigexportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configexport.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].excelFileContentType").value(hasItem(DEFAULT_EXCEL_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].excelFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_EXCEL_FILE))))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))))
            .andExpect(jsonPath("$.[*].configMappingJson").value(hasItem(DEFAULT_CONFIG_MAPPING_JSON)))
            .andExpect(jsonPath("$.[*].readAccess").value(hasItem(DEFAULT_READ_ACCESS)));
    }

    @Test
    @Transactional
    void getConfigexport() throws Exception {
        // Initialize the database
        configexportRepository.saveAndFlush(configexport);

        // Get the configexport
        restConfigexportMockMvc
            .perform(get(ENTITY_API_URL_ID, configexport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configexport.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.excelFileContentType").value(DEFAULT_EXCEL_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.excelFile").value(Base64Utils.encodeToString(DEFAULT_EXCEL_FILE)))
            .andExpect(jsonPath("$.dateCreated").value(sameInstant(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.configMappingJson").value(DEFAULT_CONFIG_MAPPING_JSON))
            .andExpect(jsonPath("$.readAccess").value(DEFAULT_READ_ACCESS));
    }

    @Test
    @Transactional
    void getNonExistingConfigexport() throws Exception {
        // Get the configexport
        restConfigexportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfigexport() throws Exception {
        // Initialize the database
        configexportRepository.saveAndFlush(configexport);

        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();

        // Update the configexport
        Configexport updatedConfigexport = configexportRepository.findById(configexport.getId()).get();
        // Disconnect from session so that the updates on updatedConfigexport are not directly saved in db
        em.detach(updatedConfigexport);
        updatedConfigexport
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE)
            .excelFile(UPDATED_EXCEL_FILE)
            .excelFileContentType(UPDATED_EXCEL_FILE_CONTENT_TYPE)
            .dateCreated(UPDATED_DATE_CREATED)
            .configMappingJson(UPDATED_CONFIG_MAPPING_JSON)
            .readAccess(UPDATED_READ_ACCESS);
        ConfigexportDTO configexportDTO = configexportMapper.toDto(updatedConfigexport);

        restConfigexportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configexportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
        Configexport testConfigexport = configexportList.get(configexportList.size() - 1);
        assertThat(testConfigexport.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testConfigexport.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testConfigexport.getExcelFile()).isEqualTo(UPDATED_EXCEL_FILE);
        assertThat(testConfigexport.getExcelFileContentType()).isEqualTo(UPDATED_EXCEL_FILE_CONTENT_TYPE);
        assertThat(testConfigexport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testConfigexport.getConfigMappingJson()).isEqualTo(UPDATED_CONFIG_MAPPING_JSON);
        assertThat(testConfigexport.getReadAccess()).isEqualTo(UPDATED_READ_ACCESS);
    }

    @Test
    @Transactional
    void putNonExistingConfigexport() throws Exception {
        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();
        configexport.setId(count.incrementAndGet());

        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigexportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configexportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigexport() throws Exception {
        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();
        configexport.setId(count.incrementAndGet());

        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigexportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigexport() throws Exception {
        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();
        configexport.setId(count.incrementAndGet());

        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigexportMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigexportWithPatch() throws Exception {
        // Initialize the database
        configexportRepository.saveAndFlush(configexport);

        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();

        // Update the configexport using partial update
        Configexport partialUpdatedConfigexport = new Configexport();
        partialUpdatedConfigexport.setId(configexport.getId());

        partialUpdatedConfigexport
            .label(UPDATED_LABEL)
            .excelFile(UPDATED_EXCEL_FILE)
            .excelFileContentType(UPDATED_EXCEL_FILE_CONTENT_TYPE)
            .configMappingJson(UPDATED_CONFIG_MAPPING_JSON)
            .readAccess(UPDATED_READ_ACCESS);

        restConfigexportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigexport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigexport))
            )
            .andExpect(status().isOk());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
        Configexport testConfigexport = configexportList.get(configexportList.size() - 1);
        assertThat(testConfigexport.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testConfigexport.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testConfigexport.getExcelFile()).isEqualTo(UPDATED_EXCEL_FILE);
        assertThat(testConfigexport.getExcelFileContentType()).isEqualTo(UPDATED_EXCEL_FILE_CONTENT_TYPE);
        assertThat(testConfigexport.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testConfigexport.getConfigMappingJson()).isEqualTo(UPDATED_CONFIG_MAPPING_JSON);
        assertThat(testConfigexport.getReadAccess()).isEqualTo(UPDATED_READ_ACCESS);
    }

    @Test
    @Transactional
    void fullUpdateConfigexportWithPatch() throws Exception {
        // Initialize the database
        configexportRepository.saveAndFlush(configexport);

        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();

        // Update the configexport using partial update
        Configexport partialUpdatedConfigexport = new Configexport();
        partialUpdatedConfigexport.setId(configexport.getId());

        partialUpdatedConfigexport
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE)
            .excelFile(UPDATED_EXCEL_FILE)
            .excelFileContentType(UPDATED_EXCEL_FILE_CONTENT_TYPE)
            .dateCreated(UPDATED_DATE_CREATED)
            .configMappingJson(UPDATED_CONFIG_MAPPING_JSON)
            .readAccess(UPDATED_READ_ACCESS);

        restConfigexportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigexport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigexport))
            )
            .andExpect(status().isOk());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
        Configexport testConfigexport = configexportList.get(configexportList.size() - 1);
        assertThat(testConfigexport.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testConfigexport.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testConfigexport.getExcelFile()).isEqualTo(UPDATED_EXCEL_FILE);
        assertThat(testConfigexport.getExcelFileContentType()).isEqualTo(UPDATED_EXCEL_FILE_CONTENT_TYPE);
        assertThat(testConfigexport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testConfigexport.getConfigMappingJson()).isEqualTo(UPDATED_CONFIG_MAPPING_JSON);
        assertThat(testConfigexport.getReadAccess()).isEqualTo(UPDATED_READ_ACCESS);
    }

    @Test
    @Transactional
    void patchNonExistingConfigexport() throws Exception {
        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();
        configexport.setId(count.incrementAndGet());

        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigexportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configexportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigexport() throws Exception {
        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();
        configexport.setId(count.incrementAndGet());

        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigexportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigexport() throws Exception {
        int databaseSizeBeforeUpdate = configexportRepository.findAll().size();
        configexport.setId(count.incrementAndGet());

        // Create the Configexport
        ConfigexportDTO configexportDTO = configexportMapper.toDto(configexport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigexportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configexportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Configexport in the database
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigexport() throws Exception {
        // Initialize the database
        configexportRepository.saveAndFlush(configexport);

        int databaseSizeBeforeDelete = configexportRepository.findAll().size();

        // Delete the configexport
        restConfigexportMockMvc
            .perform(delete(ENTITY_API_URL_ID, configexport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Configexport> configexportList = configexportRepository.findAll();
        assertThat(configexportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
