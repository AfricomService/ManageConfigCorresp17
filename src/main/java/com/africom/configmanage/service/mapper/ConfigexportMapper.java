package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.Configexport;
import com.africom.configmanage.service.dto.ConfigexportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Configexport} and its DTO {@link ConfigexportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfigexportMapper extends EntityMapper<ConfigexportDTO, Configexport> {}
