package com.africom.configmanage.repository;

import com.africom.configmanage.domain.ModelNotif;


import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ModelNotif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelNotifRepository extends JpaRepository<ModelNotif, UUID> {
    Optional<ModelNotif> findOneByCode(String code);
    Page<ModelNotif> findByFamilleIgnoreCase(String famille, Pageable pageable);

}
