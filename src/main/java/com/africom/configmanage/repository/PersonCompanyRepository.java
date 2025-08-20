package com.africom.configmanage.repository;

import com.africom.configmanage.domain.PersonCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonCompanyRepository extends JpaRepository<PersonCompany, Long> {}
