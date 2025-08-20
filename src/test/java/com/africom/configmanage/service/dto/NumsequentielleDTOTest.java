package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class NumsequentielleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumsequentielleDTO.class);
        NumsequentielleDTO numsequentielleDTO1 = new NumsequentielleDTO();
        numsequentielleDTO1.setId(UUID.randomUUID());
        NumsequentielleDTO numsequentielleDTO2 = new NumsequentielleDTO();
        assertThat(numsequentielleDTO1).isNotEqualTo(numsequentielleDTO2);
        numsequentielleDTO2.setId(numsequentielleDTO1.getId());
        assertThat(numsequentielleDTO1).isEqualTo(numsequentielleDTO2);
        numsequentielleDTO2.setId(UUID.randomUUID());
        assertThat(numsequentielleDTO1).isNotEqualTo(numsequentielleDTO2);
        numsequentielleDTO1.setId(null);
        assertThat(numsequentielleDTO1).isNotEqualTo(numsequentielleDTO2);
    }
}
