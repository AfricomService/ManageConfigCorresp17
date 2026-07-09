package com.africom.configmanage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.africom.configmanage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeDocDetailInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDocDetailInfo.class);
        TypeDocDetailInfo typeDocDetailInfo1 = new TypeDocDetailInfo();
        typeDocDetailInfo1.setId(1L);
        TypeDocDetailInfo typeDocDetailInfo2 = new TypeDocDetailInfo();
        typeDocDetailInfo2.setId(typeDocDetailInfo1.getId());
        assertThat(typeDocDetailInfo1).isEqualTo(typeDocDetailInfo2);
        typeDocDetailInfo2.setId(2L);
        assertThat(typeDocDetailInfo1).isNotEqualTo(typeDocDetailInfo2);
        typeDocDetailInfo1.setId(null);
        assertThat(typeDocDetailInfo1).isNotEqualTo(typeDocDetailInfo2);
    }
}
