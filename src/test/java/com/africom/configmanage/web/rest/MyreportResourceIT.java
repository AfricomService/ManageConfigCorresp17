package com.africom.configmanage.web.rest;

import static com.africom.configmanage.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.Myreport;
import com.africom.configmanage.repository.MyreportRepository;
import com.africom.configmanage.service.dto.MyreportDTO;
import com.africom.configmanage.service.mapper.MyreportMapper;
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

/**
 * Integration tests for the {@link MyreportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyreportResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LOGIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LOGIN_ID = 1L;
    private static final Long UPDATED_LOGIN_ID = 2L;

    private static final String DEFAULT_NAME_FILE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FOLDER = "BBBBBBBBBB";

    private static final Long DEFAULT_CONFIG_EXPORT_ID = 1L;
    private static final Long UPDATED_CONFIG_EXPORT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/myreports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyreportRepository myreportRepository;

    @Autowired
    private MyreportMapper myreportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyreportMockMvc;

    private Myreport myreport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Myreport createEntity(EntityManager em) {
        Myreport myreport = new Myreport()
            .dateCreated(DEFAULT_DATE_CREATED)
            .loginName(DEFAULT_LOGIN_NAME)
            .loginId(DEFAULT_LOGIN_ID)
            .nameFile(DEFAULT_NAME_FILE)
            .nameFolder(DEFAULT_NAME_FOLDER)
            .configExportId(DEFAULT_CONFIG_EXPORT_ID);
        return myreport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Myreport createUpdatedEntity(EntityManager em) {
        Myreport myreport = new Myreport()
            .dateCreated(UPDATED_DATE_CREATED)
            .loginName(UPDATED_LOGIN_NAME)
            .loginId(UPDATED_LOGIN_ID)
            .nameFile(UPDATED_NAME_FILE)
            .nameFolder(UPDATED_NAME_FOLDER)
            .configExportId(UPDATED_CONFIG_EXPORT_ID);
        return myreport;
    }

    @BeforeEach
    public void initTest() {
        myreport = createEntity(em);
    }

    @Test
    @Transactional
    void createMyreport() throws Exception {
        int databaseSizeBeforeCreate = myreportRepository.findAll().size();
        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);
        restMyreportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myreportDTO)))
            .andExpect(status().isCreated());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeCreate + 1);
        Myreport testMyreport = myreportList.get(myreportList.size() - 1);
        assertThat(testMyreport.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testMyreport.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testMyreport.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
        assertThat(testMyreport.getNameFile()).isEqualTo(DEFAULT_NAME_FILE);
        assertThat(testMyreport.getNameFolder()).isEqualTo(DEFAULT_NAME_FOLDER);
        assertThat(testMyreport.getConfigExportId()).isEqualTo(DEFAULT_CONFIG_EXPORT_ID);
    }

    @Test
    @Transactional
    void createMyreportWithExistingId() throws Exception {
        // Create the Myreport with an existing ID
        myreport.setId(1L);
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        int databaseSizeBeforeCreate = myreportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyreportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myreportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMyreports() throws Exception {
        // Initialize the database
        myreportRepository.saveAndFlush(myreport);

        // Get all the myreportList
        restMyreportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myreport.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))))
            .andExpect(jsonPath("$.[*].loginName").value(hasItem(DEFAULT_LOGIN_NAME)))
            .andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID.intValue())))
            .andExpect(jsonPath("$.[*].nameFile").value(hasItem(DEFAULT_NAME_FILE)))
            .andExpect(jsonPath("$.[*].nameFolder").value(hasItem(DEFAULT_NAME_FOLDER)))
            .andExpect(jsonPath("$.[*].configExportId").value(hasItem(DEFAULT_CONFIG_EXPORT_ID.intValue())));
    }

    @Test
    @Transactional
    void getMyreport() throws Exception {
        // Initialize the database
        myreportRepository.saveAndFlush(myreport);

        // Get the myreport
        restMyreportMockMvc
            .perform(get(ENTITY_API_URL_ID, myreport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myreport.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(sameInstant(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.loginName").value(DEFAULT_LOGIN_NAME))
            .andExpect(jsonPath("$.loginId").value(DEFAULT_LOGIN_ID.intValue()))
            .andExpect(jsonPath("$.nameFile").value(DEFAULT_NAME_FILE))
            .andExpect(jsonPath("$.nameFolder").value(DEFAULT_NAME_FOLDER))
            .andExpect(jsonPath("$.configExportId").value(DEFAULT_CONFIG_EXPORT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMyreport() throws Exception {
        // Get the myreport
        restMyreportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMyreport() throws Exception {
        // Initialize the database
        myreportRepository.saveAndFlush(myreport);

        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();

        // Update the myreport
        Myreport updatedMyreport = myreportRepository.findById(myreport.getId()).get();
        // Disconnect from session so that the updates on updatedMyreport are not directly saved in db
        em.detach(updatedMyreport);
        updatedMyreport
            .dateCreated(UPDATED_DATE_CREATED)
            .loginName(UPDATED_LOGIN_NAME)
            .loginId(UPDATED_LOGIN_ID)
            .nameFile(UPDATED_NAME_FILE)
            .nameFolder(UPDATED_NAME_FOLDER)
            .configExportId(UPDATED_CONFIG_EXPORT_ID);
        MyreportDTO myreportDTO = myreportMapper.toDto(updatedMyreport);

        restMyreportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myreportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myreportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
        Myreport testMyreport = myreportList.get(myreportList.size() - 1);
        assertThat(testMyreport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMyreport.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testMyreport.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);
        assertThat(testMyreport.getNameFile()).isEqualTo(UPDATED_NAME_FILE);
        assertThat(testMyreport.getNameFolder()).isEqualTo(UPDATED_NAME_FOLDER);
        assertThat(testMyreport.getConfigExportId()).isEqualTo(UPDATED_CONFIG_EXPORT_ID);
    }

    @Test
    @Transactional
    void putNonExistingMyreport() throws Exception {
        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();
        myreport.setId(count.incrementAndGet());

        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyreportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myreportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyreport() throws Exception {
        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();
        myreport.setId(count.incrementAndGet());

        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyreportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyreport() throws Exception {
        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();
        myreport.setId(count.incrementAndGet());

        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyreportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myreportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyreportWithPatch() throws Exception {
        // Initialize the database
        myreportRepository.saveAndFlush(myreport);

        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();

        // Update the myreport using partial update
        Myreport partialUpdatedMyreport = new Myreport();
        partialUpdatedMyreport.setId(myreport.getId());

        partialUpdatedMyreport.dateCreated(UPDATED_DATE_CREATED).nameFile(UPDATED_NAME_FILE).configExportId(UPDATED_CONFIG_EXPORT_ID);

        restMyreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyreport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyreport))
            )
            .andExpect(status().isOk());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
        Myreport testMyreport = myreportList.get(myreportList.size() - 1);
        assertThat(testMyreport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMyreport.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testMyreport.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
        assertThat(testMyreport.getNameFile()).isEqualTo(UPDATED_NAME_FILE);
        assertThat(testMyreport.getNameFolder()).isEqualTo(DEFAULT_NAME_FOLDER);
        assertThat(testMyreport.getConfigExportId()).isEqualTo(UPDATED_CONFIG_EXPORT_ID);
    }

    @Test
    @Transactional
    void fullUpdateMyreportWithPatch() throws Exception {
        // Initialize the database
        myreportRepository.saveAndFlush(myreport);

        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();

        // Update the myreport using partial update
        Myreport partialUpdatedMyreport = new Myreport();
        partialUpdatedMyreport.setId(myreport.getId());

        partialUpdatedMyreport
            .dateCreated(UPDATED_DATE_CREATED)
            .loginName(UPDATED_LOGIN_NAME)
            .loginId(UPDATED_LOGIN_ID)
            .nameFile(UPDATED_NAME_FILE)
            .nameFolder(UPDATED_NAME_FOLDER)
            .configExportId(UPDATED_CONFIG_EXPORT_ID);

        restMyreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyreport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyreport))
            )
            .andExpect(status().isOk());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
        Myreport testMyreport = myreportList.get(myreportList.size() - 1);
        assertThat(testMyreport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMyreport.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testMyreport.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);
        assertThat(testMyreport.getNameFile()).isEqualTo(UPDATED_NAME_FILE);
        assertThat(testMyreport.getNameFolder()).isEqualTo(UPDATED_NAME_FOLDER);
        assertThat(testMyreport.getConfigExportId()).isEqualTo(UPDATED_CONFIG_EXPORT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingMyreport() throws Exception {
        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();
        myreport.setId(count.incrementAndGet());

        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myreportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyreport() throws Exception {
        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();
        myreport.setId(count.incrementAndGet());

        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyreport() throws Exception {
        int databaseSizeBeforeUpdate = myreportRepository.findAll().size();
        myreport.setId(count.incrementAndGet());

        // Create the Myreport
        MyreportDTO myreportDTO = myreportMapper.toDto(myreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyreportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(myreportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Myreport in the database
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyreport() throws Exception {
        // Initialize the database
        myreportRepository.saveAndFlush(myreport);

        int databaseSizeBeforeDelete = myreportRepository.findAll().size();

        // Delete the myreport
        restMyreportMockMvc
            .perform(delete(ENTITY_API_URL_ID, myreport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Myreport> myreportList = myreportRepository.findAll();
        assertThat(myreportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
