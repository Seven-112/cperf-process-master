package com.mshz.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.domain.Process;
import com.mshz.domain.Task;
import com.mshz.domain.TaskStatusTraking;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.model.ChronoUtil;
import com.mshz.repository.ProcessRepository;
import com.mshz.repository.TaskStatusTrakingFileRepository;
import com.mshz.repository.TaskStatusTrakingRepository;
import com.mshz.webflux.ProcessNotifService;

/**
 * Service Implementation for managing {@link TaskStatusTraking}.
 */
@Service
@Transactional
public class TaskStatusTrakingService {

    private final Logger log = LoggerFactory.getLogger(TaskStatusTrakingService.class);

    private final TaskStatusTrakingRepository taskStatusTrakingRepository;

    private TaskStatusTrakingFileRepository fileRepository;
    
    private final ProcessRepository processRepository;

    private final ProcessNotifService notifService;

    private final TaskPauseHistoryService pauseHistoryService;

    private final KPIService kpiService;

    public TaskStatusTrakingService(TaskStatusTrakingRepository taskStatusTrakingRepository,
            TaskStatusTrakingFileRepository fileRepository,
            ProcessNotifService notifService,
            ProcessRepository processRepository,
            TaskPauseHistoryService pauseHistoryService,
            KPIService kpiService) {
        this.taskStatusTrakingRepository = taskStatusTrakingRepository;
        this.fileRepository = fileRepository;
        this.notifService = notifService;
        this.processRepository = processRepository;
        this.pauseHistoryService = pauseHistoryService;
        this.kpiService = kpiService;
    }

    /**
     * Save a taskStatusTraking.
     *
     * @param taskStatusTraking the entity to save.
     * @return the persisted entity.
     */
    public TaskStatusTraking save(TaskStatusTraking taskStatusTraking) {
        log.debug("Request to save TaskStatusTraking : {}", taskStatusTraking);
        boolean isNew = taskStatusTraking != null && taskStatusTraking.getId() == null;
        taskStatusTraking = taskStatusTrakingRepository.save(taskStatusTraking);
        if(taskStatusTraking != null && taskStatusTraking.isEditable() == Boolean.TRUE)
            notifService.sendTaskLogEditNote(taskStatusTraking, isNew);
        return taskStatusTraking;
    }

    /**
     * Get all the taskStatusTrakings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStatusTraking> findAll(Pageable pageable) {
        log.debug("Request to get all TaskStatusTrakings");
        return taskStatusTrakingRepository.findAll(pageable);
    }


    /**
     * Get one taskStatusTraking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskStatusTraking> findOne(Long id) {
        log.debug("Request to get TaskStatusTraking : {}", id);
        return taskStatusTrakingRepository.findById(id);
    }

    /**
     * Delete the taskStatusTraking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskStatusTraking : {}", id);
        fileRepository.deleteByTrackId(id);
        taskStatusTrakingRepository.deleteById(id);
    }
    
    
    @Async
    private void trackTaskStatus(Task task) {
        if(task != null && task.getId() != null && task.getProcessId() != null) {
            log.debug("Traking_task_by_aop: {} task {}", task.getName());
            Process process = processRepository.getOne(task.getProcessId());
            // checkin if process is instance 
            if(process != null && process.getModelId() != null && process.isValid() != null && process.isValid().booleanValue() == true) {
                /**
                 * si la tâche a été trackée aujourd'hui on met à jour l'état du track
                 * sinon on enregistre une nouvelle trace de la tâche 
                 */
                /* Instant date = Instant.now();
                Instant start = ZonedDateTime.ofInstant(date, ZoneId.systemDefault())
                        .withHour(0)
                        .withMinute(1)
                        .withSecond(0)
                        .toInstant();
                        
                Instant end = ZonedDateTime.ofInstant(date, ZoneId.systemDefault())
                    .withHour(23)
                    .withMinute(59)
                    .withSecond(59).toInstant(); */
                
                Boolean exceceed = taskExecutionTimeIsExcecced(task) ? Boolean.TRUE : Boolean.FALSE;
                /* 
                TaskStatusTraking traking = taskStatusTrakingRepository.findFirstByTaskIdAndTracingAtBetween(task.getId(), start, end);
                if(traking != null && traking.isPerfIndicator() != null && traking.isPerfIndicator().booleanValue() == true ) {
                    traking.setStatus(task.getStatus());
                    traking.setTracingAt(date);
                }else {
                    traking = new TaskStatusTraking();
                    traking.setTaskId(task.getId());
                    traking.setStatus(task.getStatus());
                    traking.setEditable(Boolean.FALSE);
                    traking.setExeceed(exceceed);
                    traking.setPerfIndicator(Boolean.TRUE);
                    traking.setTracingAt(date);
                }
                taskStatusTrakingRepository.save(traking); */
                taskStatusTrakingRepository.updateTaskExceceed(task.getId(), exceceed);
                kpiService.calculeAndSaveAllTaskUserKPis(task.getId(), LocalDate.now());
            }
        }
    }

    public void traceTaskStatus(Task task){
        trackTaskStatus(task);
    }
    
    /** private task status traking utils methos */
    public boolean taskExecutionTimeIsExcecced(Task task){
        if(taskHasExpirableStatus(task)){
            ChronoUtil chronoUtil = pauseHistoryService.getTaskChrono(task);
            return chronoUtil != null && chronoUtil.isExeceed();
        }
        return false;
    }
    
    private boolean taskHasExpirableStatus(Task task){
        return (task != null && task.getStartAt() != null && task.getStatus() != null 
                && !Arrays.asList(TaskStatus.VALID, TaskStatus.CANCELED).contains(task.getStatus()));
    }

    public void deleteByTaskId(Long taskId) {
        List<TaskStatusTraking> tracks = taskStatusTrakingRepository.findByTaskId(taskId);
        List<Long> trackIds = tracks.stream().map(t -> t.getId()).collect(Collectors.toList());
        fileRepository.deleteByTrack_idIn(trackIds);
        taskStatusTrakingRepository.deleteByTaskId(taskId);
        trackIds.clear();
        trackIds = null;
    }

    public TaskStatusTraking getPreviewTrackBeforeCurrent(Long taskId) {
        List<TaskStatusTraking> lastTracks = taskStatusTrakingRepository.findTop2ByTaskIdOrderByIdDesc(taskId);
        if(lastTracks != null && !lastTracks.isEmpty()){
            if(lastTracks.size() > 1)
                return lastTracks.get(1);
            return lastTracks.get(0);
        }
        return null;
    }
}
