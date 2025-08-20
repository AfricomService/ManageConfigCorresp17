package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactCompanyMapperTest {

    private ContactCompanyMapper contactCompanyMapper;

    @BeforeEach
    public void setUp() {
        contactCompanyMapper = new ContactCompanyMapperImpl();
    }
}
