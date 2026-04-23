package com.africom.configmanage.repository;

import com.africom.configmanage.domain.Numsequentielle;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Numsequentielle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumsequentielleRepository extends JpaRepository<Numsequentielle, UUID> {
    Optional<Numsequentielle> findByCodeNumSeq(String codeNumSeq);
}
