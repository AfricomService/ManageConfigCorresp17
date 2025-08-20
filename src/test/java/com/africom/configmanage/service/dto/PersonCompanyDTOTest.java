package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonCompanyDTO.class);
        PersonCompanyDTO personCompanyDTO1 = new PersonCompanyDTO();
        personCompanyDTO1.setId(1L);
        PersonCompanyDTO personCompanyDTO2 = new PersonCompanyDTO();
        assertThat(personCompanyDTO1).isNotEqualTo(personCompanyDTO2);
        personCompanyDTO2.setId(personCompanyDTO1.getId());
        assertThat(personCompanyDTO1).isEqualTo(personCompanyDTO2);
        personCompanyDTO2.setId(2L);
        assertThat(personCompanyDTO1).isNotEqualTo(personCompanyDTO2);
        personCompanyDTO1.setId(null);
        assertThat(personCompanyDTO1).isNotEqualTo(personCompanyDTO2);
    }
}
