package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MyreportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Myreport.class);
        Myreport myreport1 = new Myreport();
        myreport1.setId(1L);
        Myreport myreport2 = new Myreport();
        myreport2.setId(myreport1.getId());
        assertThat(myreport1).isEqualTo(myreport2);
        myreport2.setId(2L);
        assertThat(myreport1).isNotEqualTo(myreport2);
        myreport1.setId(null);
        assertThat(myreport1).isNotEqualTo(myreport2);
    }
}
