package com.africom.configmanage.repository;

import com.africom.configmanage.domain.ContactCompany;
import com.africom.configmanage.domain.enumeration.UsualStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the ContactCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactCompanyRepository extends JpaRepository<ContactCompany, Long>, JpaSpecificationExecutor<ContactCompany> {

    /**
     * Retourne une page des ContactCompany dont l'état est différent de l'état passé.
     * Utile pour exclure les ContactCompany marqués Delated (soft-delete).
     */
    Page<ContactCompany> findAllByEtatNot(UsualStatus etat, Pageable pageable);

    /**
     * Recherche une ContactCompany par nom (ignore case).
     * Utilisé pour valider l'unicité du nom côté création/mise à jour.
     */
    Optional<ContactCompany> findByNomIgnoreCase(String nom);

    /**
     * Recherche une ContactCompany par matrne (ignore case).
     * Retourne Optional.empty() si aucun résultat.
     * Utile pour valider l'unicité du matrne (qui peut être vide / null — l'appelant décide s'il appelle la méthode).
     */
    Optional<ContactCompany> findByMatrneIgnoreCase(String matrne);

    /**
     * Variante pratique : recherche par matrne tout en excluant un état particulier (ex: Delated).
     * Cela peut être utile pour checks rapides (exclure les enregistrements soft-deleted).
     */
    Optional<ContactCompany> findByMatrneIgnoreCaseAndEtatNot(String matrne, UsualStatus etat);

    List<ContactCompany> findAllByEtat(UsualStatus etat);

    // Recherche paginée par nom ou matrne (actives uniquement = etat == Actif)
    Page<ContactCompany> findByEtatAndNomContainingIgnoreCaseOrEtatAndMatrneContainingIgnoreCase(
        UsualStatus etat1, String nom,
        UsualStatus etat2, String matrne,
        Pageable pageable
    );

    // Sans filtre de recherche, juste les actives paginées
    Page<ContactCompany> findAllByEtat(UsualStatus etat, Pageable pageable);

}
