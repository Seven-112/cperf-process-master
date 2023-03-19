package com.mshz.service;

import com.mshz.domain.Process;
import com.mshz.domain.StartableTask;
import com.mshz.domain.Task;
import com.mshz.repository.ProcessRepository;
import com.mshz.repository.StartableTaskRepository;
import com.mshz.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StartableTask}.
 */
@Service
@Transactional
public class StartableTaskService {

    private final Logger log = LoggerFactory.getLogger(StartableTaskService.class);

    private final StartableTaskRepository startableTaskRepository;

    private final ProcessRepository processRepository;

    private final TaskRepository taskRepository;

    public StartableTaskService(StartableTaskRepository startableTaskRepository,
        ProcessRepository processRepository,TaskRepository taskRepository) {
        this.startableTaskRepository = startableTaskRepository;
        this.processRepository = processRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Save a startableTask.
     *
     * @param startableTask the entity to save.
     * @return the persisted entity.
     */
    public StartableTask save(StartableTask startableTask) {
        log.debug("Request to save StartableTask : {}", startableTask);
        startableTask = normalize(startableTask);
        return startableTaskRepository.save(startableTask);
    }

    /**
     * Get all the startableTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StartableTask> findAll(Pageable pageable) {
        log.debug("Request to get all StartableTasks");
        return startableTaskRepository.findAll(pageable);
    }


    /**
     * Get one startableTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StartableTask> findOne(Long id) {
        log.debug("Request to get StartableTask : {}", id);
        return startableTaskRepository.findById(id);
    }

    /**
     * Delete the startableTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StartableTask : {}", id);
        startableTaskRepository.deleteById(id);
    }

    public StartableTask normalize(StartableTask entity){
        if(entity != null){
            if(entity.getCreatedAt() == null){
                entity.setCreatedAt(Instant.now());
            }
            // normalize trigger field
            if(entity.getTriggerProcessName() == null && entity.getTriggerTaskId() != null){
                Task triggerTask = taskRepository.findById(entity.getTriggerTaskId()).orElse(null);
                if(triggerTask != null){
                    entity.setTriggerTaskName(triggerTask.getName());
                    Process triggerProcess = processRepository.findById(triggerTask.getProcessId()).orElse(null);
                    if(triggerProcess != null)
                        entity.setTriggerProcessName(triggerProcess.getLabel());
                }
            }

            // normalize startable field
            if(entity.getStartableProcessName() == null && entity.getStartableTaskId() != null){
                Task startable = taskRepository.findById(entity.getStartableTaskId()).orElse(null);
                if(startable != null){
                    entity.setStartableTaskName(startable.getName());
                    Process startableProcess = processRepository.findById(startable.getProcessId()).orElse(null);
                    if(startableProcess != null)
                        entity.setStartableProcessName(startableProcess.getLabel());
                }
            }
        }
        return entity;
    }
}
