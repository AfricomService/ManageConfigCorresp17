package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypedocumentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypedocumentDTO.class);
        TypedocumentDTO typedocumentDTO1 = new TypedocumentDTO();
        typedocumentDTO1.setId(1L);
        TypedocumentDTO typedocumentDTO2 = new TypedocumentDTO();
        assertThat(typedocumentDTO1).isNotEqualTo(typedocumentDTO2);
        typedocumentDTO2.setId(typedocumentDTO1.getId());
        assertThat(typedocumentDTO1).isEqualTo(typedocumentDTO2);
        typedocumentDTO2.setId(2L);
        assertThat(typedocumentDTO1).isNotEqualTo(typedocumentDTO2);
        typedocumentDTO1.setId(null);
        assertThat(typedocumentDTO1).isNotEqualTo(typedocumentDTO2);
    }
}
