package com.mshz.repository;

import com.mshz.domain.TaskSubmission;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskSubmissionRepository extends JpaRepository<TaskSubmission, Long>, JpaSpecificationExecutor<TaskSubmission> {

	@Modifying
	@Query("delete from TaskSubmission ts where ts.task != null and ts.task.id=:tid")
    void deleteByTaskId(@Param("tid") Long id);
}
