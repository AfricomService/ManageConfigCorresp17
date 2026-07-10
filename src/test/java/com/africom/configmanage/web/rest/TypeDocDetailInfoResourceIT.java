package com.africom.configmanage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.africom.configmanage.IntegrationTest;
import com.africom.configmanage.domain.TypeDocDetailInfo;
import com.africom.configmanage.repository.TypeDocDetailInfoRepository;
import com.africom.configmanage.service.dto.TypeDocDetailInfoDTO;
import com.africom.configmanage.service.mapper.TypeDocDetailInfoMapper;
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
 * Integration tests for the {@link TypeDocDetailInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeDocDetailInfoResourceIT {

    private static final Long DEFAULT_TYPE_DOC_ID = 1L;
    private static final Long UPDATED_TYPE_DOC_ID = 2L;

    private static final Long DEFAULT_DETAIL_INFO_ID = 1L;
    private static final Long UPDATED_DETAIL_INFO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/type-doc-detail-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeDocDetailInfoRepository typeDocDetailInfoRepository;

    @Autowired
    private TypeDocDetailInfoMapper typeDocDetailInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeDocDetailInfoMockMvc;

    private TypeDocDetailInfo typeDocDetailInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeDocDetailInfo createEntity(EntityManager em) {
        TypeDocDetailInfo typeDocDetailInfo = new TypeDocDetailInfo().typeDocId(DEFAULT_TYPE_DOC_ID).detailInfoId(DEFAULT_DETAIL_INFO_ID);
        return typeDocDetailInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeDocDetailInfo createUpdatedEntity(EntityManager em) {
        TypeDocDetailInfo typeDocDetailInfo = new TypeDocDetailInfo().typeDocId(UPDATED_TYPE_DOC_ID).detailInfoId(UPDATED_DETAIL_INFO_ID);
        return typeDocDetailInfo;
    }

    @BeforeEach
    public void initTest() {
        typeDocDetailInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeCreate = typeDocDetailInfoRepository.findAll().size();
        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);
        restTypeDocDetailInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDocDetailInfo testTypeDocDetailInfo = typeDocDetailInfoList.get(typeDocDetailInfoList.size() - 1);
        assertThat(testTypeDocDetailInfo.getTypeDocId()).isEqualTo(DEFAULT_TYPE_DOC_ID);
        assertThat(testTypeDocDetailInfo.getDetailInfoId()).isEqualTo(DEFAULT_DETAIL_INFO_ID);
    }

    @Test
    @Transactional
    void createTypeDocDetailInfoWithExistingId() throws Exception {
        // Create the TypeDocDetailInfo with an existing ID
        typeDocDetailInfo.setId(1L);
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        int databaseSizeBeforeCreate = typeDocDetailInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDocDetailInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeDocDetailInfos() throws Exception {
        // Initialize the database
        typeDocDetailInfoRepository.saveAndFlush(typeDocDetailInfo);

        // Get all the typeDocDetailInfoList
        restTypeDocDetailInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDocDetailInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeDocId").value(hasItem(DEFAULT_TYPE_DOC_ID.intValue())))
            .andExpect(jsonPath("$.[*].detailInfoId").value(hasItem(DEFAULT_DETAIL_INFO_ID.intValue())));
    }

    @Test
    @Transactional
    void getTypeDocDetailInfo() throws Exception {
        // Initialize the database
        typeDocDetailInfoRepository.saveAndFlush(typeDocDetailInfo);

        // Get the typeDocDetailInfo
        restTypeDocDetailInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, typeDocDetailInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeDocDetailInfo.getId().intValue()))
            .andExpect(jsonPath("$.typeDocId").value(DEFAULT_TYPE_DOC_ID.intValue()))
            .andExpect(jsonPath("$.detailInfoId").value(DEFAULT_DETAIL_INFO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTypeDocDetailInfo() throws Exception {
        // Get the typeDocDetailInfo
        restTypeDocDetailInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeDocDetailInfo() throws Exception {
        // Initialize the database
        typeDocDetailInfoRepository.saveAndFlush(typeDocDetailInfo);

        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();

        // Update the typeDocDetailInfo
        TypeDocDetailInfo updatedTypeDocDetailInfo = typeDocDetailInfoRepository.findById(typeDocDetailInfo.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDocDetailInfo are not directly saved in db
        em.detach(updatedTypeDocDetailInfo);
        updatedTypeDocDetailInfo.typeDocId(UPDATED_TYPE_DOC_ID).detailInfoId(UPDATED_DETAIL_INFO_ID);
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(updatedTypeDocDetailInfo);

        restTypeDocDetailInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeDocDetailInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
        TypeDocDetailInfo testTypeDocDetailInfo = typeDocDetailInfoList.get(typeDocDetailInfoList.size() - 1);
        assertThat(testTypeDocDetailInfo.getTypeDocId()).isEqualTo(UPDATED_TYPE_DOC_ID);
        assertThat(testTypeDocDetailInfo.getDetailInfoId()).isEqualTo(UPDATED_DETAIL_INFO_ID);
    }

    @Test
    @Transactional
    void putNonExistingTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();
        typeDocDetailInfo.setId(count.incrementAndGet());

        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDocDetailInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeDocDetailInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();
        typeDocDetailInfo.setId(count.incrementAndGet());

        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeDocDetailInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();
        typeDocDetailInfo.setId(count.incrementAndGet());

        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeDocDetailInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeDocDetailInfoWithPatch() throws Exception {
        // Initialize the database
        typeDocDetailInfoRepository.saveAndFlush(typeDocDetailInfo);

        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();

        // Update the typeDocDetailInfo using partial update
        TypeDocDetailInfo partialUpdatedTypeDocDetailInfo = new TypeDocDetailInfo();
        partialUpdatedTypeDocDetailInfo.setId(typeDocDetailInfo.getId());

        restTypeDocDetailInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeDocDetailInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeDocDetailInfo))
            )
            .andExpect(status().isOk());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
        TypeDocDetailInfo testTypeDocDetailInfo = typeDocDetailInfoList.get(typeDocDetailInfoList.size() - 1);
        assertThat(testTypeDocDetailInfo.getTypeDocId()).isEqualTo(DEFAULT_TYPE_DOC_ID);
        assertThat(testTypeDocDetailInfo.getDetailInfoId()).isEqualTo(DEFAULT_DETAIL_INFO_ID);
    }

    @Test
    @Transactional
    void fullUpdateTypeDocDetailInfoWithPatch() throws Exception {
        // Initialize the database
        typeDocDetailInfoRepository.saveAndFlush(typeDocDetailInfo);

        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();

        // Update the typeDocDetailInfo using partial update
        TypeDocDetailInfo partialUpdatedTypeDocDetailInfo = new TypeDocDetailInfo();
        partialUpdatedTypeDocDetailInfo.setId(typeDocDetailInfo.getId());

        partialUpdatedTypeDocDetailInfo.typeDocId(UPDATED_TYPE_DOC_ID).detailInfoId(UPDATED_DETAIL_INFO_ID);

        restTypeDocDetailInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeDocDetailInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeDocDetailInfo))
            )
            .andExpect(status().isOk());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
        TypeDocDetailInfo testTypeDocDetailInfo = typeDocDetailInfoList.get(typeDocDetailInfoList.size() - 1);
        assertThat(testTypeDocDetailInfo.getTypeDocId()).isEqualTo(UPDATED_TYPE_DOC_ID);
        assertThat(testTypeDocDetailInfo.getDetailInfoId()).isEqualTo(UPDATED_DETAIL_INFO_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();
        typeDocDetailInfo.setId(count.incrementAndGet());

        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDocDetailInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeDocDetailInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();
        typeDocDetailInfo.setId(count.incrementAndGet());

        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeDocDetailInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeDocDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = typeDocDetailInfoRepository.findAll().size();
        typeDocDetailInfo.setId(count.incrementAndGet());

        // Create the TypeDocDetailInfo
        TypeDocDetailInfoDTO typeDocDetailInfoDTO = typeDocDetailInfoMapper.toDto(typeDocDetailInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeDocDetailInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeDocDetailInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeDocDetailInfo in the database
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeDocDetailInfo() throws Exception {
        // Initialize the database
        typeDocDetailInfoRepository.saveAndFlush(typeDocDetailInfo);

        int databaseSizeBeforeDelete = typeDocDetailInfoRepository.findAll().size();

        // Delete the typeDocDetailInfo
        restTypeDocDetailInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeDocDetailInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeDocDetailInfo> typeDocDetailInfoList = typeDocDetailInfoRepository.findAll();
        assertThat(typeDocDetailInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
