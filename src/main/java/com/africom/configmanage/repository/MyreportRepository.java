package com.africom.configmanage.repository;

import com.africom.configmanage.domain.Myreport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Myreport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyreportRepository extends JpaRepository<Myreport, Long> {}
