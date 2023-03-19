package com.mshz.repository;

import com.mshz.domain.TaskStatusTraking;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskStatusTraking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStatusTrakingRepository extends JpaRepository<TaskStatusTraking, Long>, JpaSpecificationExecutor<TaskStatusTraking> {

    TaskStatusTraking findFirstByTaskIdAndTracingAt(Long taskId, Instant trackingAt);

    TaskStatusTraking findFirstByTaskIdAndTracingAtBetween(Long id, Instant start, Instant end);

    void deleteByTaskId(Long taskId);

    List<TaskStatusTraking> findByTaskId(Long taskId);
    
    @Modifying
    @Query("update Task t set t.exceceed =:exceceed where t.id =:tid")
    void updateTaskExceceed(@Param("tid") Long taskId, @Param("exceceed") Boolean exceceed);

    List<TaskStatusTraking> findTop2ByTaskIdOrderByIdDesc(Long taskId);
}
