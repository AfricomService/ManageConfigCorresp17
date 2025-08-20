package com.africom.configmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.Typedocument;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.TypedocumentRepository;
import com.africom.configmanage.service.dto.TypedocumentDTO;
import com.africom.configmanage.service.mapper.TypedocumentMapper;
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
 * Integration tests for the {@link TypedocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypedocumentResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAUTDEST = "AAAAAAAAAA";
    private static final String UPDATED_DEFAUTDEST = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULTOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULTOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULTENCOPIE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULTENCOPIE = "BBBBBBBBBB";

    private static final UsualStatus DEFAULT_USUALSTATUS = UsualStatus.Actif;
    private static final UsualStatus UPDATED_USUALSTATUS = UsualStatus.Delated;

    private static final String ENTITY_API_URL = "/api/typedocuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypedocumentRepository typedocumentRepository;

    @Autowired
    private TypedocumentMapper typedocumentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypedocumentMockMvc;

    private Typedocument typedocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typedocument createEntity(EntityManager em) {
        Typedocument typedocument = new Typedocument()
            .nom(DEFAULT_NOM)
            .defautdest(DEFAULT_DEFAUTDEST)
            .defaultobject(DEFAULT_DEFAULTOBJECT)
            .defaultencopie(DEFAULT_DEFAULTENCOPIE)
            .usualstatus(DEFAULT_USUALSTATUS);
        return typedocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typedocument createUpdatedEntity(EntityManager em) {
        Typedocument typedocument = new Typedocument()
            .nom(UPDATED_NOM)
            .defautdest(UPDATED_DEFAUTDEST)
            .defaultobject(UPDATED_DEFAULTOBJECT)
            .defaultencopie(UPDATED_DEFAULTENCOPIE)
            .usualstatus(UPDATED_USUALSTATUS);
        return typedocument;
    }

    @BeforeEach
    public void initTest() {
        typedocument = createEntity(em);
    }

    @Test
    @Transactional
    void createTypedocument() throws Exception {
        int databaseSizeBeforeCreate = typedocumentRepository.findAll().size();
        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);
        restTypedocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeCreate + 1);
        Typedocument testTypedocument = typedocumentList.get(typedocumentList.size() - 1);
        assertThat(testTypedocument.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypedocument.getDefautdest()).isEqualTo(DEFAULT_DEFAUTDEST);
        assertThat(testTypedocument.getDefaultobject()).isEqualTo(DEFAULT_DEFAULTOBJECT);
        assertThat(testTypedocument.getDefaultencopie()).isEqualTo(DEFAULT_DEFAULTENCOPIE);
        assertThat(testTypedocument.getUsualstatus()).isEqualTo(DEFAULT_USUALSTATUS);
    }

    @Test
    @Transactional
    void createTypedocumentWithExistingId() throws Exception {
        // Create the Typedocument with an existing ID
        typedocument.setId(1L);
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        int databaseSizeBeforeCreate = typedocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypedocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypedocuments() throws Exception {
        // Initialize the database
        typedocumentRepository.saveAndFlush(typedocument);

        // Get all the typedocumentList
        restTypedocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typedocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].defautdest").value(hasItem(DEFAULT_DEFAUTDEST)))
            .andExpect(jsonPath("$.[*].defaultobject").value(hasItem(DEFAULT_DEFAULTOBJECT)))
            .andExpect(jsonPath("$.[*].defaultencopie").value(hasItem(DEFAULT_DEFAULTENCOPIE)))
            .andExpect(jsonPath("$.[*].usualstatus").value(hasItem(DEFAULT_USUALSTATUS.toString())));
    }

    @Test
    @Transactional
    void getTypedocument() throws Exception {
        // Initialize the database
        typedocumentRepository.saveAndFlush(typedocument);

        // Get the typedocument
        restTypedocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, typedocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typedocument.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.defautdest").value(DEFAULT_DEFAUTDEST))
            .andExpect(jsonPath("$.defaultobject").value(DEFAULT_DEFAULTOBJECT))
            .andExpect(jsonPath("$.defaultencopie").value(DEFAULT_DEFAULTENCOPIE))
            .andExpect(jsonPath("$.usualstatus").value(DEFAULT_USUALSTATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTypedocument() throws Exception {
        // Get the typedocument
        restTypedocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypedocument() throws Exception {
        // Initialize the database
        typedocumentRepository.saveAndFlush(typedocument);

        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();

        // Update the typedocument
        Typedocument updatedTypedocument = typedocumentRepository.findById(typedocument.getId()).get();
        // Disconnect from session so that the updates on updatedTypedocument are not directly saved in db
        em.detach(updatedTypedocument);
        updatedTypedocument
            .nom(UPDATED_NOM)
            .defautdest(UPDATED_DEFAUTDEST)
            .defaultobject(UPDATED_DEFAULTOBJECT)
            .defaultencopie(UPDATED_DEFAULTENCOPIE)
            .usualstatus(UPDATED_USUALSTATUS);
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(updatedTypedocument);

        restTypedocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typedocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
        Typedocument testTypedocument = typedocumentList.get(typedocumentList.size() - 1);
        assertThat(testTypedocument.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypedocument.getDefautdest()).isEqualTo(UPDATED_DEFAUTDEST);
        assertThat(testTypedocument.getDefaultobject()).isEqualTo(UPDATED_DEFAULTOBJECT);
        assertThat(testTypedocument.getDefaultencopie()).isEqualTo(UPDATED_DEFAULTENCOPIE);
        assertThat(testTypedocument.getUsualstatus()).isEqualTo(UPDATED_USUALSTATUS);
    }

    @Test
    @Transactional
    void putNonExistingTypedocument() throws Exception {
        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();
        typedocument.setId(count.incrementAndGet());

        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypedocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typedocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypedocument() throws Exception {
        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();
        typedocument.setId(count.incrementAndGet());

        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypedocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypedocument() throws Exception {
        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();
        typedocument.setId(count.incrementAndGet());

        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypedocumentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypedocumentWithPatch() throws Exception {
        // Initialize the database
        typedocumentRepository.saveAndFlush(typedocument);

        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();

        // Update the typedocument using partial update
        Typedocument partialUpdatedTypedocument = new Typedocument();
        partialUpdatedTypedocument.setId(typedocument.getId());

        partialUpdatedTypedocument.defautdest(UPDATED_DEFAUTDEST).defaultobject(UPDATED_DEFAULTOBJECT).usualstatus(UPDATED_USUALSTATUS);

        restTypedocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypedocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypedocument))
            )
            .andExpect(status().isOk());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
        Typedocument testTypedocument = typedocumentList.get(typedocumentList.size() - 1);
        assertThat(testTypedocument.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypedocument.getDefautdest()).isEqualTo(UPDATED_DEFAUTDEST);
        assertThat(testTypedocument.getDefaultobject()).isEqualTo(UPDATED_DEFAULTOBJECT);
        assertThat(testTypedocument.getDefaultencopie()).isEqualTo(DEFAULT_DEFAULTENCOPIE);
        assertThat(testTypedocument.getUsualstatus()).isEqualTo(UPDATED_USUALSTATUS);
    }

    @Test
    @Transactional
    void fullUpdateTypedocumentWithPatch() throws Exception {
        // Initialize the database
        typedocumentRepository.saveAndFlush(typedocument);

        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();

        // Update the typedocument using partial update
        Typedocument partialUpdatedTypedocument = new Typedocument();
        partialUpdatedTypedocument.setId(typedocument.getId());

        partialUpdatedTypedocument
            .nom(UPDATED_NOM)
            .defautdest(UPDATED_DEFAUTDEST)
            .defaultobject(UPDATED_DEFAULTOBJECT)
            .defaultencopie(UPDATED_DEFAULTENCOPIE)
            .usualstatus(UPDATED_USUALSTATUS);

        restTypedocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypedocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypedocument))
            )
            .andExpect(status().isOk());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
        Typedocument testTypedocument = typedocumentList.get(typedocumentList.size() - 1);
        assertThat(testTypedocument.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypedocument.getDefautdest()).isEqualTo(UPDATED_DEFAUTDEST);
        assertThat(testTypedocument.getDefaultobject()).isEqualTo(UPDATED_DEFAULTOBJECT);
        assertThat(testTypedocument.getDefaultencopie()).isEqualTo(UPDATED_DEFAULTENCOPIE);
        assertThat(testTypedocument.getUsualstatus()).isEqualTo(UPDATED_USUALSTATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTypedocument() throws Exception {
        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();
        typedocument.setId(count.incrementAndGet());

        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypedocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typedocumentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypedocument() throws Exception {
        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();
        typedocument.setId(count.incrementAndGet());

        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypedocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypedocument() throws Exception {
        int databaseSizeBeforeUpdate = typedocumentRepository.findAll().size();
        typedocument.setId(count.incrementAndGet());

        // Create the Typedocument
        TypedocumentDTO typedocumentDTO = typedocumentMapper.toDto(typedocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypedocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typedocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Typedocument in the database
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypedocument() throws Exception {
        // Initialize the database
        typedocumentRepository.saveAndFlush(typedocument);

        int databaseSizeBeforeDelete = typedocumentRepository.findAll().size();

        // Delete the typedocument
        restTypedocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, typedocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Typedocument> typedocumentList = typedocumentRepository.findAll();
        assertThat(typedocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
