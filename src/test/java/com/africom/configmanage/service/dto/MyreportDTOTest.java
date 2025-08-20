package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MyreportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyreportDTO.class);
        MyreportDTO myreportDTO1 = new MyreportDTO();
        myreportDTO1.setId(1L);
        MyreportDTO myreportDTO2 = new MyreportDTO();
        assertThat(myreportDTO1).isNotEqualTo(myreportDTO2);
        myreportDTO2.setId(myreportDTO1.getId());
        assertThat(myreportDTO1).isEqualTo(myreportDTO2);
        myreportDTO2.setId(2L);
        assertThat(myreportDTO1).isNotEqualTo(myreportDTO2);
        myreportDTO1.setId(null);
        assertThat(myreportDTO1).isNotEqualTo(myreportDTO2);
    }
}
