package com.africom.configmanage.repository;

import com.africom.configmanage.domain.PersonCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Spring Data JPA repository for the PersonCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonCompanyRepository extends JpaRepository<PersonCompany, Long> {
    // recherche par id du contact (utilise le champ contactCompanyId dans PersonCompany)
    List<PersonCompany> findByContactCompanyId(Long contactCompanyId);

    // variante paginée (utile pour les gros jeux de résultats)
    Page<PersonCompany> findByContactCompanyId(Long contactCompanyId, Pageable pageable);

    // recherche en base en excluant les entités dont etat == Delated
    // -----------------------
    List<PersonCompany> findByContactCompanyIdAndEtatNot(Long contactCompanyId, UsualStatus etat);

    Page<PersonCompany> findByContactCompanyIdAndEtatNot(Long contactCompanyId, UsualStatus etat, Pageable pageable);

    List<PersonCompany> findByContactCompanyIdAndEtat(Long contactCompanyId, UsualStatus etat);

}
