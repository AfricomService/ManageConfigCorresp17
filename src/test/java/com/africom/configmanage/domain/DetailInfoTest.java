package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailInfo.class);
        DetailInfo detailInfo1 = new DetailInfo();
        detailInfo1.setId(1L);
        DetailInfo detailInfo2 = new DetailInfo();
        detailInfo2.setId(detailInfo1.getId());
        assertThat(detailInfo1).isEqualTo(detailInfo2);
        detailInfo2.setId(2L);
        assertThat(detailInfo1).isNotEqualTo(detailInfo2);
        detailInfo1.setId(null);
        assertThat(detailInfo1).isNotEqualTo(detailInfo2);
    }
}
