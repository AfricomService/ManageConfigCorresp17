package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeDocDetailInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDocDetailInfoDTO.class);
        TypeDocDetailInfoDTO typeDocDetailInfoDTO1 = new TypeDocDetailInfoDTO();
        typeDocDetailInfoDTO1.setId(1L);
        TypeDocDetailInfoDTO typeDocDetailInfoDTO2 = new TypeDocDetailInfoDTO();
        assertThat(typeDocDetailInfoDTO1).isNotEqualTo(typeDocDetailInfoDTO2);
        typeDocDetailInfoDTO2.setId(typeDocDetailInfoDTO1.getId());
        assertThat(typeDocDetailInfoDTO1).isEqualTo(typeDocDetailInfoDTO2);
        typeDocDetailInfoDTO2.setId(2L);
        assertThat(typeDocDetailInfoDTO1).isNotEqualTo(typeDocDetailInfoDTO2);
        typeDocDetailInfoDTO1.setId(null);
        assertThat(typeDocDetailInfoDTO1).isNotEqualTo(typeDocDetailInfoDTO2);
    }
}
