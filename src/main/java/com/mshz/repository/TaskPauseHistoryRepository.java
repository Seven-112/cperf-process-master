package com.mshz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mshz.domain.TaskPauseHistory;

/**
 * Spring Data repository for the TaskPauseHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskPauseHistoryRepository
		extends JpaRepository<TaskPauseHistory, Long>, JpaSpecificationExecutor<TaskPauseHistory> {
	List<TaskPauseHistory> findByTaskIdAndTaskExecutionDeleyExeceedAndPausedAtNotNullAndRestartedAtNotNull(Long taskId,
			Boolean taskExecutionTimeExceceed);

	void deleteByTaskId(Long taskId);

	Page<TaskPauseHistory> findByTaskIdAndPausedAtNotNullAndRestartedAtNotNull(Long taskId, Pageable pageable);
}
