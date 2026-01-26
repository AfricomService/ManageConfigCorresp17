package com.africom.configmanage.repository;

import com.africom.configmanage.domain.ContactCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactCompanyRepository extends JpaRepository<ContactCompany, Long> {
    /**
     * Retourne une page des ContactCompany dont l'état est différent de l'état passé.
     * Utile pour exclure les ContactCompany marqués Delated (soft-delete).
     */
    Page<ContactCompany> findAllByEtatNot(UsualStatus etat, Pageable pageable);
}
