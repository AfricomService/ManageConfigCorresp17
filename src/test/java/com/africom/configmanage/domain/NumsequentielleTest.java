package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class NumsequentielleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Numsequentielle.class);
        Numsequentielle numsequentielle1 = new Numsequentielle();
        numsequentielle1.setId(UUID.randomUUID());
        Numsequentielle numsequentielle2 = new Numsequentielle();
        numsequentielle2.setId(numsequentielle1.getId());
        assertThat(numsequentielle1).isEqualTo(numsequentielle2);
        numsequentielle2.setId(UUID.randomUUID());
        assertThat(numsequentielle1).isNotEqualTo(numsequentielle2);
        numsequentielle1.setId(null);
        assertThat(numsequentielle1).isNotEqualTo(numsequentielle2);
    }
}
