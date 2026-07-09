package com.africom.configmanage.repository;

import com.africom.configmanage.domain.TypeDocDetailInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Spring Data SQL repository for the TypeDocDetailInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeDocDetailInfoRepository extends JpaRepository<TypeDocDetailInfo, Long> {
    List<TypeDocDetailInfo> findByTypeDocId(Long typeDocId);

    List<TypeDocDetailInfo> findByDetailInfoId(Long detailInfoId);

    @Modifying
    @Transactional
    void deleteByTypeDocId(Long typeDocId);
}
