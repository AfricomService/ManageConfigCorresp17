package com.africom.configmanage.service.mapper;

import com.africom.configmanage.domain.ContactCompany;
import com.africom.configmanage.service.dto.ContactCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactCompany} and its DTO {@link ContactCompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactCompanyMapper extends EntityMapper<ContactCompanyDTO, ContactCompany> {}
