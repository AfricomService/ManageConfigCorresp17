package com.africom.configmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.Orgacare;
import com.africom.configmanage.repository.OrgacareRepository;
import com.africom.configmanage.service.dto.OrgacareDTO;
import com.africom.configmanage.service.mapper.OrgacareMapper;
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
 * Integration tests for the {@link OrgacareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgacareResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIGRAMME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIGRAMME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ORGA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGA_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/orgacares";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgacareRepository orgacareRepository;

    @Autowired
    private OrgacareMapper orgacareMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgacareMockMvc;

    private Orgacare orgacare;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orgacare createEntity(EntityManager em) {
        Orgacare orgacare = new Orgacare()
            .company(DEFAULT_COMPANY)
            .organigramme(DEFAULT_ORGANIGRAMME)
            .status(DEFAULT_STATUS)
            .orgaCode(DEFAULT_ORGA_CODE);
        return orgacare;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orgacare createUpdatedEntity(EntityManager em) {
        Orgacare orgacare = new Orgacare()
            .company(UPDATED_COMPANY)
            .organigramme(UPDATED_ORGANIGRAMME)
            .status(UPDATED_STATUS)
            .orgaCode(UPDATED_ORGA_CODE);
        return orgacare;
    }

    @BeforeEach
    public void initTest() {
        orgacare = createEntity(em);
    }

    @Test
    @Transactional
    void createOrgacare() throws Exception {
        int databaseSizeBeforeCreate = orgacareRepository.findAll().size();
        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);
        restOrgacareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgacareDTO)))
            .andExpect(status().isCreated());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeCreate + 1);
        Orgacare testOrgacare = orgacareList.get(orgacareList.size() - 1);
        assertThat(testOrgacare.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testOrgacare.getOrganigramme()).isEqualTo(DEFAULT_ORGANIGRAMME);
        assertThat(testOrgacare.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrgacare.getOrgaCode()).isEqualTo(DEFAULT_ORGA_CODE);
    }

    @Test
    @Transactional
    void createOrgacareWithExistingId() throws Exception {
        // Create the Orgacare with an existing ID
        orgacare.setId(1L);
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        int databaseSizeBeforeCreate = orgacareRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgacareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgacareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrgacares() throws Exception {
        // Initialize the database
        orgacareRepository.saveAndFlush(orgacare);

        // Get all the orgacareList
        restOrgacareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgacare.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].organigramme").value(hasItem(DEFAULT_ORGANIGRAMME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orgaCode").value(hasItem(DEFAULT_ORGA_CODE)));
    }

    @Test
    @Transactional
    void getOrgacare() throws Exception {
        // Initialize the database
        orgacareRepository.saveAndFlush(orgacare);

        // Get the orgacare
        restOrgacareMockMvc
            .perform(get(ENTITY_API_URL_ID, orgacare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgacare.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.organigramme").value(DEFAULT_ORGANIGRAMME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.orgaCode").value(DEFAULT_ORGA_CODE));
    }

    @Test
    @Transactional
    void getNonExistingOrgacare() throws Exception {
        // Get the orgacare
        restOrgacareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrgacare() throws Exception {
        // Initialize the database
        orgacareRepository.saveAndFlush(orgacare);

        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();

        // Update the orgacare
        Orgacare updatedOrgacare = orgacareRepository.findById(orgacare.getId()).get();
        // Disconnect from session so that the updates on updatedOrgacare are not directly saved in db
        em.detach(updatedOrgacare);
        updatedOrgacare.company(UPDATED_COMPANY).organigramme(UPDATED_ORGANIGRAMME).status(UPDATED_STATUS).orgaCode(UPDATED_ORGA_CODE);
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(updatedOrgacare);

        restOrgacareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgacareDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgacareDTO))
            )
            .andExpect(status().isOk());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
        Orgacare testOrgacare = orgacareList.get(orgacareList.size() - 1);
        assertThat(testOrgacare.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testOrgacare.getOrganigramme()).isEqualTo(UPDATED_ORGANIGRAMME);
        assertThat(testOrgacare.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrgacare.getOrgaCode()).isEqualTo(UPDATED_ORGA_CODE);
    }

    @Test
    @Transactional
    void putNonExistingOrgacare() throws Exception {
        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();
        orgacare.setId(count.incrementAndGet());

        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgacareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgacareDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgacareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrgacare() throws Exception {
        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();
        orgacare.setId(count.incrementAndGet());

        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgacareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgacareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrgacare() throws Exception {
        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();
        orgacare.setId(count.incrementAndGet());

        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgacareMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgacareDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgacareWithPatch() throws Exception {
        // Initialize the database
        orgacareRepository.saveAndFlush(orgacare);

        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();

        // Update the orgacare using partial update
        Orgacare partialUpdatedOrgacare = new Orgacare();
        partialUpdatedOrgacare.setId(orgacare.getId());

        partialUpdatedOrgacare.status(UPDATED_STATUS).orgaCode(UPDATED_ORGA_CODE);

        restOrgacareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgacare.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgacare))
            )
            .andExpect(status().isOk());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
        Orgacare testOrgacare = orgacareList.get(orgacareList.size() - 1);
        assertThat(testOrgacare.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testOrgacare.getOrganigramme()).isEqualTo(DEFAULT_ORGANIGRAMME);
        assertThat(testOrgacare.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrgacare.getOrgaCode()).isEqualTo(UPDATED_ORGA_CODE);
    }

    @Test
    @Transactional
    void fullUpdateOrgacareWithPatch() throws Exception {
        // Initialize the database
        orgacareRepository.saveAndFlush(orgacare);

        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();

        // Update the orgacare using partial update
        Orgacare partialUpdatedOrgacare = new Orgacare();
        partialUpdatedOrgacare.setId(orgacare.getId());

        partialUpdatedOrgacare
            .company(UPDATED_COMPANY)
            .organigramme(UPDATED_ORGANIGRAMME)
            .status(UPDATED_STATUS)
            .orgaCode(UPDATED_ORGA_CODE);

        restOrgacareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgacare.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgacare))
            )
            .andExpect(status().isOk());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
        Orgacare testOrgacare = orgacareList.get(orgacareList.size() - 1);
        assertThat(testOrgacare.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testOrgacare.getOrganigramme()).isEqualTo(UPDATED_ORGANIGRAMME);
        assertThat(testOrgacare.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrgacare.getOrgaCode()).isEqualTo(UPDATED_ORGA_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingOrgacare() throws Exception {
        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();
        orgacare.setId(count.incrementAndGet());

        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgacareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgacareDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgacareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrgacare() throws Exception {
        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();
        orgacare.setId(count.incrementAndGet());

        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgacareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgacareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrgacare() throws Exception {
        int databaseSizeBeforeUpdate = orgacareRepository.findAll().size();
        orgacare.setId(count.incrementAndGet());

        // Create the Orgacare
        OrgacareDTO orgacareDTO = orgacareMapper.toDto(orgacare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgacareMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orgacareDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orgacare in the database
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrgacare() throws Exception {
        // Initialize the database
        orgacareRepository.saveAndFlush(orgacare);

        int databaseSizeBeforeDelete = orgacareRepository.findAll().size();

        // Delete the orgacare
        restOrgacareMockMvc
            .perform(delete(ENTITY_API_URL_ID, orgacare.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orgacare> orgacareList = orgacareRepository.findAll();
        assertThat(orgacareList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
