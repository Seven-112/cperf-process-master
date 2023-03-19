package com.mshz.service;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.domain.EdgeInfo;
import com.mshz.domain.Process;
import com.mshz.domain.Task;
import com.mshz.domain.TaskItem;
import com.mshz.domain.TaskPauseHistory;
import com.mshz.domain.TaskStatusTraking;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.TaskUserRole;
import com.mshz.domain.projection.ITaskProcess;
import com.mshz.model.ChronoUtil;
import com.mshz.repository.EdgeInfoRepository;
import com.mshz.repository.ProcessRepository;
import com.mshz.repository.StartableTaskRepository;
import com.mshz.repository.TaskFileRepository;
import com.mshz.repository.TaskItemRepository;
import com.mshz.repository.TaskRepository;
import com.mshz.repository.TaskSubmissionRepository;
import com.mshz.repository.TaskUserRepository;
import com.mshz.repository.TaskValidationControlRepository;
import com.mshz.service.async.AsyncUtilsService;
import com.mshz.webflux.ProcessNotifService;
/**
 * Service Implementation for managing {@link Task}.
 */
@Service
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    private final AsyncUtilsService asyncUtilsService;
    private final EdgeInfoRepository edgeInfoRepository;
    private final TaskUserRepository taskUserRepository;
    private final TaskFileRepository taskFileRepository;
    private final TaskItemRepository taskItemRepository;
    private final ItemCheckJustificationService itemCheckJustificationService;
    private final TaskValidationControlRepository validationControlRepository;
    private final TaskSubmissionRepository submissionRepository;
    private final ProcessNotifService notifService;
    private final ProcessRepository processRepository;
    private final TaskPauseHistoryService pauseHistoryService;
    
    private final JustificationService justificationService;
    
    private final StartableTaskRepository startableTaskRepository;
    
    private final TaskStatusTrakingService statusTrakingService;

    public TaskService(TaskRepository taskRepository,
     AsyncUtilsService asyncUtilsService,
     EdgeInfoRepository edgeInfoRepository,
     TaskUserRepository taskUserRepository,
     TaskFileRepository taskFileRepository,
     TaskItemRepository taskItemRepository,
     TaskValidationControlRepository validationControlRepository,
     TaskSubmissionRepository submissionRepository,
     ProcessNotifService notifService, 
     ProcessRepository processRepository,
     TaskPauseHistoryService pauseHistoryService,
     JustificationService justificationService,
     StartableTaskRepository startableTaskRepository,
     TaskStatusTrakingService statusTrakingService,
     ItemCheckJustificationService itemCheckJustificationService) {
        this.taskRepository = taskRepository;
        this.asyncUtilsService = asyncUtilsService;
        this.edgeInfoRepository = edgeInfoRepository;
        this.taskUserRepository = taskUserRepository;
        this.taskFileRepository = taskFileRepository;
        this.taskItemRepository = taskItemRepository;
        this.validationControlRepository = validationControlRepository;
        this.submissionRepository = submissionRepository;
        this.notifService = notifService;
        this.processRepository = processRepository;
        this.pauseHistoryService = pauseHistoryService;
        this.justificationService = justificationService;
        this.itemCheckJustificationService = itemCheckJustificationService;
        this.startableTaskRepository = startableTaskRepository;
        this.statusTrakingService = statusTrakingService;
    }

    /**
     * Save a task.
     *
     * @param task the entity to save.
     * @return the persisted entity.
     */
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        if(task != null && task.getId() == null){
            task.setChecked(Boolean.TRUE);
            task.setValid(Boolean.TRUE);
            task.setExceceed(Boolean.FALSE);
            task = setDefaultLogigramCords(task);
        }
        return taskRepository.save(task);
    }

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        Page<Task> result =  taskRepository.findAll(pageable);
        return new PageImpl<>(sortTasks(result.getContent()), result.getPageable(), result.getTotalElements());
    }


    /**
     * Get one task by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Task> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id);
    }

    /**
     * Delete the task by id.
     *
     * @param id the id of the entity.a
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        if(taskRepository.existsById(id)){
            // taskRepository.unLinkStartupAssociationsByTaskId(id);
            taskRepository.unLinkParentIdByTaskId(id);
            taskUserRepository.deleteByTaskId(id);
            taskFileRepository.deleteByTaskId(id);
            deleteItemCheckByTaskId(id);
            validationControlRepository.deletByTaskId(id);
            submissionRepository.deleteByTaskId(id);
            edgeInfoRepository.deleteBySourceOrTarget(id.toString(), id.toString());
            justificationService.deleteByTaskId(id);
            startableTaskRepository.deleteByTriggerTaskIdOrStartableTaskId(id, id);
            pauseHistoryService.deleteByTaskId(id);
            statusTrakingService.deleteByTaskId(id);
            taskRepository.deleteById(id);
        }
    }

    private void deleteItemCheckByTaskId(Long id) {
        List<TaskItem> items = taskItemRepository.findByTaskId(id);
        List<Long> itemsIds = items.stream().map(i -> i.getId()).collect(Collectors.toList());
        itemCheckJustificationService.deleteByItemsIds(itemsIds);
        taskItemRepository.deleteByTaskId(id);
    }

    public List<Task> findByProcessIdOrderByIdAsc(Long id) {
        return sortTasks(taskRepository.findByProcessId(id));
    }
    
    public Task copy(Task t, Long newProcessId) {
    	if(t == null)
    		return null;
    	Task task = new Task();
    	task.setDescription(t.getDescription());
    	task.setGroupId(t.getGroupId());
    	task.setLogigramPosX(t.getLogigramPosX());
    	task.setLogigramPosY(t.getLogigramPosY());
        task.setManualMode(t.isManualMode());
    	task.setName(t.getName());
    	task.setNbDays(t.getNbDays());
    	task.setNbHours(t.getNbHours());
    	task.setNbMinuites(t.getNbMinuites());
    	task.setNbMonths(t.getNbMonths());
    	task.setNbPause(t.getNbPause());
    	task.setNbYears(t.getNbYears());
    	task.setPriorityLevel(t.getPriorityLevel());
    	task.setStartWithProcess(t.isStartWithProcess());
    	task.setValid(true);
    	task.setFinishAt(null);
    	task.setPauseAt(null);
    	task.setProcessId(newProcessId);
    	task.setStartAt(null);
    	task.setStatus(TaskStatus.VALID);
    	task.setTaskModelId(t.getId());
        task.setRiskId(t.getRiskId());
        task.setSheduledStartAt(t.getSheduledStartAt());
        task.setSheduledStartHour(t.getSheduledStartHour());
        task.setSheduledStartMinute(t.getSheduledStartMinute());
        task.setChecked(t.isChecked());
    	return taskRepository.saveAndFlush(task);
    }
    
    public Task copyParentAndStartup(Task task) {
    	if(task != null && task.getTaskModelId() != null) {
    		Task model = taskRepository.findById(task.getTaskModelId()).orElse(null);
        	if(model != null && model.getParentId() != null) {
        		Task parent = taskRepository.findByTaskModelIdAndProcessId(model.getParentId(), task.getProcessId()).orElse(null);
        		if(parent != null) 
        			task.setParentId(parent.getId());
        		else
        			task.setParentId(null);
        	}
        	if(model.getStartupTask() != null) {
        		Task startupTask = taskRepository.findByTaskModelIdAndProcessId(model.getStartupTask().getId(), task.getProcessId()).orElse(null);
        		task.setStartupTask(startupTask);
        	}
        	return taskRepository.saveAndFlush(task);
    	}
        return task;
    }
    

    // set task to valid status
    public List<Task> resetTask(Task task) {
    	List<Task> tasksStatusChanged = new ArrayList<Task>();
    	task.setFinishAt(null);
    	task.setManualMode(false);
    	task.setNbPause(0);
    	task.setStartAt(null);
    	task.setStatus(TaskStatus.VALID);
    	task.setValid(true);
        task.finishAt(null);
    	task = taskRepository.saveAndFlush(task);
    	if(task != null){
            notifService.sendTaskSatatusChangeNote(task);
            updateCheckedByTask(task, Boolean.FALSE);
    		tasksStatusChanged.add(task);
            pauseHistoryService.deleteByTaskId(task.getId());
        }
    	return sortTasks(tasksStatusChanged);
    }
    
    public Task startTask(Task task, boolean cheickIsNotCanceled) {
    	if(task != null && task.getStatus() != TaskStatus.STARTED && (!cheickIsNotCanceled || task.getStatus() != TaskStatus.CANCELED)) {
            TaskStatus lastStatus = task.getStatus();
            task.setValid(true);
    		task.setStartAt(new Date().toInstant());
            task.setPauseAt(null);
            task.setManualMode(false);
    		task.setStatus(TaskStatus.STARTED);
            task.setFinishAt(null);
    		taskRepository.save(task);
            if(task != null && lastStatus != TaskStatus.ON_PAUSE) {
                processRepository.updateCancledAt(task.getProcessId(), null);
                changeCanceledTaskStatusToLastSatusBeforeCanceled(task.getProcessId());
                updateCheckedByTask(task, Boolean.FALSE);
                pauseHistoryService.deleteByTaskId(task.getId());
            }
            asyncUtilsService.startStratupTasks(task, true);
            notifService.sendTaskSatatusChangeNote(task);
    	}
    	return task;
    }

    public Task executeTask(Task task) {
    	if(task != null && task.getStatus() != TaskStatus.EXECUTED) {
            List<TaskUserRole> roles = new ArrayList<>();
            roles.add(TaskUserRole.SUBMITOR);
            roles.add(TaskUserRole.VALIDATOR);
            if(!taskHasUsersWithRoles(task.getId(), roles))
                return finishTask(task);
            task.setValid(true);
            if(task.getStartAt() == null)
    		    task.setStartAt(new Date().toInstant());
            task.setPauseAt(null);
            task.setManualMode(false);
    		task.setStatus(TaskStatus.EXECUTED);
            task.setFinishAt(null);
    		taskRepository.save(task);
            notifService.sendTaskSatatusChangeNote(task);
    	}
    	return task;
    }
    
    public Task submitTask(Task task) {
    	if(task != null && task.getStatus() != TaskStatus.SUBMITTED) {
            List<TaskUserRole> roles = new ArrayList<>();
            roles.add(TaskUserRole.VALIDATOR);
            if(!taskHasUsersWithRoles(task.getId(), roles))
                return finishTask(task);
            task.setValid(true);
            if(task.getStartAt() == null)
    		    task.setStartAt(new Date().toInstant());
            task.setPauseAt(null);
            task.setManualMode(false);
    		task.setStatus(TaskStatus.SUBMITTED);
            task.setFinishAt(null);
    		taskRepository.save(task);
            notifService.sendTaskSatatusChangeNote(task);
    	}
    	return task;
    }
    
    public void runTaskChildren(Task task){
    	if(task != null && task.getId() != null) {
            List<EdgeInfo> edgeInfos = edgeInfoRepository.findBySourceAndProcessId(task.getId().toString(), task.getProcessId());
            if(edgeInfos != null){
                /* chidlren task is linkend to parent with target edgeinfo property
                /* find chlidren task ids
                */
                List<Long> childrenIds = edgeInfos.stream()
                                                  .filter(ei -> ei.getTarget() != null && ei.getTarget().toLowerCase().indexOf("cond") == -1)
                                                  .map(ei -> Long.valueOf(ei.getTarget())).collect(Collectors.toList());
                if(childrenIds != null){
                    List<Task> children = taskRepository.findByIdInAndProcessId(childrenIds, task.getProcessId());
                    for (Task child : children) {
                       startTask(child, true);
                    }
                }
            }
    	}
    }
    
    public Task finishTask(Task task){
    	task.setFinishAt(new Date().toInstant());
    	task.setValid(true);
    	task.setStatus(TaskStatus.COMPLETED);
    	task = taskRepository.save(task);
    	if(task != null){
            notifService.sendTaskSatatusChangeNote(task);
    		runTaskChildren(task);
        }
    	return task;
    }
    
    public Task cancelTask(Task task, Boolean cancelProcess){
    	task.setStatus(TaskStatus.CANCELED);
    	task.setValid(true);
        task.setFinishAt(null);
    	task = taskRepository.saveAndFlush(task);
    	if(task != null){
            notifService.sendTaskSatatusChangeNote(task);
            if(Boolean.TRUE.equals(cancelProcess)){
                processRepository.updateCancledAt(task.getProcessId(), Instant.now());
                taskRepository.updateStatusByProcess(task.getProcessId(), TaskStatus.CANCELED);
            }else{
                runTaskChildren(task);
            }
        }
    	return task;
    } 


    public Task playTask(Task task){
        if(task != null && (task.getStatus() == TaskStatus.SUBMITTED || task.getStatus() == TaskStatus.ON_PAUSE)){
            pauseHistoryService.updateRestartedAt(task.getCurrentPauseHistoryId());
            task.setPauseAt(null);
            task.setCurrentPauseHistoryId(null);
            task.setNbPause((task.getNbPause() == null) ? 1: task.getNbPause() + 1);
            task.setValid(true);
            task.setManualMode(true);
            task.setFinishAt(null);
            if(task.getStartAt() == null)
                task.setStartAt(new Date().toInstant());
            task.setStatus(TaskStatus.STARTED);

            task = taskRepository.saveAndFlush(task);
            if(task != null){
                processRepository.updateCancledAt(task.getProcessId(), null);
                changeCanceledTaskStatusToLastSatusBeforeCanceled(task.getProcessId());
                notifService.sendTaskSatatusChangeNote(task);
                asyncUtilsService.startStratupTasks(task, true);
            }
        }
        return task;
    }
    
    // is turn task to automatic mode
    public Task playByInstance(Task task){
        if(task != null && (task.getStatus() == TaskStatus.SUBMITTED || task.getStatus() == TaskStatus.ON_PAUSE)){
            pauseHistoryService.updateRestartedAt(task.getCurrentPauseHistoryId());
            task.setPauseAt(null);
            task.setCurrentPauseHistoryId(null);
            task.setFinishAt(null);
            task.setNbPause((task.getNbPause() == null) ? 1: task.getNbPause() + 1);
            task.setValid(true);
            task.setManualMode(false);
            if(task.getStartAt() == null)
                task.setStartAt(new Date().toInstant());
            task.setStatus(TaskStatus.STARTED);
            taskRepository.saveAndFlush(task);
            if(task != null){
                notifService.sendTaskSatatusChangeNote(task);
                asyncUtilsService.startStratupTasks(task, true);
            }
        }
        return task;
    }

    public Task pauseTask(Task task, boolean onSubmiteAction){
        if(task != null && (task.getStatus() == TaskStatus.STARTED) && task.isValid()){
        	Instant pauseAt = Instant.now();
        	TaskPauseHistory pauseHistory = pauseHistoryService.trackTaskPause(task, pauseAt);
        	if(pauseHistory != null)
        		task.setCurrentPauseHistoryId(pauseHistory.getId());
            task.setPauseAt(pauseAt);
            task.setManualMode(true);
            task.setFinishAt(null);
            if(task.getStartAt() == null)
                task.setStartAt(new Date().toInstant());
                if(onSubmiteAction){
                    List<TaskUserRole> roles = new ArrayList<>();
                    roles.add(TaskUserRole.SUBMITOR);
                    roles.add(TaskUserRole.VALIDATOR);
                    if(!taskHasUsersWithRoles(task.getId(),roles))
                        return finishTask(task);
                    else
                        task.setStatus(TaskStatus.SUBMITTED);
                }else{
                    task.setStatus(TaskStatus.ON_PAUSE);
                }
            return taskRepository.saveAndFlush(task);
        }
        return task;
    }


    public Task submiTask(Task task){
        return pauseTask(task, true);
    }

	public Page<ITaskProcess> findByEmployeeIdAndTaskStatus(Long id, TaskStatus status, PageRequest of) {
	    Page<ITaskProcess> page = taskRepository.findByEmployeeIdAndStatus(id, Boolean.TRUE,status, of);
        Page<ITaskProcess> pageImpl = new PageImpl<>(sortTaskProcesses(page.getContent()),page.getPageable(), page.getTotalElements());
        return pageImpl;
	}
    
    public Page<ITaskProcess> findByUserIdAndRoleAndTaskStatus(Long id, TaskStatus status, TaskUserRole role, Pageable pageable) {
		Page<ITaskProcess> page = taskRepository.findByUserIdAndRoleAndTaskStatus(id, Boolean.TRUE,status, role, pageable);
        Page<ITaskProcess> pageImpl = new PageImpl<>(sortTaskProcesses(page.getContent()),page.getPageable(), page.getTotalElements());
        return pageImpl;
	}

    public Page<ITaskProcess> findByUserIdAndRoleAndTaskStatusIn(Long id, List<TaskStatus> status, TaskUserRole role, Pageable pageable) {
		Page<ITaskProcess> page = taskRepository.findByUserIdAndRoleAndTaskStatusIn(id, Boolean.TRUE,status, role, pageable);
        Page<ITaskProcess> pageImpl = new PageImpl<>(sortTaskProcesses(page.getContent()),page.getPageable(), page.getTotalElements());
        return pageImpl;
	}


	public Page<ITaskProcess> findCheckableTasksByUserIdAndTaskStatus(Long id, TaskStatus status, Pageable pageable) {
        List<TaskStatus> listOfStatus = new ArrayList<>();
        if(status != null){
            listOfStatus.add(status);
        }else{
            // use default checkables status
            listOfStatus.add(TaskStatus.STARTED);
            listOfStatus.add(TaskStatus.EXECUTED);
        }
        Page<ITaskProcess> page = taskRepository.getTaskCheckListByUserIdAndAndValidAndTaskStatus(id, Boolean.TRUE,listOfStatus, pageable);
        Page<ITaskProcess> pageImpl = new PageImpl<>(
                sortTaskProcesses(page.getContent()),
                page.getPageable(), 
                page.getTotalElements()
        );
        return pageImpl;
	}

	public List<Task> findEmployeeTasksBetween(Long id, Instant startAt, Instant endAt) {
	     List<Task> result = taskRepository.findByEmployeeTasksByInstancesCreatedAtBetween(id,startAt, endAt, Boolean.TRUE);
         return sortTasks(result);
	}

	public Page<ITaskProcess> findByEmployeeId(Long id, PageRequest of) {
        Page<ITaskProcess> page = taskRepository.findByEmployeeId(id, Boolean.TRUE, of);
        Page<ITaskProcess> pageImpl = new PageImpl<>(
                sortTaskProcesses(page.getContent()),
                page.getPageable(), 
                page.getTotalElements()
        );
        return pageImpl;
	}
	
	public Page<Task> findTasksToStartupAssociable(Long parentTaskId, Long taskProcessId, Pageable pageable){
		Page<Task> page = taskRepository.getTasksToStartupAssociable(parentTaskId, taskProcessId, pageable);
        Page<Task> pageImpl = new PageImpl<>(
                sortTasks(page.getContent()),
                page.getPageable(), 
                page.getTotalElements()
        );
        return pageImpl;
	}


    public List<Task> getStratupTasks(Long taskId){
        return sortTasks(taskRepository.findByStartupTask_id(taskId));
    }

    public List<Task> getChildrenTasks(Long taskParentId){
        return sortTasks(taskRepository.findByParentId(taskParentId));
    }

    public List<Task> getByProcessIdAndStatus(Long processId, TaskStatus status){
        return sortTasks(taskRepository.findByProcessIdAndStatus(processId, status));
    }

    public void updateManualModeByProcessId(Long processId, Boolean manualMode){
        taskRepository.updateManualModeByProcessId(manualMode, processId);
    }

    public boolean taskHasUsersWithRoles(Long taskId, List<TaskUserRole> roles){
        if(taskId != null && roles != null){
            return !taskUserRepository.findByTask_idAndRoleIn(taskId, roles, PageRequest.of(0, 1)).isEmpty();
        }
        return false;
    }

    private void updateCheckedByTask(Task task, Boolean checked){
        if(task != null && task.getId() != null){
            taskItemRepository.updateCheckedByTaskId(task.getId(), checked);
            List<TaskItem> requiedUncheckedItems = taskItemRepository.findByTaskIdAndRequiredAndChecked(task.getId(), Boolean.TRUE, Boolean.FALSE);
            if(requiedUncheckedItems.isEmpty())
                task.setChecked(Boolean.TRUE);
            else
                task.setChecked(Boolean.FALSE);
            taskRepository.save(task);
        }
    }

    public List<Task> getByProcessIdAndValid(Long processId, Boolean valid){
        return sortTasks(taskRepository.findByProcessIdAndValid(processId, valid));
    }

    public ChronoUtil getTaskChronoUtil(Long taskId){
        Task task = taskRepository.findById(taskId).orElse(null);
        ChronoUtil chronoUtil = pauseHistoryService.getTaskChrono(task);
        if(chronoUtil != null && (task.isExceceed() == null || task.isExceceed().booleanValue() != chronoUtil.isExeceed()))
            taskRepository.updateExceceed(taskId, chronoUtil.isExeceed());
        return chronoUtil;
    }

    private Task setDefaultLogigramCords(Task task){
        if(task != null){
            Task refTask = taskRepository.findFirstByOrderByLogigramPosXAsc();
            double x = 100;
            double y = 100;
            if(refTask != null && refTask.getLogigramPosX() != null && refTask.getLogigramPosY() != null){
                 x = refTask.getLogigramPosX().doubleValue() + 8;
                 y = refTask.getLogigramPosY().doubleValue()+8;
            }
            task.setLogigramPosX(Double.valueOf(x));
            task.setLogigramPosY(Double.valueOf(y));
        }

        return task;
    }

    public Boolean allRequiredTaskItemsIsChecked(Long taskId) {
        TaskItem unchecked = taskItemRepository.
            findFirstByTaskIdAndRequiredAndChecked(taskId, Boolean.TRUE, Boolean.FALSE);
        return unchecked == null;
    }
    
    public Task getPonctualByQueryId(Long queryInstanceId){
        if(queryInstanceId != null){
            Process instance = processRepository.findFirstByQueryId(queryInstanceId);
            if(instance != null)
                return taskRepository.findFirstByProcessId(instance.getId());
        }
        return null;
    }

    /** 
     * ===================================
     * ======= BEGIN SOTRING UTILS METHODS =====
     * ===================================
     */

    public List<Task> sortTasksyIds(List<Long> ids){
        List<Task> tasks = new ArrayList<>();
        if(ids != null && !ids.isEmpty()){
            tasks = taskRepository.findByIdIn(ids);
            return sortTasks(tasks);
        }
        return tasks;
    }

    public List<Task> getSortedByPriority(List<String> ids) {
        if(ids != null && !ids.isEmpty()){
            List<Long> longIds = ids.stream().map(id -> Long.valueOf(id))
                                    .collect(Collectors.toList());
            return sortTasksyIds(longIds);
        }
        return new ArrayList<>();
    }

    public List<Task> sortTasks(List<Task> tasks){
        if(tasks != null && tasks.size() >=2){
            /**
             * a will placed before b if sorted comparator is negative
             * b will placed before a if sorted comparator is positive
             * no sorting if sorted comparator is zero
             */
            return tasks.stream()
                // sorting by ids
                .sorted((a,b) -> sortById(a,b))
                // sorting by started tasks
                .sorted((a,b) -> sortByStatus(a,b))
                // sorting by chrono
                .sorted((a,b) -> sortByChrono(a,b))
                .collect(Collectors.toList());
        }

        return tasks;
    }

    public List<ITaskProcess> sortTaskProcesses(List<ITaskProcess> elements) {
        if(elements != null && !elements.isEmpty()){
            return elements.stream()
                // sorting by ids
                .sorted((a,b) -> sortById(a.getTask(),b.getTask()))
                // sorting by started tasks
                .sorted((a,b) -> sortByStatus(a.getTask(),b.getTask()))
                // sorting by chrono
                .sorted((a,b) -> sortByChrono(a.getTask(),b.getTask()))
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private int sortById(Task ta, Task tb){
        if(ta != null && tb != null){
            try {
                if(ta.getId() == null && tb.getId() != null)
                    return 1; // tb avant ta
                if(ta.getId() != null && tb.getId() == null)
                    return -1; // ta avant tb
                if(ta.getId() != null && tb.getId() != null)
                    return tb.getId().intValue() - ta.getId().intValue();
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return 0;
    }

    private int sortByStatus(Task ta, Task tb){
        // if task is started will render first
        if(ta != null && tb != null){
            try {
                List<TaskStatus> startedStatus = Arrays
                    .asList(TaskStatus.EXECUTED, TaskStatus.STARTED, TaskStatus.SUBMITTED);
                
                boolean taIsStarted = ta.getStatus() != null && startedStatus.contains(ta.getStatus());
                boolean tbIsStarted = tb.getStatus() != null && startedStatus.contains(tb.getStatus());

                if(!taIsStarted && tbIsStarted)
                    return 1; // tb avant ta
                if(taIsStarted && !tbIsStarted)
                    return -1; // ta avant tb
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return 0;
    }

    private int sortByChrono(Task ta, Task tb){
        if(ta != null && tb != null){
            try {
                ChronoUtil taChrono = pauseHistoryService.getTaskChrono(ta);
                ChronoUtil tbChrono = pauseHistoryService.getTaskChrono(tb);

                if(taChrono == null && tbChrono == null)
                    return 0;

                if(taChrono == null && tbChrono != null)
                    return 1; // tb avant ta

                if(taChrono != null && tbChrono == null)
                    return -1; // ta avant tb
                    
                if(taChrono.getStatus() != TaskStatus.CANCELED && tbChrono.getStatus() == TaskStatus.CANCELED)
                    return -1; // tb avant ta

                if(taChrono.getStatus() == TaskStatus.CANCELED && tbChrono.getStatus() != TaskStatus.CANCELED)
                    return 1; // ta avant tb


                if(taChrono.getFinishDate() == null && tbChrono.getFinishDate() != null)
                    return 1; // tb avant ta

                if(taChrono.getFinishDate() != null && tbChrono.getFinishDate() == null)
                    return -1; // ta avant tb

                if(taChrono.getPausedDate() == null && tbChrono.getPausedDate() != null)
                    return -1; // tb avant ta

                if(taChrono.getPausedDate() != null && tbChrono.getPausedDate() == null)
                    return 1; // ta avant tb

                // sorting by excecced chrono
                if(taChrono.isExeceed() && !tbChrono.isExeceed())
                    return -1;
                if(!taChrono.isExeceed() && tbChrono.isExeceed())
                    return 1;
                if(taChrono.isExeceed() && tbChrono.isExeceed()){
                    Instant now = Instant.now();
                    Duration da = Duration.between(now, taChrono.getPrviewFinishDate());
                    Duration db = Duration.between(now, tbChrono.getPrviewFinishDate());
                    return da.compareTo(db); 
                }
                // end  sorting by excecced chrono

                if(taChrono.getPrviewFinishDate() != null && tbChrono.getPrviewFinishDate() != null )
                    return taChrono.getPrviewFinishDate().compareTo(tbChrono.getPrviewFinishDate());
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return 0;
    }

    public List<Task> getPreview(Long currentTaskId){
        List<Task> result = new ArrayList<>();
        if(currentTaskId != null){
            Task task = taskRepository.findById(currentTaskId).orElse(null);
            if(task != null){
                List<Long> previewTaskIds = getSourceTaskIdsFormTarget(currentTaskId.toString(), task.getProcessId());
                return taskRepository.findByIdInAndProcessIdAndValidIsTrue(previewTaskIds, task.getProcessId());
            }
        }
        return result;
    }

    private List<Long> getSourceTaskIdsFormTarget(String target, Long processId) {
        List<Long> taskIds = new ArrayList<>();
        if(target != null){
            // finding in edge info
            List<EdgeInfo> edges = edgeInfoRepository.findByTargetAndProcessId(target, processId);
            if(edges != null && !edges.isEmpty()){
                // add taskids 
                taskIds.addAll(edges
                    .stream()
                    .filter(edge -> edge.getSource() != null && !edge.getSource().toLowerCase().startsWith("cond"))
                    .map(edge -> Long.valueOf(edge.getSource()))
                    .collect(Collectors.toSet()));

                Set<String> condTargets = edges
                    .stream()
                    .filter(edge -> edge.getSource() != null && edge.getSource().toLowerCase().startsWith("cond"))
                    .map(edge -> edge.getSource())
                    .collect(Collectors.toSet());

                if(condTargets != null && !condTargets.isEmpty()){
                    for (String condTarget : condTargets) {
                        taskIds.addAll(getSourceTaskIdsFormTarget(condTarget, processId));
                    }
                }
            }
        }

        return taskIds;
    }

    @Async
    public void changeCanceledTaskStatusToLastSatusBeforeCanceled(Long processId){
        List<Task> cancledTasks = taskRepository.findByProcessIdAndStatus(processId, TaskStatus.CANCELED);
        if(cancledTasks != null && !cancledTasks.isEmpty()){
           for (Task task : cancledTasks) {
             TaskStatusTraking track = statusTrakingService.getPreviewTrackBeforeCurrent(task.getId());
             if(track != null && track.getStatus() != null)
                task.setStatus(track.getStatus());
             else
                task.setStatus(TaskStatus.VALID);
              taskRepository.save(task);
           }
        }
    }

    public Task changeTaskStatusById(Long taskId, TaskStatus status){
        Task task = null;
        if(taskId != null && status != null){
            try {
                task = taskRepository.getOne(taskId);
                if(task != null && !task.getStatus().equals(status)){
                    switch (status) {
                        case CANCELED:
                            task = cancelTask(task, false);
                            break;
                        case COMPLETED:
                            task =finishTask(task);
                            break;
                        case EXECUTED:
                            task = executeTask(task);
                            break;
                        case ON_PAUSE:
                            task = pauseTask(task, false);
                            break;
                        case STARTED:
                            task =startTask(task, true);
                            break;
                        case SUBMITTED:
                            task = submiTask(task);
                            break;
                        default:
                            resetTask(task);
                            break;
                    }
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
                e.printStackTrace();
            }
        }
        return task;
    }

    @Async
    public void asyncChangeTaskStatusById(Long taskId, TaskStatus status){
        changeTaskStatusById(taskId, status);
    }

    public Task reset(@Valid Task task) {
    	task.setFinishAt(null);
    	task.setManualMode(false);
    	task.setNbPause(0);
    	task.setStartAt(null);
    	task.setStatus(TaskStatus.VALID);
    	task.setValid(true);
        task.setFinishAt(null);
        task.setChecked(Boolean.FALSE);
    	return taskRepository.save(task);
    }
    
}
