package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ModelNotifDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelNotifDTO.class);
        ModelNotifDTO modelNotifDTO1 = new ModelNotifDTO();
        modelNotifDTO1.setId(UUID.randomUUID());
        ModelNotifDTO modelNotifDTO2 = new ModelNotifDTO();
        assertThat(modelNotifDTO1).isNotEqualTo(modelNotifDTO2);
        modelNotifDTO2.setId(modelNotifDTO1.getId());
        assertThat(modelNotifDTO1).isEqualTo(modelNotifDTO2);
        modelNotifDTO2.setId(UUID.randomUUID());
        assertThat(modelNotifDTO1).isNotEqualTo(modelNotifDTO2);
        modelNotifDTO1.setId(null);
        assertThat(modelNotifDTO1).isNotEqualTo(modelNotifDTO2);
    }
}
