package com.mshz.repository;

import com.mshz.domain.Task;
import com.mshz.domain.TaskFile;
import com.mshz.domain.enumeration.TaskFileType;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskFileRepository extends JpaRepository<TaskFile, Long>, JpaSpecificationExecutor<TaskFile> {

	List<TaskFile> findByTaskAndType(Task task, TaskFileType type);

	@Modifying
	@Query("delete from TaskFile tf where tf.task != null and tf.task.id=:tid")
    void deleteByTaskId(@Param("tid") Long id);
}
