package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactCompany.class);
        ContactCompany contactCompany1 = new ContactCompany();
        contactCompany1.setId(1L);
        ContactCompany contactCompany2 = new ContactCompany();
        contactCompany2.setId(contactCompany1.getId());
        assertThat(contactCompany1).isEqualTo(contactCompany2);
        contactCompany2.setId(2L);
        assertThat(contactCompany1).isNotEqualTo(contactCompany2);
        contactCompany1.setId(null);
        assertThat(contactCompany1).isNotEqualTo(contactCompany2);
    }
}
