package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ModelNotifTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelNotif.class);
        ModelNotif modelNotif1 = new ModelNotif();
        modelNotif1.setId(UUID.randomUUID());
        ModelNotif modelNotif2 = new ModelNotif();
        modelNotif2.setId(modelNotif1.getId());
        assertThat(modelNotif1).isEqualTo(modelNotif2);
        modelNotif2.setId(UUID.randomUUID());
        assertThat(modelNotif1).isNotEqualTo(modelNotif2);
        modelNotif1.setId(null);
        assertThat(modelNotif1).isNotEqualTo(modelNotif2);
    }
}
