package com.mshz.service.async;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.mshz.domain.Process;
import com.mshz.domain.Task;
import com.mshz.domain.TaskFile;
import com.mshz.domain.TaskItem;
import com.mshz.domain.TaskPauseHistory;
import com.mshz.domain.TaskUser;
import com.mshz.domain.enumeration.TaskFileType;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.model.PonctualTaskUtil;
import com.mshz.repository.ProcessRepository;
import com.mshz.repository.TaskFileRepository;
import com.mshz.repository.TaskItemRepository;
import com.mshz.repository.TaskRepository;
import com.mshz.repository.TaskUserRepository;
import com.mshz.service.PublicHolidayService;
import com.mshz.service.TaskPauseHistoryService;
import com.mshz.service.TaskStatusTrakingService;
import com.mshz.service.WorkCalenderService;
import com.mshz.webflux.ProcessNotifService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

    private final Logger log = LoggerFactory.getLogger(UtilService.class);

    private final TaskRepository taskRepository;

    private final ProcessRepository processRepository;
    private final PublicHolidayService publicHolidayServie;
    private final WorkCalenderService workCalenderService;
    private final TaskFileRepository taskFileRepository;
    private final TaskUserRepository taskUserRepository;
    private final TaskItemRepository taskItemRepository;
    private final TaskStatusTrakingService taskStatusTrakingService;
    private final ProcessNotifService notifService;
    private final TaskPauseHistoryService pauseHistoryService;

    public UtilService(TaskRepository taskRepository, ProcessRepository processRepository,
                        PublicHolidayService publicHolidayServie, WorkCalenderService workCalenderService,
                        TaskFileRepository taskFileRepository, TaskUserRepository taskUserRepository,
                        TaskItemRepository taskItemRepository, ProcessNotifService notifService,
                        TaskStatusTrakingService taskStatusTrakingService,
                        TaskPauseHistoryService pauseHistoryService) {
        this.taskRepository = taskRepository;
        this.processRepository = processRepository;
        this.publicHolidayServie = publicHolidayServie;
        this.workCalenderService = workCalenderService;
        this.taskFileRepository = taskFileRepository;
        this.taskUserRepository = taskUserRepository;
        this.taskItemRepository = taskItemRepository;
        this.notifService = notifService;
        this.taskStatusTrakingService = taskStatusTrakingService;
        this.pauseHistoryService = pauseHistoryService;
    }


    /**
     * auto start tasks
     */
    public void autoPauseTasks(){
       try {
           Date date = new Date();
           if(publicHolidayServie.isPublicHoliday(date) || !workCalenderService.isWorkingTime(date)){
                List<Process> instances = processRepository.findByModelIdIsNotNullAndCanceledAtIsNull();
                if(instances != null && !instances.isEmpty()){
                    List<Long> instancesIds = instances.stream().map(p -> p.getId()).collect(Collectors.toList());
                    List<Task> tasks = taskRepository.findByStatusAndProcessIdInAndManualModeNot(TaskStatus.STARTED, instancesIds, true);
                    for (Task task : tasks) {
                    	Instant pauseAt = Instant.now();
                    	TaskPauseHistory pauseHistory = pauseHistoryService.trackTaskPause(task, pauseAt);
                        task.setPauseAt(pauseAt);
                        task.setCurrentPauseHistoryId(pauseHistory.getId());
                        task.setStatus(TaskStatus.ON_PAUSE);
                        task.setFinishAt(null);
                        taskRepository.save(task);
                        notifService.sendTaskSatatusChangeNote(task);
                    }
                    tasks.clear();
                    tasks = null;
                    instancesIds.clear();
                    instancesIds = null;
                }
                instances = null;
           }
       } catch (Exception e) {
            e.printStackTrace();
       }
    }

    public void autoPlaySheduledTaks(){
        ZonedDateTime zdt = ZonedDateTime.now();
        List<Process> instances = processRepository.findByModelIdIsNotNullAndCanceledAtIsNull();
        if(instances != null && !instances.isEmpty()){
            List<Long> instancesIds = instances.stream().map(p -> p.getId()).collect(Collectors.toList());
            List<Task> tasks = taskRepository
                .getShudledTasks(TaskStatus.VALID,zdt.toLocalDate(), zdt.getHour(), zdt.getMinute(),instancesIds);
            for (Task task : tasks) {
                StartTask(task, true);
                // reitinializeTaskCheickItems(task);
            }
            instancesIds.clear();
            instancesIds = null;
            tasks.clear();
            tasks = null;
        }
    }

    /**
     * auto plays tasks
     */
    public void  autoPlayTasks(){
        try {
            Date date = new Date();
            if(publicHolidayServie.isPublicHoliday(date) == false && workCalenderService.isWorkingTime(date)){
                log.info("in auto play Task id {} and name {}");
                List<Process> instances = processRepository.findByModelIdIsNotNullAndCanceledAtIsNull();
                if(instances != null && !instances.isEmpty()){
                    List<Long> instancesIds = instances.stream().map(p -> p.getId()).collect(Collectors.toList());
                    List<Task> tasks = taskRepository
                    .getAllTaskToPlayAutomatically(TaskStatus.ON_PAUSE, instancesIds);
                    for (Task task : tasks) {
                        StartTask(task,false);
                        // reitinializeTaskCheickItems(task);
                    }
                    instancesIds.clear();
                    instancesIds = null;
                    tasks.clear();
                    tasks = null;
                }
                instances = null;
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
     }

    /**
     * 
     * @param t
     */
    public void startStatupTask(Task t, boolean sendNotification){
        try{
            if(t != null){
                List<Task> tasks = taskRepository.findByStartupTask_id(t.getId());
                log.debug("srtTaskfinded {}", tasks.size());
                if(tasks != null){
                    for (Task task : tasks) {
                        Task startedTTask = StartTask(task, true);
                        // reitinializeTaskCheickItems(task); 
                        if(startedTTask != null && startedTTask.getStatus() == TaskStatus.STARTED){
                            if(sendNotification)
                                notifService.sendTaskSatatusChangeNote(task);
                            startStatupTask(startedTTask, sendNotification);
                        }
                    }
                    tasks = null;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            log.info("error {}", e.getMessage());
        }
    }

    /* public void reitinializeTaskCheickItems(Task task){
        if(task != null && task.getStatus() == TaskStatus.VALID){
            log.debug("checik cleaning {} {} ", task.getStatus(), task.getName());
            // cleaning task cheick validations
            taskItemRepository.updateCheckedByTaskId(task.getId(), Boolean.FALSE); 
        }
    } */


    /**
     * 
     * @param task
     * @return
     */
    
    public Task StartTask(Task task, boolean sendNotification){
            // (task.getStatus() == TaskStatus.VALID || task.getStatus() == TaskStatus.ON_PAUSE) && task.isManualMode() != true && task.isValid() == true
            if(task != null && (task.getStatus() == TaskStatus.VALID || task.getStatus() == TaskStatus.ON_PAUSE) 
                && (task.isManualMode() == null || task.isManualMode().booleanValue() == false)) {
                task.setValid(true);
                if(task.getStartAt() == null)
                    task.setStartAt(new Date().toInstant());
                if(task.getStatus() == TaskStatus.ON_PAUSE){
                    task.setNbPause((task.getNbPause() == null) ? 1: task.getNbPause() + 1);
                    pauseHistoryService.updateRestartedAt(task.getCurrentPauseHistoryId());
                }
                task.setCurrentPauseHistoryId(null);
                task.setPauseAt(null);
                task.setStatus(TaskStatus.STARTED);
                task.setFinishAt(null);
                taskRepository.save(task);
                if(sendNotification)
                    notifService.sendTaskSatatusChangeNote(task);
            }
        return task;
    }

    /**
     * 
     * @param tasks
     * @param taskFileType
     */
   public void copyTaskSFiles(List<Task> tasks, TaskFileType taskFileType) {
    for (Task task : tasks) {
        Task taskModel = new Task();
        taskModel.setId(task.getTaskModelId());
         List<TaskFile> files = taskFileRepository.findByTaskAndType(taskModel, taskFileType);
         for (TaskFile file : files) {
            copyTaskFile(file, task);
         }
     }
     tasks = null;
  }

  /**
   * 
   * @param file
   * @param newTask
   */
  public void copyTaskFile(TaskFile file, Task newTask){
    if(file != null && newTask != null){
        TaskFile copy = new TaskFile();
        copy.setTask(newTask);
        copy.setFileId(file.getFileId());
        copy.setDescription(file.getDescription());
        copy.setType(file.getType());
        taskFileRepository.save(copy);
    }
  }

   /**
    * 
    * @param tasks
    */
  public void copyTasksUsers(List<Task> tasks) {
        for (Task task : tasks) {
            if(task.getTaskModelId() != null) {
                   Task model = taskRepository.findById(task.getTaskModelId()).orElse(null);
                    if(model != null) {
                        List<TaskUser> tasksUsers = taskUserRepository.findByTask(model);
                        for (TaskUser taskUser : tasksUsers) {
                            copyTaskUser(taskUser, task);
                        }
                        tasksUsers = null;
                    }
            }
       }
       tasks = null;
    }

    /**
     * 
     * @param taskUser
     * @param newTask
     * @return
     */
    public TaskUser copyTaskUser(TaskUser taskUser, Task newTask) {
    	TaskUser copy = new TaskUser();
    	copy.setUserId(taskUser.getUserId());
    	copy.setTask(newTask);
    	copy.setRole(taskUser.getRole());
    	return taskUserRepository.save(copy);
    }

    public void copyTaskitemsFormTasks(List<Task> tasks){
        if(tasks != null){
            for (Task task : tasks) {
                if(task.getTaskModelId() != null){
                    copyTaskItems(task.getTaskModelId(), task.getId());
                }
            }
            tasks = null;
        }
    }

    public List<TaskItem> copyTaskItems(Long taskId, Long newTaskId){
        List<TaskItem> results = new ArrayList<>();
        if(taskId != null && newTaskId != null && taskId != newTaskId){
            List<TaskItem> items = taskItemRepository.findByTaskId(taskId);
            for (TaskItem taskItem : items) {
                taskItem.setId(null);
                taskItem.setTaskId(newTaskId);
                taskItem = taskItemRepository.save(taskItem);
                if(taskItem != null)
                    results.add(taskItem);
            }
        }
        return results;
    }

    public Task unUsedNowAddPauseDurationOnTaskDelay(Task task){
        if(task != null && task.getStartAt() != null){
            LocalDate now  = LocalDate.now();
            Period p = Period.between(task.getPauseAt().atZone(ZoneOffset.UTC).toLocalDate(), now);
            long diffSeconds = Duration.between(task.getPauseAt(), (new Date()).toInstant()).getSeconds();
            if(!p.isNegative()){
                // year
                if(diffSeconds > 0){
                    int nbYears = p.getYears();
                    log.debug("nbYears  {}", nbYears);
                    if(nbYears > 0){
                        task.setNbYears(task.getNbYears() != null ? (task.getNbYears()  + nbYears) : nbYears);
                        diffSeconds = diffSeconds - (365 * nbYears * 24 * 60 * 60);
                    }
                }

                // months
                if(diffSeconds > 0){
                    int months = p.getMonths();
                    log.debug("months  {}", months);
                    if(months > 0){
                        task.setNbMonths(task.getNbMonths() != null ? (task.getNbMonths() + months) : months);
                        diffSeconds = diffSeconds - (months * 30 * 24 * 60 *60);
                    }
                }

                // days
                if(diffSeconds > 0){
                    int days = p.getDays();
                    log.debug("days  {}", days);
                    if(days > 0){
                        task.setNbDays(task.getNbDays() != null ? (task.getNbDays() + days) : days);
                        diffSeconds = diffSeconds - (days * 24 * 60 *60);
                    }
                }

            }

            /* hours */
            if(diffSeconds > 0){
                long hour = diffSeconds / 3600;
                if(hour > 0){
                    log.debug("hours  {}", hour);
                    task.setNbHours(task.getNbHours() != null ? (task.getNbHours() + Math.toIntExact(hour)) : Math.toIntExact(hour));
                    diffSeconds = diffSeconds - (hour * 60 *60);
                }
            }

            /* minmutes */
            if(diffSeconds > 0){
                long minutes = diffSeconds % 3600 / 60;
                task.setNbMinuites(task.getNbMinuites() != null ? (task.getNbMinuites() + Math.toIntExact(minutes)) : Math.toIntExact(minutes));
            }
        }
        return task;
    }


    public Task updatePonctualTaskExecTime(@Valid PonctualTaskUtil ponctualTaskUtil, int round) {
        Task task = null;
        if(ponctualTaskUtil != null &&  (ponctualTaskUtil.getTaskId() != null || ponctualTaskUtil.getInstanceId() != null)
           && (ponctualTaskUtil.getNbMinutes() != null || ponctualTaskUtil.getNbHours() != null
            || ponctualTaskUtil.getNbDays() != null || ponctualTaskUtil.getNbMonths() != null || ponctualTaskUtil.getNbYears() != null)){
            
            // finding task
            if(ponctualTaskUtil.getTaskId() != null)
                task = taskRepository.findById(ponctualTaskUtil.getTaskId()).orElse(null);
            else
                task = taskRepository.findFirstByProcessId(ponctualTaskUtil.getInstanceId());
            
            if(task == null){
                // retry finding
                if(round <= 6){
                    try {
                        Thread.sleep(20000);
                       task = updatePonctualTaskExecTime(ponctualTaskUtil, ++round);
                    } catch (Exception e) {
                        //TODO: handle exception
                    }
                }
            }else{
                // update task
                task.setNbMinuites(ponctualTaskUtil.getNbMinutes());
                task.setNbHours(ponctualTaskUtil.getNbHours());
                task.setNbDays(ponctualTaskUtil.getNbDays());
                task.setNbMonths(ponctualTaskUtil.getNbMonths());
                task.setNbYears(ponctualTaskUtil.getNbYears());
                task = taskRepository.save(task);
            }
          }

        return task;
    }

    /**
     * method to auto save task status tracking util for performance indicators view
     * @param pageNumber : number of page naturaly start with 0
     * @param size : number of element per page
     * @param totalPages : must be greter than pageNumber param
     * @param instant : date of tracking
     */
    public void trackTasksStatus(Instant instant){
        if(instant != null){
            Pageable pageable = PageRequest.of(0, 200);
            while(pageable != null){
                // finding and save task status traking
                Page<Task> page = taskRepository.findByTaskModelIdIsNotNullAndValidIsTrue(pageable);
                for (Task task : page.getContent()) {
                    taskStatusTrakingService.traceTaskStatus(task);
                }
                // increment or breack loop by setting pageable to null
                pageable = page.hasNext() ? page.nextPageable() : null;
            }
            pageable = null;
        }
    }
}


