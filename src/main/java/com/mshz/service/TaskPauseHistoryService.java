package com.mshz.service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.domain.Task;
import com.mshz.domain.TaskPauseHistory;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.model.ChronoUtil;
import com.mshz.repository.TaskPauseHistoryRepository;

/**
 * Service Implementation for managing {@link TaskPauseHistory}.
 */
@Service
@Transactional
public class TaskPauseHistoryService {

    private final Logger log = LoggerFactory.getLogger(TaskPauseHistoryService.class);

    private final TaskPauseHistoryRepository taskPauseHistoryRepository;

    public TaskPauseHistoryService(TaskPauseHistoryRepository taskPauseHistoryRepository) {
        this.taskPauseHistoryRepository = taskPauseHistoryRepository;
    }

    /**
     * Save a taskPauseHistory.
     *
     * @param taskPauseHistory the entity to save.
     * @return the persisted entity.
     */
    public TaskPauseHistory save(TaskPauseHistory taskPauseHistory) {
        log.debug("Request to save TaskPauseHistory : {}", taskPauseHistory);
        return taskPauseHistoryRepository.save(taskPauseHistory);
    }

    /**
     * Get all the taskPauseHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskPauseHistory> findAll(Pageable pageable) {
        log.debug("Request to get all TaskPauseHistories");
        return taskPauseHistoryRepository.findAll(pageable);
    }


    /**
     * Get one taskPauseHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskPauseHistory> findOne(Long id) {
        log.debug("Request to get TaskPauseHistory : {}", id);
        return taskPauseHistoryRepository.findById(id);
    }

    /**
     * Delete the taskPauseHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskPauseHistory : {}", id);
        taskPauseHistoryRepository.deleteById(id);
    }

    public TaskPauseHistory trackTaskPause(Task task, Instant date){
        TaskPauseHistory history = new TaskPauseHistory();
        if(task != null){
        	history.setTaskId(task.getId());
            history.setOldTaskstatus(task.getStatus());
            history.setPausedAt(date != null ? date : Instant.now());
            // ChronoUtil chronoUtil = getTaskChrono(task);
            // history.setTaskExecutionDeleyExeceed(chronoUtil.isExeceed());
            history.setTaskExecutionDeleyExeceed(null);
            history.setRestartedAt(null);
            return taskPauseHistoryRepository.save(history);
        }
        return history;
    }

    public TaskPauseHistory updateRestartedAt(Long id){
        log.info("task pause history id: {}", id);
        try {
        	TaskPauseHistory history = taskPauseHistoryRepository.getOne(id);
            if(history != null){
                 history.setRestartedAt(Instant.now());
                 return taskPauseHistoryRepository.save(history);
            };
		} catch (Exception e) {
			e.printStackTrace();
	        log.info("task pause history error: {}", e.getMessage());
		}
        return null;
    }

    public ChronoUtil getTaskChrono(Task task){
        if(task != null && task.getStartAt() != null){
            // calcul max finish date
            ZonedDateTime zdt = ZonedDateTime.ofInstant(task.getStartAt(), ZoneId.systemDefault())
            .plusYears(task.getNbYears() != null ? task.getNbYears().longValue() : 0)
            .plusMonths(task.getNbMonths() != null ? task.getNbMonths().longValue() : 0)
            .plusDays(task.getNbDays() != null ? task.getNbDays().longValue() : 0)
            .plusHours(task.getNbHours() != null ? task.getNbHours().longValue() : 0)
            .plusMinutes(task.getNbMinuites() != null ? task.getNbMinuites().longValue() : 0)
            // .plusSeconds(calculeElapsedSecondsInTaskPause(task.getId(), Boolean.FALSE))
            // .plusSeconds(calculeElapsedSecondsInTaskPause(task.getId(), Boolean.TRUE));
            .plusSeconds(calculeAllPausedTimes(task.getId()));

            Instant previewFinishDate = zdt.toInstant();

            boolean execeed =  (task.getStatus() == TaskStatus.COMPLETED && task.getFinishAt() != null)
                ? previewFinishDate.isBefore(task.getFinishAt()) : previewFinishDate.isBefore(Instant.now());

            return new ChronoUtil(task.getStartAt(), task.getPauseAt(), 
                    previewFinishDate, task.getFinishAt(), task.getStatus(), execeed);

        }
        return null;
    }
    

    public long calculeAllPausedTimes(Long taskId){
        long seconds = 0;
        Pageable pageable = PageRequest.of(0, 200);
        while(pageable != null){
            Page<TaskPauseHistory> page = taskPauseHistoryRepository.findByTaskIdAndPausedAtNotNullAndRestartedAtNotNull(taskId,pageable);
            for (TaskPauseHistory history : page.getContent()) {
                seconds = seconds + Duration
                        .between(history.getPausedAt(), history.getRestartedAt())
                            .abs().toSeconds();
            }
            // increment or breack loop by setting pageable to null
            pageable = page.hasNext() ? page.nextPageable() : null;
        }
        return seconds;
    }

    public long calculeElapsedSecondsInTaskPause(Long taskId, Boolean executionTimeExeceed){
        long seconds = 0;
        List<TaskPauseHistory> histories = taskPauseHistoryRepository
            .findByTaskIdAndTaskExecutionDeleyExeceedAndPausedAtNotNullAndRestartedAtNotNull(taskId,executionTimeExeceed);
        for (TaskPauseHistory history : histories) {
            seconds = seconds + Duration
                    .between(history.getPausedAt(), history.getRestartedAt())
                        .abs().toSeconds();
        }
        return seconds;
    }

    public void deleteByTaskId(Long taskId){
        taskPauseHistoryRepository.deleteByTaskId(taskId);
    }
}
