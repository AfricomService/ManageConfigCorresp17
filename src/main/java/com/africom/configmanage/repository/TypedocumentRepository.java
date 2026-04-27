package com.africom.configmanage.repository;

import com.africom.configmanage.domain.Typedocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Typedocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypedocumentRepository extends JpaRepository<Typedocument, Long> {
    List<Typedocument> findByCorrespTypeOrCorrespTypeIsNull(String correspType);

    List<Typedocument> findByCorrespTypeAndCodeUniqueSociete(String correspType, String codeUniqueSociete);

//    @Query("SELECT t FROM Typedocument t WHERE " +
//        "(t.correspType = :correspType OR t.correspType IS NULL) " +
//        "AND t.codeUniqueSociete = :codeUniqueSociete")
//    List<Typedocument> findByCorrespTypeAndSociete(
//        @Param("correspType") String correspType,
//        @Param("codeUniqueSociete") String codeUniqueSociete);

    @Query("SELECT t FROM Typedocument t WHERE " +
        "(t.correspType = :correspType OR t.correspType IS NULL) " +
        "AND (t.codeUniqueSociete = :codeUniqueSociete OR t.codeUniqueSociete IS NULL)")
    List<Typedocument> findByCorrespTypeAndSociete(
        @Param("correspType") String correspType,
        @Param("codeUniqueSociete") String codeUniqueSociete);
}
