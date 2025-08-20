package com.africom.configmanage.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactCompanyDTO.class);
        ContactCompanyDTO contactCompanyDTO1 = new ContactCompanyDTO();
        contactCompanyDTO1.setId(1L);
        ContactCompanyDTO contactCompanyDTO2 = new ContactCompanyDTO();
        assertThat(contactCompanyDTO1).isNotEqualTo(contactCompanyDTO2);
        contactCompanyDTO2.setId(contactCompanyDTO1.getId());
        assertThat(contactCompanyDTO1).isEqualTo(contactCompanyDTO2);
        contactCompanyDTO2.setId(2L);
        assertThat(contactCompanyDTO1).isNotEqualTo(contactCompanyDTO2);
        contactCompanyDTO1.setId(null);
        assertThat(contactCompanyDTO1).isNotEqualTo(contactCompanyDTO2);
    }
}
