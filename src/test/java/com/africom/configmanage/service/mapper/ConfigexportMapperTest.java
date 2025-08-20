package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigexportMapperTest {

    private ConfigexportMapper configexportMapper;

    @BeforeEach
    public void setUp() {
        configexportMapper = new ConfigexportMapperImpl();
    }
}
