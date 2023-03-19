package com.mshz.repository;

import com.mshz.domain.ProcessCategoryUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessCategoryUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessCategoryUserRepository extends JpaRepository<ProcessCategoryUser, Long>, JpaSpecificationExecutor<ProcessCategoryUser> {

    @Modifying(flushAutomatically = true)
    @Query("DELETE from ProcessCategoryUser pcu WHERE pcu.categoryId=:catId")
    void deleteByCategoryId(@Param("catId") Long categoryId);

    @Modifying(flushAutomatically = true)
    @Query("DELETE from ProcessCategoryUser pcu WHERE pcu.userId=:userId AND pcu.categoryId=:catId AND pcu.processId=:processId")
    void deleteByUserIdAndCategoryIdAndProcessId(@Param("userId") Long userId,
         @Param("catId") Long categoryId, @Param("processId") Long processId);
}
