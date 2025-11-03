package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.Orgacare;
import com.africom.configmanage.service.dto.OrgacareDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orgacare} and its DTO {@link OrgacareDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrgacareMapper extends EntityMapper<OrgacareDTO, Orgacare> {}
