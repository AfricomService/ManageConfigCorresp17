package com.africom.configmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import com.africom.configmanage.repository.PersonCompanyRepository;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import com.africom.configmanage.service.mapper.PersonCompanyMapper;
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
 * Integration tests for the {@link PersonCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonCompanyResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

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

    private static final String DEFAULT_WHATSAPP = "AAAAAAAAAA";
    private static final String UPDATED_WHATSAPP = "BBBBBBBBBB";

    private static final UsualStatus DEFAULT_ETAT = UsualStatus.Actif;
    private static final UsualStatus UPDATED_ETAT = UsualStatus.Delated;

    private static final String ENTITY_API_URL = "/api/person-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonCompanyRepository personCompanyRepository;

    @Autowired
    private PersonCompanyMapper personCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonCompanyMockMvc;

    private PersonCompany personCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonCompany createEntity(EntityManager em) {
        PersonCompany personCompany = new PersonCompany()
            .nom(DEFAULT_NOM)
            .pays(DEFAULT_PAYS)
            .adresse(DEFAULT_ADRESSE)
            .tel(DEFAULT_TEL)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .whatsapp(DEFAULT_WHATSAPP)
            .etat(DEFAULT_ETAT);
        return personCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonCompany createUpdatedEntity(EntityManager em) {
        PersonCompany personCompany = new PersonCompany()
            .nom(UPDATED_NOM)
            .pays(UPDATED_PAYS)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .whatsapp(UPDATED_WHATSAPP)
            .etat(UPDATED_ETAT);
        return personCompany;
    }

    @BeforeEach
    public void initTest() {
        personCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonCompany() throws Exception {
        int databaseSizeBeforeCreate = personCompanyRepository.findAll().size();
        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);
        restPersonCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        PersonCompany testPersonCompany = personCompanyList.get(personCompanyList.size() - 1);
        assertThat(testPersonCompany.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonCompany.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testPersonCompany.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPersonCompany.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testPersonCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testPersonCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonCompany.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testPersonCompany.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createPersonCompanyWithExistingId() throws Exception {
        // Create the PersonCompany with an existing ID
        personCompany.setId(1L);
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        int databaseSizeBeforeCreate = personCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personCompanyRepository.findAll().size();
        // set the field null
        personCompany.setNom(null);

        // Create the PersonCompany, which fails.
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        restPersonCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonCompanies() throws Exception {
        // Initialize the database
        personCompanyRepository.saveAndFlush(personCompany);

        // Get all the personCompanyList
        restPersonCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    void getPersonCompany() throws Exception {
        // Initialize the database
        personCompanyRepository.saveAndFlush(personCompany);

        // Get the personCompany
        restPersonCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, personCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personCompany.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonCompany() throws Exception {
        // Get the personCompany
        restPersonCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonCompany() throws Exception {
        // Initialize the database
        personCompanyRepository.saveAndFlush(personCompany);

        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();

        // Update the personCompany
        PersonCompany updatedPersonCompany = personCompanyRepository.findById(personCompany.getId()).get();
        // Disconnect from session so that the updates on updatedPersonCompany are not directly saved in db
        em.detach(updatedPersonCompany);
        updatedPersonCompany
            .nom(UPDATED_NOM)
            .pays(UPDATED_PAYS)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .whatsapp(UPDATED_WHATSAPP)
            .etat(UPDATED_ETAT);
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(updatedPersonCompany);

        restPersonCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
        PersonCompany testPersonCompany = personCompanyList.get(personCompanyList.size() - 1);
        assertThat(testPersonCompany.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonCompany.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testPersonCompany.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPersonCompany.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testPersonCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testPersonCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonCompany.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testPersonCompany.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingPersonCompany() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();
        personCompany.setId(count.incrementAndGet());

        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonCompany() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();
        personCompany.setId(count.incrementAndGet());

        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonCompany() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();
        personCompany.setId(count.incrementAndGet());

        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonCompanyWithPatch() throws Exception {
        // Initialize the database
        personCompanyRepository.saveAndFlush(personCompany);

        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();

        // Update the personCompany using partial update
        PersonCompany partialUpdatedPersonCompany = new PersonCompany();
        partialUpdatedPersonCompany.setId(personCompany.getId());

        partialUpdatedPersonCompany.nom(UPDATED_NOM).pays(UPDATED_PAYS).tel(UPDATED_TEL).whatsapp(UPDATED_WHATSAPP).etat(UPDATED_ETAT);

        restPersonCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonCompany))
            )
            .andExpect(status().isOk());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
        PersonCompany testPersonCompany = personCompanyList.get(personCompanyList.size() - 1);
        assertThat(testPersonCompany.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonCompany.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testPersonCompany.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPersonCompany.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testPersonCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testPersonCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonCompany.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testPersonCompany.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void fullUpdatePersonCompanyWithPatch() throws Exception {
        // Initialize the database
        personCompanyRepository.saveAndFlush(personCompany);

        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();

        // Update the personCompany using partial update
        PersonCompany partialUpdatedPersonCompany = new PersonCompany();
        partialUpdatedPersonCompany.setId(personCompany.getId());

        partialUpdatedPersonCompany
            .nom(UPDATED_NOM)
            .pays(UPDATED_PAYS)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .whatsapp(UPDATED_WHATSAPP)
            .etat(UPDATED_ETAT);

        restPersonCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonCompany))
            )
            .andExpect(status().isOk());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
        PersonCompany testPersonCompany = personCompanyList.get(personCompanyList.size() - 1);
        assertThat(testPersonCompany.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonCompany.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testPersonCompany.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPersonCompany.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testPersonCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testPersonCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonCompany.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testPersonCompany.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingPersonCompany() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();
        personCompany.setId(count.incrementAndGet());

        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonCompany() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();
        personCompany.setId(count.incrementAndGet());

        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonCompany() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyRepository.findAll().size();
        personCompany.setId(count.incrementAndGet());

        // Create the PersonCompany
        PersonCompanyDTO personCompanyDTO = personCompanyMapper.toDto(personCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonCompany in the database
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonCompany() throws Exception {
        // Initialize the database
        personCompanyRepository.saveAndFlush(personCompany);

        int databaseSizeBeforeDelete = personCompanyRepository.findAll().size();

        // Delete the personCompany
        restPersonCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, personCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonCompany> personCompanyList = personCompanyRepository.findAll();
        assertThat(personCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
