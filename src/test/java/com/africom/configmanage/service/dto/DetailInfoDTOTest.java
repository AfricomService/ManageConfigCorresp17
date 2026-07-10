package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailInfoDTO.class);
        DetailInfoDTO detailInfoDTO1 = new DetailInfoDTO();
        detailInfoDTO1.setId(1L);
        DetailInfoDTO detailInfoDTO2 = new DetailInfoDTO();
        assertThat(detailInfoDTO1).isNotEqualTo(detailInfoDTO2);
        detailInfoDTO2.setId(detailInfoDTO1.getId());
        assertThat(detailInfoDTO1).isEqualTo(detailInfoDTO2);
        detailInfoDTO2.setId(2L);
        assertThat(detailInfoDTO1).isNotEqualTo(detailInfoDTO2);
        detailInfoDTO1.setId(null);
        assertThat(detailInfoDTO1).isNotEqualTo(detailInfoDTO2);
    }
}
