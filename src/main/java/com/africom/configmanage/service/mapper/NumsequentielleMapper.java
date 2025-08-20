package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.Numsequentielle;
import com.africom.configmanage.service.dto.NumsequentielleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Numsequentielle} and its DTO {@link NumsequentielleDTO}.
 */
@Mapper(componentModel = "spring")
public interface NumsequentielleMapper extends EntityMapper<NumsequentielleDTO, Numsequentielle> {}
