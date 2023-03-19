package com.mshz.service;

import com.mshz.domain.TaskStatusTrakingFile;
import com.mshz.repository.TaskStatusTrakingFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskStatusTrakingFile}.
 */
@Service
@Transactional
public class TaskStatusTrakingFileService {

    private final Logger log = LoggerFactory.getLogger(TaskStatusTrakingFileService.class);

    private final TaskStatusTrakingFileRepository taskStatusTrakingFileRepository;

    public TaskStatusTrakingFileService(TaskStatusTrakingFileRepository taskStatusTrakingFileRepository) {
        this.taskStatusTrakingFileRepository = taskStatusTrakingFileRepository;
    }

    /**
     * Save a taskStatusTrakingFile.
     *
     * @param taskStatusTrakingFile the entity to save.
     * @return the persisted entity.
     */
    public TaskStatusTrakingFile save(TaskStatusTrakingFile taskStatusTrakingFile) {
        log.debug("Request to save TaskStatusTrakingFile : {}", taskStatusTrakingFile);
        return taskStatusTrakingFileRepository.save(taskStatusTrakingFile);
    }

    /**
     * Get all the taskStatusTrakingFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStatusTrakingFile> findAll(Pageable pageable) {
        log.debug("Request to get all TaskStatusTrakingFiles");
        return taskStatusTrakingFileRepository.findAll(pageable);
    }


    /**
     * Get one taskStatusTrakingFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskStatusTrakingFile> findOne(Long id) {
        log.debug("Request to get TaskStatusTrakingFile : {}", id);
        return taskStatusTrakingFileRepository.findById(id);
    }

    /**
     * Delete the taskStatusTrakingFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskStatusTrakingFile : {}", id);
        taskStatusTrakingFileRepository.deleteById(id);
    }
}
