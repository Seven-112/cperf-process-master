package com.mshz.repository;

import com.mshz.domain.TaskValidationControl;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskValidationControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskValidationControlRepository extends JpaRepository<TaskValidationControl, Long>, JpaSpecificationExecutor<TaskValidationControl> {

	@Modifying
	@Query("delete from TaskValidationControl tvc where tvc.task != null and tvc.task.id=:tid")
    void deletByTaskId(@Param("tid") Long id);
}
