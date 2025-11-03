package com.africom.configmanage.repository;

import com.africom.configmanage.domain.Orgacare;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Orgacare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgacareRepository extends JpaRepository<Orgacare, Long> {}
