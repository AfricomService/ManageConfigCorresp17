package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetailInfoMapperTest {

    private DetailInfoMapper detailInfoMapper;

    @BeforeEach
    public void setUp() {
        detailInfoMapper = new DetailInfoMapperImpl();
    }
}
