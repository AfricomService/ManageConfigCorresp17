package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonCompanyMapperTest {

    private PersonCompanyMapper personCompanyMapper;

    @BeforeEach
    public void setUp() {
        personCompanyMapper = new PersonCompanyMapperImpl();
    }
}
