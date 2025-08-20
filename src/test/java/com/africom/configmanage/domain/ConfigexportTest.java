package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigexportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configexport.class);
        Configexport configexport1 = new Configexport();
        configexport1.setId(1L);
        Configexport configexport2 = new Configexport();
        configexport2.setId(configexport1.getId());
        assertThat(configexport1).isEqualTo(configexport2);
        configexport2.setId(2L);
        assertThat(configexport1).isNotEqualTo(configexport2);
        configexport1.setId(null);
        assertThat(configexport1).isNotEqualTo(configexport2);
    }
}
