package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.service.dto.PersonCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonCompany} and its DTO {@link PersonCompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonCompanyMapper extends EntityMapper<PersonCompanyDTO, PersonCompany> {}
