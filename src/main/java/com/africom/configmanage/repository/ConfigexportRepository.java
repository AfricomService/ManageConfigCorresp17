package com.africom.configmanage.repository;

import com.africom.configmanage.domain.Configexport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Configexport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigexportRepository extends JpaRepository<Configexport, Long> {}
