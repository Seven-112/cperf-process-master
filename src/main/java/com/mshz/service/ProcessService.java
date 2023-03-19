package com.mshz.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.domain.Process;
import com.mshz.domain.Task;
import com.mshz.domain.enumeration.TaskFileType;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.model.ChronoUtil;
import com.mshz.model.PonctualTaskUtil;
import com.mshz.repository.EventTriggerRepository;
import com.mshz.repository.JustificationRepository;
import com.mshz.repository.ProcessRepository;
import com.mshz.service.async.AsyncUtilsService;

/**
 * Service Implementation for managing {@link Process}.
 */
@Service
@Transactional
public class ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessService.class);

    private final ProcessRepository processRepository;
    
    private final TaskService taskService;
    private final EdgeInfoService edgeInfoService;
    private final CondNodeService condNodeService;
    private final AsyncUtilsService asyncUtilsService;
    private final TaskItemService taskItemService;
    private final TaskPauseHistoryService pauseHistoryService;
    private final JustificationRepository justificationRepository;
    private final EventTriggerRepository eventTriggerRepository;

    public ProcessService(
    	ProcessRepository processRepository, 
        TaskService taskService,
        TaskItemService taskItemService,
        EdgeInfoService edgeInfoService,
        CondNodeService condNodeService,
        AsyncUtilsService asyncUtilsService,
        TaskPauseHistoryService pauseHistoryService,
        EventTriggerRepository eventTriggerRepository,
        JustificationRepository justificationRepository) {
        this.processRepository = processRepository;
        this.taskService = taskService;
        this.edgeInfoService = edgeInfoService;
        this.condNodeService = condNodeService;
        this.asyncUtilsService = asyncUtilsService;
        this.taskItemService = taskItemService;
        this.pauseHistoryService = pauseHistoryService;
        this.justificationRepository = justificationRepository;
        this.eventTriggerRepository = eventTriggerRepository;
    }

    /**
     * Save a process.
     *
     * @param process the entity to save.
     * @return the persisted entity.
     */
    public Process save(Process process) {
        log.debug("Request to save Process : {}", process);
        return processRepository.save(process);
    }

    /**
     * Get all the processes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Process> findAll(Pageable pageable) {
        log.debug("Request to get all Processes");
        return processRepository.findAll(pageable);
    }


    /**
     * Get one process by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Process> findOne(Long id) {
        log.debug("Request to get Process : {}", id);
        return processRepository.findById(id);
    }

    /**
     * Delete the process by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Process : {}", id);
        // delete tasks
        if(processRepository.existsById(id)) {
            List<Task> tasks = taskService.findByProcessIdOrderByIdAsc(id);
            for (Task task : tasks) {
                taskService.delete(task.getId());
            }
            // delete justifications
            justificationRepository.deleteByProcessId(id);
            
            // delete edges
            edgeInfoService.deleteByProcessId(id);
            eventTriggerRepository.deleteByProcess_id(id);
            // delete instances
            List<Process> instances = processRepository.findByModelId(id);
            for (Process p : instances) {
                delete(p.getId());
            }
            processRepository.deleteById(id);
        }
    }
    
    @Transactional
    public Process copyProcess(Long modelId, String label) {
    	Process model = processRepository.findById(modelId).orElse(null);
    	if(model != null) {
        	Process copy = new Process();
    		copy.setId(null);
    		copy.setModelId(model.getModelId()); // set process no instance
    		copy.setCreatedAt(new Date().toInstant());
    		copy.setDescription(model.getLabel() + "-copie");
    		copy.setFinishedAt(model.getFinishedAt());
    		copy.setEditorId(model.getEditorId());
    		copy.setLabel(label);
    		copy.setPreviewFinishAt(model.getPreviewFinishAt());
    		copy.setPreviewStartAt(model.getPreviewStartAt());
    		copy.setPriorityLevel(model.getPriorityLevel());
    		copy.setProcedureId(model.getProcedureId());
    		copy.setRunnableProcessId(model.getRunnableProcessId());
    		copy.setStartAt(model.getStartAt());
    		copy.setStartCount(model.getStartCount());
    		copy.setValid(model.isValid());
            copy.setCategory(model.getCategory());
    		copy = processRepository.save(copy);
    		if(copy != null && copy.getId() != null) {
        		// coping tasks
        		List<Task> tasks = copyTasks(copy, modelId);
                /* reproduces  tasks logigram  dependecies */
                copyLogigramEdgesInfoAndConNodes(tasks);
                // reproduce logigram cond node
                condNodeService.reproduceCondNodes(modelId,copy.getId());
                // coping tasks users
        		asyncUtilsService.copyTasksUsers(tasks);
        		// coping tasks files
        		asyncUtilsService.copyTaskSFiles(tasks, TaskFileType.DESCRIPTION);
        		asyncUtilsService.copyTaskSFiles(tasks, TaskFileType.SOUMISSION);
        		asyncUtilsService.copyTaskSFiles(tasks, TaskFileType.VALIDATION);
                return copy;
    		}
    	}
    	return null;
    }
    
    public List<Task> startTaskStartubaleWithProcess(List<Task> tasks){
    	List<Task> startedTasks = new ArrayList<Task>();
    	if(tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
            	if(task.isStartWithProcess() != null && task.isStartWithProcess().booleanValue() == true) {
            		Task result = taskService.startTask(task,true);
            		startedTasks.add(result);
            	}
			}
    	}
    	return startedTasks;
    }
    
    public Process createInstance(String dossier, Long processModelId, Long runnaleProcessId, Long queryId){
        Process instance = null;
        Process processModel = processRepository.findById(processModelId).orElse(null);
        if(processModel != null){
            instance = this.saveInstance(dossier, processModel, runnaleProcessId, queryId);
            if(instance != null && instance.getId() !=null){
            	// coping tasks
               List<Task> tasks = copyTasks(instance, processModelId);
               /* reproduces  tasks logigram  dependecies */
               copyLogigramEdgesInfoAndConNodes(tasks);
                // reproduce logigram cond node
                condNodeService.reproduceCondNodes(instance.getModelId(), instance.getId());
                
                // copy task users
                asyncUtilsService.copyTasksUsers(tasks);
                
                // copy files
                asyncUtilsService.copyTaskSFiles(tasks, TaskFileType.DESCRIPTION);
                
                // starting startuble tasks with instances
                startTaskStartubaleWithProcess(tasks);

                // copy tasks task-items
                asyncUtilsService.copyTasksTaskItems(tasks);
            }
        }
        return instance;
    }
    
    private void copyLogigramEdgesInfoAndConNodes(List<Task> tasks) {
        /* reproduces  tasks logigram  dependecies */
        for (Task task : tasks) {
            if(task.getTaskModelId() != null)
                edgeInfoService.reproduceEdgeInfo(task.getId().toString(), task.getTaskModelId().toString(),task.getProcessId());
        }
    }
    
    public List<Task> copyTasks(Process process, Long modelId) {
    	List<Task> tasksToCopy = taskService.findByProcessIdOrderByIdAsc(modelId);
    	List<Task> result = new ArrayList<>();
    	List<Task> cpiedWithoutParentAndStartupTasks = new ArrayList<>();
    	for (Task task : tasksToCopy) {
    		Task t = taskService.copy(task, process.getId());
    		if(t != null){
    			cpiedWithoutParentAndStartupTasks.add(t);
                taskItemService.copyFromTask(t.getTaskModelId(), t);
            }
		}
    	
    	for (Task task : cpiedWithoutParentAndStartupTasks) {
    		task = taskService.copyParentAndStartup(task);
			result.add(task);
		}
    	// assingin tasks parent and 
    	return result;
    }

    private Process saveInstance(String dossier, Process processModel, Long runnableProcessId, Long queryId){
        if(processModel != null){
            Process instance = new Process();
            instance.setCreatedAt(new Date().toInstant());
            instance.setModelId(processModel.getId());
            instance.setLabel(dossier);
            instance.setDescription(processModel.getDescription());
            instance.setValid(true);
            instance.setPriorityLevel(processModel.getPriorityLevel());
            instance.setStartAt(new Date().toInstant());
            instance.setRunnableProcessId(runnableProcessId);
            instance.setQueryId(queryId);
            return processRepository.save(instance);
        }
        return null;
    }

   /**
    * 
    * @param procedure
    * @param processId
    */
   public List<Process> bindOrDetachAProcedure(Long procedureId, Long processId){
       List<Process> saved = new ArrayList<>();
        try {
            List<Process> processes =  processRepository.findByIdOrModelId(processId, processId);
            if(processes != null){
                for (Process process : processes) {
                    process.setProcedureId(procedureId);
                    processRepository.save(process);
                    saved.add(process);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
     return saved;
   }
   
   @Async
   public void UpdateAllTaskManualMode(Long processId, Boolean manualMode){
        taskService.updateManualModeByProcessId(processId, manualMode);
   }

   @Async
   public void playOrPauseTasks(long processId, boolean play){
       List<Task> tasks = taskService.getByProcessIdAndStatus(processId, play ? TaskStatus.ON_PAUSE : TaskStatus.STARTED);
       if(tasks != null){
           processRepository.updateCancledAt(processId, null);
           for (Task task : tasks) {
               if(play)
                taskService.playByInstance(task);
               else
                    taskService.pauseTask(task, false);
           }
       }
   }

   public Integer toggleValidValue(Long processId, Boolean valid) {
        Integer result = processRepository.updateValidValue(processId,valid);
        if(processId != null && valid == null || (valid != null && valid.booleanValue() == false)) {
            delete(processId);
        }
        return result;
   }

   public ChronoUtil getChronoUtil(Long processId){
       Process process = processRepository.findById(processId).orElse(null);
       if(process != null && process.isValid() && process.getModelId() != null){
           List<Task> tasks = taskService.getByProcessIdAndValid(processId, Boolean.TRUE);
           TaskStatus  status = process.getCanceledAt() != null ? TaskStatus.CANCELED : getProcessChronoStatusByTasks(tasks);
           List<Task> noCanceledTasks = tasks.stream()
                .filter(t -> t.getStatus() != TaskStatus.CANCELED)
                .collect(Collectors.toList());
           if(!noCanceledTasks.isEmpty()){

            Instant startDate = noCanceledTasks.stream()
                .filter(t -> t.getStartAt() != null)
                .map(t -> t.getStartAt()).min(Instant::compareTo).get();

            Instant previewFinishDate = null;

            Integer nbMinutes = 0;

            if(startDate != null){

                Integer nbYears = noCanceledTasks.stream()
                    .filter(t -> t.getNbYears() != null)
                    .mapToInt(t -> t.getNbYears()).sum();

                Integer nbMonths = noCanceledTasks.stream()
                    .filter(t -> t.getNbMonths() != null)
                    .mapToInt(t -> t.getNbMonths()).sum();

                Integer nbDays = noCanceledTasks.stream()
                    .filter(t -> t.getNbDays() != null)
                    .map(t -> t.getNbDays()).collect(Collectors.summingInt(Integer::intValue));

                Integer nbHours = noCanceledTasks.stream()
                    .filter(t -> t.getNbHours() != null)
                    .mapToInt(t -> t.getNbHours()).sum();

                nbMinutes = noCanceledTasks.stream()
                    .filter(t -> t.getNbMinuites() != null)
                    .mapToInt(t -> t.getNbMinuites()).sum();
                nbMinutes = nbMinutes != null ? nbMinutes : 0;
          
                previewFinishDate = ZonedDateTime
                    .ofInstant(startDate, ZoneId.systemDefault())
                    .plusYears(nbYears != null ? nbYears.longValue() : 0)
                    .plusMonths(nbMonths != null ? nbMonths.longValue() : 0)
                    .plusDays(nbDays != null ? nbDays.longValue() : 0)
                    .plusHours(nbHours != null ? nbHours.longValue() : 0)
                    .plusMinutes(nbMinutes != null ? nbMinutes.longValue() : 0)
                    .toInstant();
            }

            Instant pausedDate = null;

            if(status == TaskStatus.ON_PAUSE){
                pausedDate = noCanceledTasks.stream()
                    .filter(t -> t.getPauseAt() != null)
                    .map(t -> t.getPauseAt()).max(Instant::compareTo).get();
            }

            Instant finishDate = null;
            if(process.getCanceledAt() != null){
                finishDate = process.getCanceledAt();
            }else if(status == TaskStatus.COMPLETED && previewFinishDate != null){
                List<Task> completedTask = noCanceledTasks.stream()
                .filter(t -> t.getStatus() != TaskStatus.COMPLETED && t.getFinishAt() != null)
                .collect(Collectors.toList());
                finishDate = ZonedDateTime.ofInstant(previewFinishDate, ZoneId.systemDefault())
                            .plusSeconds(getSavedOrLostSeconds(completedTask, true).longValue())
                            .minusSeconds(getSavedOrLostSeconds(completedTask, false).longValue())
                            .toInstant();
            }

            boolean execeed =  previewFinishDate != null ? 
                (finishDate != null && Arrays.asList(TaskStatus.CANCELED, TaskStatus.COMPLETED).contains(status))
                ? previewFinishDate.isBefore(finishDate) : previewFinishDate.isBefore(Instant.now())
                : false;

            return new ChronoUtil(startDate, pausedDate, previewFinishDate, finishDate, status, execeed);

           }

       }
       return null;
   }

   private TaskStatus getProcessChronoStatusByTasks(List<Task> tasks){
       if(tasks != null && !tasks.isEmpty()){
          if(tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.STARTED)
           || tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.SUBMITTED)
           || tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.EXECUTED)){
            return TaskStatus.STARTED;
          }

          if(tasks.stream().allMatch(t -> t.getStatus() == TaskStatus.CANCELED)){
            return TaskStatus.CANCELED;
          }

          if(tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.COMPLETED)
                // && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.VALID)
                && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.ON_PAUSE)
                && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.EXECUTED)
                && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.SUBMITTED)){
            return TaskStatus.COMPLETED;
          }

          if(tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.ON_PAUSE)){
            return TaskStatus.ON_PAUSE;
          }
          
       }
       return TaskStatus.VALID;
   }

   // calcul le temps perdu ou gagné
   private Long getSavedOrLostSeconds(List<Task> tasks, boolean execeed){
       Long result = Long.valueOf(0);
       if(tasks != null && !tasks.isEmpty()){
           try {
            long sum = tasks.stream()
                .filter(t -> t.getFinishAt() != null && t.getStatus() == TaskStatus.COMPLETED)
                .map(t -> pauseHistoryService.getTaskChrono(t))
                .filter(cu -> (cu.getFinishDate() != null && cu.getPrviewFinishDate() != null && cu.isExeceed() == execeed))
                .mapToLong(tcu -> ChronoUnit.SECONDS.between(tcu.getFinishDate(), tcu.getPrviewFinishDate()))
                .sum();
            result = Long.valueOf(sum);
           } catch (Exception e) {
               log.debug(e.getMessage());
               e.printStackTrace();
           }

       }
       return Math.abs(result.longValue());
   }

    public List<ChronoUtil> getProcessesChronoUtils(List<Long> pIds) {
        List<ChronoUtil> result = new ArrayList<>();
        if(pIds != null && !pIds.isEmpty())
        for (Long processId : pIds) {
            result.add(getChronoUtil(processId));
        }
        return result;
    }

    public void updatePonctualTaskExecTime(@Valid PonctualTaskUtil ponctualTaskUtil) {
        asyncUtilsService.updatePonctualTaskExecTime(ponctualTaskUtil);
    }

    /**
     * delete list of process by ids
     * @param ids : list of process ids
     * @return void
     */
    public void deleteAllByIds(List<Long> ids) {
        for (Long id : ids) {
            try {
                delete(id);
            } catch (Exception e) {
                log.debug("deleting no valid process error: {]", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
