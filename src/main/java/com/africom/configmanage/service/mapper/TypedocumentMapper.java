package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.Typedocument;
import com.africom.configmanage.service.dto.TypedocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Typedocument} and its DTO {@link TypedocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypedocumentMapper extends EntityMapper<TypedocumentDTO, Typedocument> {}
