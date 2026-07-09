package com.africom.configmanage.repository;

import com.africom.configmanage.domain.DetailInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetailInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailInfoRepository extends JpaRepository<DetailInfo, Long> {}
