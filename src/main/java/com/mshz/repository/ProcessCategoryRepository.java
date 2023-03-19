package com.mshz.repository;

import com.mshz.domain.ProcessCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessCategoryRepository extends JpaRepository<ProcessCategory, Long>, JpaSpecificationExecutor<ProcessCategory> {
}
