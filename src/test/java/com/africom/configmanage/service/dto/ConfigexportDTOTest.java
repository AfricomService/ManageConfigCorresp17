package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigexportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigexportDTO.class);
        ConfigexportDTO configexportDTO1 = new ConfigexportDTO();
        configexportDTO1.setId(1L);
        ConfigexportDTO configexportDTO2 = new ConfigexportDTO();
        assertThat(configexportDTO1).isNotEqualTo(configexportDTO2);
        configexportDTO2.setId(configexportDTO1.getId());
        assertThat(configexportDTO1).isEqualTo(configexportDTO2);
        configexportDTO2.setId(2L);
        assertThat(configexportDTO1).isNotEqualTo(configexportDTO2);
        configexportDTO1.setId(null);
        assertThat(configexportDTO1).isNotEqualTo(configexportDTO2);
    }
}
