package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgacareDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgacareDTO.class);
        OrgacareDTO orgacareDTO1 = new OrgacareDTO();
        orgacareDTO1.setId(1L);
        OrgacareDTO orgacareDTO2 = new OrgacareDTO();
        assertThat(orgacareDTO1).isNotEqualTo(orgacareDTO2);
        orgacareDTO2.setId(orgacareDTO1.getId());
        assertThat(orgacareDTO1).isEqualTo(orgacareDTO2);
        orgacareDTO2.setId(2L);
        assertThat(orgacareDTO1).isNotEqualTo(orgacareDTO2);
        orgacareDTO1.setId(null);
        assertThat(orgacareDTO1).isNotEqualTo(orgacareDTO2);
    }
}
