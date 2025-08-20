package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.Configexport;
import com.africom.configmanage.domain.Myreport;
import com.africom.configmanage.service.dto.ConfigexportDTO;
import com.africom.configmanage.service.dto.MyreportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Myreport} and its DTO {@link MyreportDTO}.
 */
@Mapper(componentModel = "spring")
public interface MyreportMapper extends EntityMapper<MyreportDTO, Myreport> {
    @Mapping(target = "configexport", source = "configexport", qualifiedByName = "configexportId")
    MyreportDTO toDto(Myreport s);

    @Named("configexportId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConfigexportDTO toDtoConfigexportId(Configexport configexport);
}
