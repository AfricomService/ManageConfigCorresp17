//package com.africom.configmanage.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.africom.configmanage.IntegrationTest;
//import com.africom.configmanage.domain.DetailInfo;
//import com.africom.configmanage.repository.DetailInfoRepository;
//import com.africom.configmanage.service.dto.DetailInfoDTO;
//import com.africom.configmanage.service.mapper.DetailInfoMapper;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link DetailInfoResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class DetailInfoResourceIT {
//
//    private static final Boolean DEFAULT_STATUS = false;
//    private static final Boolean UPDATED_STATUS = true;
//
//    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
//    private static final String UPDATED_LABEL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CODE = "AAAAAAAAAA";
//    private static final String UPDATED_CODE = "BBBBBBBBBB";
//
//    private static final Boolean DEFAULT_REQUIRED = false;
//    private static final Boolean UPDATED_REQUIRED = true;
//
//    private static final String DEFAULT_INPUT_TYPE = "AAAAAAAAAA";
//    private static final String UPDATED_INPUT_TYPE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_MULTIPLE_CHOICE_OPTION = "AAAAAAAAAA";
//    private static final String UPDATED_MULTIPLE_CHOICE_OPTION = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/detail-infos";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private DetailInfoRepository detailInfoRepository;
//
//    @Autowired
//    private DetailInfoMapper detailInfoMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restDetailInfoMockMvc;
//
//    private DetailInfo detailInfo;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static DetailInfo createEntity(EntityManager em) {
//        DetailInfo detailInfo = new DetailInfo()
//            .status(DEFAULT_STATUS)
//            .label(DEFAULT_LABEL)
//            .code(DEFAULT_CODE)
//            .required(DEFAULT_REQUIRED)
//            .inputType(DEFAULT_INPUT_TYPE)
//            .multipleChoiceOption(DEFAULT_MULTIPLE_CHOICE_OPTION);
//        return detailInfo;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static DetailInfo createUpdatedEntity(EntityManager em) {
//        DetailInfo detailInfo = new DetailInfo()
//            .status(UPDATED_STATUS)
//            .label(UPDATED_LABEL)
//            .code(UPDATED_CODE)
//            .required(UPDATED_REQUIRED)
//            .inputType(UPDATED_INPUT_TYPE)
//            .multipleChoiceOption(UPDATED_MULTIPLE_CHOICE_OPTION);
//        return detailInfo;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        detailInfo = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createDetailInfo() throws Exception {
//        int databaseSizeBeforeCreate = detailInfoRepository.findAll().size();
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//        restDetailInfoMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailInfoDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeCreate + 1);
//        DetailInfo testDetailInfo = detailInfoList.get(detailInfoList.size() - 1);
//        assertThat(testDetailInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testDetailInfo.getLabel()).isEqualTo(DEFAULT_LABEL);
//        assertThat(testDetailInfo.getCode()).isEqualTo(DEFAULT_CODE);
//        assertThat(testDetailInfo.getRequired()).isEqualTo(DEFAULT_REQUIRED);
//        assertThat(testDetailInfo.getInputType()).isEqualTo(DEFAULT_INPUT_TYPE);
//        assertThat(testDetailInfo.getMultipleChoiceOption()).isEqualTo(DEFAULT_MULTIPLE_CHOICE_OPTION);
//    }
//
//    @Test
//    @Transactional
//    void createDetailInfoWithExistingId() throws Exception {
//        // Create the DetailInfo with an existing ID
//        detailInfo.setId(1L);
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        int databaseSizeBeforeCreate = detailInfoRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restDetailInfoMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailInfoDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllDetailInfos() throws Exception {
//        // Initialize the database
//        detailInfoRepository.saveAndFlush(detailInfo);
//
//        // Get all the detailInfoList
//        restDetailInfoMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(detailInfo.getId().intValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
//            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
//            .andExpect(jsonPath("$.[*].required").value(hasItem(DEFAULT_REQUIRED.booleanValue())))
//            .andExpect(jsonPath("$.[*].inputType").value(hasItem(DEFAULT_INPUT_TYPE)))
//            .andExpect(jsonPath("$.[*].multipleChoiceOption").value(hasItem(DEFAULT_MULTIPLE_CHOICE_OPTION)));
//    }
//
//    @Test
//    @Transactional
//    void getDetailInfo() throws Exception {
//        // Initialize the database
//        detailInfoRepository.saveAndFlush(detailInfo);
//
//        // Get the detailInfo
//        restDetailInfoMockMvc
//            .perform(get(ENTITY_API_URL_ID, detailInfo.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(detailInfo.getId().intValue()))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
//            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
//            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
//            .andExpect(jsonPath("$.required").value(DEFAULT_REQUIRED.booleanValue()))
//            .andExpect(jsonPath("$.inputType").value(DEFAULT_INPUT_TYPE))
//            .andExpect(jsonPath("$.multipleChoiceOption").value(DEFAULT_MULTIPLE_CHOICE_OPTION));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingDetailInfo() throws Exception {
//        // Get the detailInfo
//        restDetailInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewDetailInfo() throws Exception {
//        // Initialize the database
//        detailInfoRepository.saveAndFlush(detailInfo);
//
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//
//        // Update the detailInfo
//        DetailInfo updatedDetailInfo = detailInfoRepository.findById(detailInfo.getId()).get();
//        // Disconnect from session so that the updates on updatedDetailInfo are not directly saved in db
//        em.detach(updatedDetailInfo);
//        updatedDetailInfo
//            .status(UPDATED_STATUS)
//            .label(UPDATED_LABEL)
//            .code(UPDATED_CODE)
//            .required(UPDATED_REQUIRED)
//            .inputType(UPDATED_INPUT_TYPE)
//            .multipleChoiceOption(UPDATED_MULTIPLE_CHOICE_OPTION);
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(updatedDetailInfo);
//
//        restDetailInfoMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, detailInfoDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(detailInfoDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//        DetailInfo testDetailInfo = detailInfoList.get(detailInfoList.size() - 1);
//        assertThat(testDetailInfo.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testDetailInfo.getLabel()).isEqualTo(UPDATED_LABEL);
//        assertThat(testDetailInfo.getCode()).isEqualTo(UPDATED_CODE);
//        assertThat(testDetailInfo.getRequired()).isEqualTo(UPDATED_REQUIRED);
//        assertThat(testDetailInfo.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
//        assertThat(testDetailInfo.getMultipleChoiceOption()).isEqualTo(UPDATED_MULTIPLE_CHOICE_OPTION);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingDetailInfo() throws Exception {
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//        detailInfo.setId(count.incrementAndGet());
//
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restDetailInfoMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, detailInfoDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(detailInfoDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchDetailInfo() throws Exception {
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//        detailInfo.setId(count.incrementAndGet());
//
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDetailInfoMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(detailInfoDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamDetailInfo() throws Exception {
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//        detailInfo.setId(count.incrementAndGet());
//
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDetailInfoMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailInfoDTO)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateDetailInfoWithPatch() throws Exception {
//        // Initialize the database
//        detailInfoRepository.saveAndFlush(detailInfo);
//
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//
//        // Update the detailInfo using partial update
//        DetailInfo partialUpdatedDetailInfo = new DetailInfo();
//        partialUpdatedDetailInfo.setId(detailInfo.getId());
//
//        partialUpdatedDetailInfo.label(UPDATED_LABEL).multipleChoiceOption(UPDATED_MULTIPLE_CHOICE_OPTION);
//
//        restDetailInfoMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedDetailInfo.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailInfo))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//        DetailInfo testDetailInfo = detailInfoList.get(detailInfoList.size() - 1);
//        assertThat(testDetailInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testDetailInfo.getLabel()).isEqualTo(UPDATED_LABEL);
//        assertThat(testDetailInfo.getCode()).isEqualTo(DEFAULT_CODE);
//        assertThat(testDetailInfo.getRequired()).isEqualTo(DEFAULT_REQUIRED);
//        assertThat(testDetailInfo.getInputType()).isEqualTo(DEFAULT_INPUT_TYPE);
//        assertThat(testDetailInfo.getMultipleChoiceOption()).isEqualTo(UPDATED_MULTIPLE_CHOICE_OPTION);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateDetailInfoWithPatch() throws Exception {
//        // Initialize the database
//        detailInfoRepository.saveAndFlush(detailInfo);
//
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//
//        // Update the detailInfo using partial update
//        DetailInfo partialUpdatedDetailInfo = new DetailInfo();
//        partialUpdatedDetailInfo.setId(detailInfo.getId());
//
//        partialUpdatedDetailInfo
//            .status(UPDATED_STATUS)
//            .label(UPDATED_LABEL)
//            .code(UPDATED_CODE)
//            .required(UPDATED_REQUIRED)
//            .inputType(UPDATED_INPUT_TYPE)
//            .multipleChoiceOption(UPDATED_MULTIPLE_CHOICE_OPTION);
//
//        restDetailInfoMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedDetailInfo.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailInfo))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//        DetailInfo testDetailInfo = detailInfoList.get(detailInfoList.size() - 1);
//        assertThat(testDetailInfo.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testDetailInfo.getLabel()).isEqualTo(UPDATED_LABEL);
//        assertThat(testDetailInfo.getCode()).isEqualTo(UPDATED_CODE);
//        assertThat(testDetailInfo.getRequired()).isEqualTo(UPDATED_REQUIRED);
//        assertThat(testDetailInfo.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
//        assertThat(testDetailInfo.getMultipleChoiceOption()).isEqualTo(UPDATED_MULTIPLE_CHOICE_OPTION);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingDetailInfo() throws Exception {
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//        detailInfo.setId(count.incrementAndGet());
//
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restDetailInfoMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, detailInfoDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(detailInfoDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchDetailInfo() throws Exception {
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//        detailInfo.setId(count.incrementAndGet());
//
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDetailInfoMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(detailInfoDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamDetailInfo() throws Exception {
//        int databaseSizeBeforeUpdate = detailInfoRepository.findAll().size();
//        detailInfo.setId(count.incrementAndGet());
//
//        // Create the DetailInfo
//        DetailInfoDTO detailInfoDTO = detailInfoMapper.toDto(detailInfo);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDetailInfoMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailInfoDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the DetailInfo in the database
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteDetailInfo() throws Exception {
//        // Initialize the database
//        detailInfoRepository.saveAndFlush(detailInfo);
//
//        int databaseSizeBeforeDelete = detailInfoRepository.findAll().size();
//
//        // Delete the detailInfo
//        restDetailInfoMockMvc
//            .perform(delete(ENTITY_API_URL_ID, detailInfo.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<DetailInfo> detailInfoList = detailInfoRepository.findAll();
//        assertThat(detailInfoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
