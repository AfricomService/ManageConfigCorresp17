package com.africom.configmanage.repository;

import com.africom.configmanage.domain.Typedocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Typedocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypedocumentRepository extends JpaRepository<Typedocument, Long> {}
