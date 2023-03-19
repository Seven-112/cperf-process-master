package com.mshz.repository;

import com.mshz.domain.StartableTask;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StartableTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StartableTaskRepository extends JpaRepository<StartableTask, Long>, JpaSpecificationExecutor<StartableTask> {

    void deleteByTriggerTaskIdOrStartableTaskId(Long triggerTaskId, Long stratableTaskId);
}
