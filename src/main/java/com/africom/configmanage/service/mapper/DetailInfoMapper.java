package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.DetailInfo;
import com.africom.configmanage.service.dto.DetailInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailInfo} and its DTO {@link DetailInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DetailInfoMapper extends EntityMapper<DetailInfoDTO, DetailInfo> {}
