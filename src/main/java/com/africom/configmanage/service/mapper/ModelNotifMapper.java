package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.ModelNotif;
import com.africom.configmanage.service.dto.ModelNotifDTO;
import org.mapstruct.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper for the entity {@link ModelNotif} and its DTO {@link ModelNotifDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelNotifMapper extends EntityMapper<ModelNotifDTO, ModelNotif> {

    @Override
    @Mapping(target = "saveCopy", source = "saveCopy")
    ModelNotif toEntity(ModelNotifDTO dto);

    @Override
    @Mapping(target = "saveCopy", source = "saveCopy")
    ModelNotifDTO toDto(ModelNotif entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget ModelNotif entity, ModelNotifDTO dto);
}
