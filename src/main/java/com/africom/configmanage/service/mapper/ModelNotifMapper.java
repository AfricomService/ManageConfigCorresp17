package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.ModelNotif;
import com.africom.configmanage.service.dto.ModelNotifDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelNotif} and its DTO {@link ModelNotifDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelNotifMapper extends EntityMapper<ModelNotifDTO, ModelNotif> {}
