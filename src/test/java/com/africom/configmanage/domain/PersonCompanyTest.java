package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonCompany.class);
        PersonCompany personCompany1 = new PersonCompany();
        personCompany1.setId(1L);
        PersonCompany personCompany2 = new PersonCompany();
        personCompany2.setId(personCompany1.getId());
        assertThat(personCompany1).isEqualTo(personCompany2);
        personCompany2.setId(2L);
        assertThat(personCompany1).isNotEqualTo(personCompany2);
        personCompany1.setId(null);
        assertThat(personCompany1).isNotEqualTo(personCompany2);
    }
}
