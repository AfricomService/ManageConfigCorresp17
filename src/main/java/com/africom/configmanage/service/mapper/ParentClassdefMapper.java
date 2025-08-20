package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.Numsequentielle;
import com.africom.configmanage.domain.ParentClassdef;
import com.africom.configmanage.service.dto.NumsequentielleDTO;
import com.africom.configmanage.service.dto.ParentClassdefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParentClassdef} and its DTO {@link ParentClassdefDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParentClassdefMapper extends EntityMapper<ParentClassdefDTO, ParentClassdef> {
    @Mapping(target = "sequenceId", source = "sequenceId", qualifiedByName = "numsequentielleId")
    ParentClassdefDTO toDto(ParentClassdef s);

    @Named("numsequentielleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NumsequentielleDTO toDtoNumsequentielleId(Numsequentielle numsequentielle);
}
