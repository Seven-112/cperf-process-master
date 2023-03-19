package com.mshz.repository;

import com.mshz.domain.Task;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.TaskUserRole;
import com.mshz.domain.projection.ITaskProcess;
import com.mshz.model.TaskKpiGroupBy;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

	List<Task> findByProcessId(Long id);

	Optional<Task> findByTaskModelIdAndProcessId(Long parentId, Long newProcessId);

	Set<Task> findByProcessIdAndTaskModelIdIn(Long newProcessId, Set<Task> startupTasks);
	
	List<Task> findByStartupTask_id(Long startupId);

    // get all task to play automatically
    @Query("select t from Task t where t.status =:status and t.processId in :processIds "+
           "and (t.manualMode = false or t.manualMode=null)")
    List<Task> getAllTaskToPlayAutomatically(@Param("status") TaskStatus status, @Param("processIds") List<Long> processIds);
    
    // get all task to pause automatically
    List<Task> findByStatusAndProcessIdInAndManualModeNot(TaskStatus status, List<Long> processIds, Boolean manualMode);
   
    // page tasks by employee id
    @Query("SELECT DISTINCT t as task, p as process FROM Task t "
    		+ "JOIN Process p on t.processId = p.id "
    		+ "LEFT JOIN TaskUser tu on t.id=tu.task.id " 
            +"WHERE tu.userId =:empId AND p.modelId != null AND p.valid =:valid")
    Page<ITaskProcess> findByEmployeeId(@Param("empId") Long empId, @Param("valid") Boolean valid, Pageable pageable);    
    
    // page tasks by employee id and status
    @Query("SELECT DISTINCT t as task, p as process FROM Task t "
    		+ "JOIN Process p on t.processId = p.id "
    		+ "LEFT JOIN TaskUser tu on t.id=tu.task.id " 
            +"WHERE tu.userId =:empId AND p.modelId != null AND t.status =:status AND p.valid =:valid")
    Page<ITaskProcess> findByEmployeeIdAndStatus(@Param("empId") Long empId, @Param("valid") Boolean valid, @Param("status") TaskStatus status, Pageable pageable);

    // page tasks by employee id and status
    @Query("SELECT DISTINCT t as task, p as process FROM Task t "
    		+ "JOIN Process p on t.processId = p.id "
    		+ "LEFT JOIN TaskUser tu on t.id=tu.task.id " 
            +"WHERE tu.userId =:empId AND tu.role=:role AND p.modelId != null AND t.status =:status AND p.valid =:valid")
    Page<ITaskProcess> findByUserIdAndRoleAndTaskStatus(@Param("empId") Long userId, @Param("valid") Boolean valid, @Param("status") TaskStatus status, @Param("role") TaskUserRole role, Pageable pageable);

    // page tasks by employee id and status
    @Query("SELECT DISTINCT t as task, p as process FROM Task t "
    		+ "JOIN Process p on t.processId = p.id "
    		+ "LEFT JOIN TaskUser tu on t.id=tu.task.id " 
            +"WHERE tu.userId =:empId AND tu.role=:role AND p.modelId != null AND t.status IN :status AND p.valid =:valid")
    Page<ITaskProcess> findByUserIdAndRoleAndTaskStatusIn(@Param("empId") Long userId, @Param("valid") Boolean valid, @Param("status") List<TaskStatus> status, @Param("role") TaskUserRole role, Pageable pageable);


    // page tasks by employee id and status
    @Query("SELECT DISTINCT t as task, p as process FROM Task t "
    		+ "JOIN Process p on t.processId = p.id "
                + "JOIN TaskItem ti on ti.taskId = t.id "
            +"WHERE ti.checkerId =:empId AND p.modelId != null AND t.status in :status AND p.valid =:valid")
    Page<ITaskProcess> getTaskCheckListByUserIdAndAndValidAndTaskStatus(@Param("empId") Long empId, @Param("valid") Boolean valid, @Param("status") List<TaskStatus> listOfStatus, Pageable pageable);
    

    // find tasks by employee id and process startAt beetwen
    @Query("SELECT DISTINCT t as task FROM Task t JOIN Process p on t.processId = p.id LEFT JOIN TaskUser tu on t.id=tu.task.id " 
            +"WHERE tu.userId =:empId AND p.modelId != null AND p.createdAt != null AND (p.createdAt between :startAt AND :endAt) AND p.valid =:valid")
    List<Task> findByEmployeeTasksByInstancesCreatedAtBetween(@Param("empId") Long employeeId, @Param("startAt") Instant startAt,  @Param("endAt") Instant endAt, @Param("valid") Boolean valid);
    
    @Query("SELECT t FROM Task t WHERE t.id != :tid AND t.processId = :pid AND (t.startupTask = null OR (t.startupTask != null AND t.startupTask.id != :tid))")
    Page<Task> getTasksToStartupAssociable(@Param("tid") Long taskToAssociateId, @Param("pid") Long processId,  Pageable page);
    
    List<Task> findByParentId(Long parentId);

    List<Task> findByProcessIdAndStatus(Long processId, TaskStatus taskStatus);

    // change manual mode 
    @Modifying(flushAutomatically = true)
    @Query("UPDATE Task t set t.manualMode =:manualMode WHERE t.processId =:processId")
    void updateManualModeByProcessId(@Param("manualMode") Boolean manualMode, @Param("processId") Long processId);

    List<Task> findByIdIn(List<Long> childrenIds);

    @Query("select t from Task t where t.status =:status and t.processId in :processIds "+
           "and t.sheduledStartAt=:dte and t.sheduledStartHour=:hour and t.sheduledStartMinute=:minute")
    List<Task> getShudledTasks(@Param("status") TaskStatus status, @Param("dte") LocalDate localDate,
        @Param("hour") int hour, @Param("minute") int minute, @Param("processIds") List<Long> instancesIds);
   
   @Modifying
   @Query("update Task t set t.startupTask=null where t.startupTask.id=:tid")
   void unLinkStartupAssociationsByTaskId(@Param("tid") Long id);   
   
   @Modifying
   @Query("update Task t set t.parentId=null where t.parentId=:tid")
   void unLinkParentIdByTaskId(@Param("tid") Long id);

   Task findFirstByOrderByLogigramPosXAsc();

   List<Task> findByProcessIdAndValid(Long processId, Boolean valid);

  Task findFirstByProcessId(Long instanceId);

  List<Task> findByIdInAndProcessId(List<Long> childrenIds, Long processId);

  Page<Task> findByTaskModelIdIsNotNullAndValidIsTrue(Pageable pageable);

  @Modifying
  @Query("update Task t set t.exceceed =:exceceed where t.id =:tid")
  void updateExceceed(@Param("tid") Long id, @Param("exceceed") Boolean exceceed);

  List<Task> findByIdInAndProcessIdAndValidIsTrue(List<Long> previewTaskIds, Long processId);

  @Modifying(flushAutomatically = true)
  @Query("update Task t set t.status =:status where t.processId =:pid and t.status !=:status")
  void updateStatusByProcess(@Param("pid") Long processId, @Param("status") TaskStatus status);

}
