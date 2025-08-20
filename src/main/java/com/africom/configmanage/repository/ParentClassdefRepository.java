package com.africom.configmanage.repository;

import com.africom.configmanage.domain.ParentClassdef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ParentClassdef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParentClassdefRepository extends JpaRepository<ParentClassdef, Long> {}
