package com.africom.configmanage.repository;

import com.africom.configmanage.domain.ContactCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactCompanyRepository extends JpaRepository<ContactCompany, Long> {}
