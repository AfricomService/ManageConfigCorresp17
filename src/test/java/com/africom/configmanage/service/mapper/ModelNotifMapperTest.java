package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelNotifMapperTest {

    private ModelNotifMapper modelNotifMapper;

    @BeforeEach
    public void setUp() {
        modelNotifMapper = new ModelNotifMapperImpl();
    }
}
