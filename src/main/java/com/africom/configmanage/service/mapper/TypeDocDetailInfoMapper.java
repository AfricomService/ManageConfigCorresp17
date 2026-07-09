package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.TypeDocDetailInfo;
import com.africom.configmanage.service.dto.TypeDocDetailInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeDocDetailInfo} and its DTO {@link TypeDocDetailInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeDocDetailInfoMapper extends EntityMapper<TypeDocDetailInfoDTO, TypeDocDetailInfo> {}
