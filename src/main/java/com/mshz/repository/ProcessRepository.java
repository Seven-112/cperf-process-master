package com.mshz.repository;

import java.time.Instant;
import java.util.List;

import com.mshz.domain.Process;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Process entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessRepository extends JpaRepository<Process, Long>, JpaSpecificationExecutor<Process> {
    // find all instances
    List<Process> findByModelIdIsNotNull();
    List<Process> findByModelIdIsNotNullAndCanceledAtIsNull();

    List<Process> findByIdOrModelId(Long id, Long modelId);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE Process p set p.valid =:valid WHERE p.id =:pid OR p.modelId =:pid")
    Integer updateValidValue(@Param("pid") Long processId, @Param("valid") Boolean valid);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE Process p set p.category=null WHERE p.category != null AND p.category.id=:catId")
    void detachCategory(@Param("catId") Long categoryId);

    Process findFirstByQueryId(Long queryInstanceId);

    List<Process> findByModelId(Long id);

    List<Process> findByValidIsFalseOrValidIsNull();

    @Modifying(flushAutomatically = true)
    @Query("UPDATE Process p set p.canceledAt=:val WHERE p.id=:id")
    void updateCancledAt(@Param("id") Long id, @Param("val") Instant canceledAt);
}
