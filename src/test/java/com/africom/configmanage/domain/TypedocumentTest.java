package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypedocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Typedocument.class);
        Typedocument typedocument1 = new Typedocument();
        typedocument1.setId(1L);
        Typedocument typedocument2 = new Typedocument();
        typedocument2.setId(typedocument1.getId());
        assertThat(typedocument1).isEqualTo(typedocument2);
        typedocument2.setId(2L);
        assertThat(typedocument1).isNotEqualTo(typedocument2);
        typedocument1.setId(null);
        assertThat(typedocument1).isNotEqualTo(typedocument2);
    }
}
