package com.africom.configmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.ContactCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.ContactCompanyRepository;
import com.africom.configmanage.service.dto.ContactCompanyDTO;
import com.africom.configmanage.service.mapper.ContactCompanyMapper;
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
 * Integration tests for the {@link ContactCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactCompanyResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOMABREGE = "AAAAAAAAAA";
    private static final String UPDATED_NOMABREGE = "BBBBBBBBBB";

    private static final String DEFAULT_MATRNE = "AAAAAAAAAA";
    private static final String UPDATED_MATRNE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBPAGE = "AAAAAAAAAA";
    private static final String UPDATED_WEBPAGE = "BBBBBBBBBB";

    private static final UsualStatus DEFAULT_ETAT = UsualStatus.Actif;
    private static final UsualStatus UPDATED_ETAT = UsualStatus.Delated;

    private static final String ENTITY_API_URL = "/api/contact-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactCompanyRepository contactCompanyRepository;

    @Autowired
    private ContactCompanyMapper contactCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactCompanyMockMvc;

    private ContactCompany contactCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactCompany createEntity(EntityManager em) {
        ContactCompany contactCompany = new ContactCompany()
            .nom(DEFAULT_NOM)
            .nomabrege(DEFAULT_NOMABREGE)
            .matrne(DEFAULT_MATRNE)
            .pays(DEFAULT_PAYS)
            .adresse(DEFAULT_ADRESSE)
            .tel(DEFAULT_TEL)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .webpage(DEFAULT_WEBPAGE)
            .etat(DEFAULT_ETAT);
        return contactCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactCompany createUpdatedEntity(EntityManager em) {
        ContactCompany contactCompany = new ContactCompany()
            .nom(UPDATED_NOM)
            .nomabrege(UPDATED_NOMABREGE)
            .matrne(UPDATED_MATRNE)
            .pays(UPDATED_PAYS)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .webpage(UPDATED_WEBPAGE)
            .etat(UPDATED_ETAT);
        return contactCompany;
    }

    @BeforeEach
    public void initTest() {
        contactCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createContactCompany() throws Exception {
        int databaseSizeBeforeCreate = contactCompanyRepository.findAll().size();
        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);
        restContactCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        ContactCompany testContactCompany = contactCompanyList.get(contactCompanyList.size() - 1);
        assertThat(testContactCompany.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testContactCompany.getNomabrege()).isEqualTo(DEFAULT_NOMABREGE);
        assertThat(testContactCompany.getMatrne()).isEqualTo(DEFAULT_MATRNE);
        assertThat(testContactCompany.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testContactCompany.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testContactCompany.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testContactCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testContactCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactCompany.getWebpage()).isEqualTo(DEFAULT_WEBPAGE);
        assertThat(testContactCompany.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createContactCompanyWithExistingId() throws Exception {
        // Create the ContactCompany with an existing ID
        contactCompany.setId(1L);
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        int databaseSizeBeforeCreate = contactCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactCompanyRepository.findAll().size();
        // set the field null
        contactCompany.setNom(null);

        // Create the ContactCompany, which fails.
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        restContactCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactCompanies() throws Exception {
        // Initialize the database
        contactCompanyRepository.saveAndFlush(contactCompany);

        // Get all the contactCompanyList
        restContactCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomabrege").value(hasItem(DEFAULT_NOMABREGE)))
            .andExpect(jsonPath("$.[*].matrne").value(hasItem(DEFAULT_MATRNE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webpage").value(hasItem(DEFAULT_WEBPAGE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    void getContactCompany() throws Exception {
        // Initialize the database
        contactCompanyRepository.saveAndFlush(contactCompany);

        // Get the contactCompany
        restContactCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, contactCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactCompany.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomabrege").value(DEFAULT_NOMABREGE))
            .andExpect(jsonPath("$.matrne").value(DEFAULT_MATRNE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.webpage").value(DEFAULT_WEBPAGE))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContactCompany() throws Exception {
        // Get the contactCompany
        restContactCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContactCompany() throws Exception {
        // Initialize the database
        contactCompanyRepository.saveAndFlush(contactCompany);

        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();

        // Update the contactCompany
        ContactCompany updatedContactCompany = contactCompanyRepository.findById(contactCompany.getId()).get();
        // Disconnect from session so that the updates on updatedContactCompany are not directly saved in db
        em.detach(updatedContactCompany);
        updatedContactCompany
            .nom(UPDATED_NOM)
            .nomabrege(UPDATED_NOMABREGE)
            .matrne(UPDATED_MATRNE)
            .pays(UPDATED_PAYS)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .webpage(UPDATED_WEBPAGE)
            .etat(UPDATED_ETAT);
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(updatedContactCompany);

        restContactCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
        ContactCompany testContactCompany = contactCompanyList.get(contactCompanyList.size() - 1);
        assertThat(testContactCompany.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testContactCompany.getNomabrege()).isEqualTo(UPDATED_NOMABREGE);
        assertThat(testContactCompany.getMatrne()).isEqualTo(UPDATED_MATRNE);
        assertThat(testContactCompany.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testContactCompany.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testContactCompany.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testContactCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testContactCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactCompany.getWebpage()).isEqualTo(UPDATED_WEBPAGE);
        assertThat(testContactCompany.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingContactCompany() throws Exception {
        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();
        contactCompany.setId(count.incrementAndGet());

        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactCompany() throws Exception {
        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();
        contactCompany.setId(count.incrementAndGet());

        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactCompany() throws Exception {
        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();
        contactCompany.setId(count.incrementAndGet());

        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactCompanyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactCompanyWithPatch() throws Exception {
        // Initialize the database
        contactCompanyRepository.saveAndFlush(contactCompany);

        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();

        // Update the contactCompany using partial update
        ContactCompany partialUpdatedContactCompany = new ContactCompany();
        partialUpdatedContactCompany.setId(contactCompany.getId());

        partialUpdatedContactCompany
            .nom(UPDATED_NOM)
            .nomabrege(UPDATED_NOMABREGE)
            .matrne(UPDATED_MATRNE)
            .pays(UPDATED_PAYS)
            .email(UPDATED_EMAIL)
            .webpage(UPDATED_WEBPAGE)
            .etat(UPDATED_ETAT);

        restContactCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactCompany))
            )
            .andExpect(status().isOk());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
        ContactCompany testContactCompany = contactCompanyList.get(contactCompanyList.size() - 1);
        assertThat(testContactCompany.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testContactCompany.getNomabrege()).isEqualTo(UPDATED_NOMABREGE);
        assertThat(testContactCompany.getMatrne()).isEqualTo(UPDATED_MATRNE);
        assertThat(testContactCompany.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testContactCompany.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testContactCompany.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testContactCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testContactCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactCompany.getWebpage()).isEqualTo(UPDATED_WEBPAGE);
        assertThat(testContactCompany.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateContactCompanyWithPatch() throws Exception {
        // Initialize the database
        contactCompanyRepository.saveAndFlush(contactCompany);

        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();

        // Update the contactCompany using partial update
        ContactCompany partialUpdatedContactCompany = new ContactCompany();
        partialUpdatedContactCompany.setId(contactCompany.getId());

        partialUpdatedContactCompany
            .nom(UPDATED_NOM)
            .nomabrege(UPDATED_NOMABREGE)
            .matrne(UPDATED_MATRNE)
            .pays(UPDATED_PAYS)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .webpage(UPDATED_WEBPAGE)
            .etat(UPDATED_ETAT);

        restContactCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactCompany))
            )
            .andExpect(status().isOk());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
        ContactCompany testContactCompany = contactCompanyList.get(contactCompanyList.size() - 1);
        assertThat(testContactCompany.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testContactCompany.getNomabrege()).isEqualTo(UPDATED_NOMABREGE);
        assertThat(testContactCompany.getMatrne()).isEqualTo(UPDATED_MATRNE);
        assertThat(testContactCompany.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testContactCompany.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testContactCompany.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testContactCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testContactCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactCompany.getWebpage()).isEqualTo(UPDATED_WEBPAGE);
        assertThat(testContactCompany.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingContactCompany() throws Exception {
        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();
        contactCompany.setId(count.incrementAndGet());

        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactCompany() throws Exception {
        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();
        contactCompany.setId(count.incrementAndGet());

        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactCompany() throws Exception {
        int databaseSizeBeforeUpdate = contactCompanyRepository.findAll().size();
        contactCompany.setId(count.incrementAndGet());

        // Create the ContactCompany
        ContactCompanyDTO contactCompanyDTO = contactCompanyMapper.toDto(contactCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactCompany in the database
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactCompany() throws Exception {
        // Initialize the database
        contactCompanyRepository.saveAndFlush(contactCompany);

        int databaseSizeBeforeDelete = contactCompanyRepository.findAll().size();

        // Delete the contactCompany
        restContactCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactCompany> contactCompanyList = contactCompanyRepository.findAll();
        assertThat(contactCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
