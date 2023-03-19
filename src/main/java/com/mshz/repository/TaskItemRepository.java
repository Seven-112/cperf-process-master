package com.mshz.repository;

import java.util.List;

import com.mshz.domain.TaskItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Long>, JpaSpecificationExecutor<TaskItem> {
    List<TaskItem> findByTaskId(Long taskId);

	@Modifying
	@Query("delete from TaskItem ti where ti.taskId=:tid")
    void deleteByTaskId(@Param("tid") Long id);

    @Modifying
	@Query("update TaskItem ti set ti.checked=:checked where ti.taskId=:tid")
    void updateCheckedByTaskId(@Param("tid") Long taskId, @Param("checked") Boolean checked);

    List<TaskItem> findByTaskIdAndRequiredAndChecked(Long taskId, Boolean required, Boolean checked);
    @Query("select count(ti) from TaskItem ti where ti.taskId=:tid")
    long countByTaskId(@Param("tid") Long taskId);

    List<TaskItem> findByNameAndTaskIdAndCheckerId(String name, Long taskId, Long checkerId);

    TaskItem findFirstByTaskIdAndRequiredAndChecked(Long taskId, Boolean true1, Boolean false1);

    List<TaskItem> findByTaskIdAndCheckedIsNot(Long id, Boolean true1);
}
