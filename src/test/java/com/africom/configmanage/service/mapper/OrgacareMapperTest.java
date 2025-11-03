package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrgacareMapperTest {

    private OrgacareMapper orgacareMapper;

    @BeforeEach
    public void setUp() {
        orgacareMapper = new OrgacareMapperImpl();
    }
}
