package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumsequentielleMapperTest {

    private NumsequentielleMapper numsequentielleMapper;

    @BeforeEach
    public void setUp() {
        numsequentielleMapper = new NumsequentielleMapperImpl();
    }
}
