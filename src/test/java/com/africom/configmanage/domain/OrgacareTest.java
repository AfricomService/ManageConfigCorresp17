package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgacareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orgacare.class);
        Orgacare orgacare1 = new Orgacare();
        orgacare1.setId(1L);
        Orgacare orgacare2 = new Orgacare();
        orgacare2.setId(orgacare1.getId());
        assertThat(orgacare1).isEqualTo(orgacare2);
        orgacare2.setId(2L);
        assertThat(orgacare1).isNotEqualTo(orgacare2);
        orgacare1.setId(null);
        assertThat(orgacare1).isNotEqualTo(orgacare2);
    }
}
