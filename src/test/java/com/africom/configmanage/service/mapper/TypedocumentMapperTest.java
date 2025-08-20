package com.africom.configmanage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypedocumentMapperTest {

    private TypedocumentMapper typedocumentMapper;

    @BeforeEach
    public void setUp() {
        typedocumentMapper = new TypedocumentMapperImpl();
    }
}
