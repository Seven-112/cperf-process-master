package com.mshz.repository;

import com.mshz.domain.Task;
import com.mshz.domain.TaskUser;
import com.mshz.domain.enumeration.TaskUserRole;
import com.mshz.model.TaskKpiGroupBy;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskUserRepository extends JpaRepository<TaskUser, Long>, JpaSpecificationExecutor<TaskUser> {

	List<TaskUser> findByTask(Task task);

    Page<TaskUser> findByTask_idAndRoleIn(Long taskId, List<TaskUserRole> roles, Pageable page);

	@Modifying
	@Query("delete from TaskUser tu where tu.task != null and tu.task.id=:tid")
    void deleteByTaskId(@Param("tid") Long id);

    List<TaskUser> findByTask_idAndRole(Long id, TaskUserRole role);

    List<TaskUser> findByTask_id(Long id);


// kPI users data finder
@Query("SELECT new com.mshz.model.TaskKpiGroupBy(count(t.status),t.status, tu.userId, t.exceceed) "
+"FROM TaskUser tu INNER JOIN tu.task t INNER JOIN Process p "
+"ON t.processId=p.id WHERE p.valid = true AND p.modelId != null "
+"AND t.valid=true and tu.userId=:userId and t.status != null "
+"AND (t.status != com.mshz.domain.enumeration.TaskStatus.COMPLETED"
+"  OR (t.status = com.mshz.domain.enumeration.TaskStatus.COMPLETED"
+" and t.finishAt != null and t.finishAt between :minDate and :maxDate)) "
+"GROUP BY t.status, tu.userId, t.exceceed")
List<TaskKpiGroupBy> groupingByStatusAndExceceedAndCount(@Param("userId") Long userId,
    @Param("minDate") Instant minDate, @Param("maxDate") Instant maxDate);


@Query("SELECT new com.mshz.model.TaskKpiGroupBy(count(t.status),t.status, t.exceceed) "
+"FROM Task t INNER JOIN Process p "
+"ON t.processId=p.id WHERE p.valid = true AND p.modelId != null "
+"AND t.valid=true and t.status != null "
+"AND (t.status != com.mshz.domain.enumeration.TaskStatus.COMPLETED"
+"  OR (t.status = com.mshz.domain.enumeration.TaskStatus.COMPLETED"
+" and t.finishAt != null and t.finishAt between :minDate and :maxDate)) "
+"GROUP BY t.status, t.exceceed")
List<TaskKpiGroupBy> groupingByStatusAndExceceedAndCount(@Param("minDate") Instant minDate, @Param("maxDate") Instant maxDate);
}
