package com.mshz.service;

import com.mshz.domain.Task;
import com.mshz.domain.TaskFile;
import com.mshz.domain.enumeration.TaskFileType;
import com.mshz.repository.TaskFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskFile}.
 */
@Service
@Transactional
public class TaskFileService {

    private final Logger log = LoggerFactory.getLogger(TaskFileService.class);

    private final TaskFileRepository taskFileRepository;

    public TaskFileService(TaskFileRepository taskFileRepository) {
        this.taskFileRepository = taskFileRepository;
    }

    /**
     * Save a taskFile.
     *
     * @param taskFile the entity to save.
     * @return the persisted entity.
     */
    public TaskFile save(TaskFile taskFile) {
        log.debug("Request to save TaskFile : {}", taskFile);
        return taskFileRepository.save(taskFile);
    }

    /**
     * Get all the taskFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskFile> findAll(Pageable pageable) {
        log.debug("Request to get all TaskFiles");
        return taskFileRepository.findAll(pageable);
    }


    /**
     * Get one taskFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskFile> findOne(Long id) {
        log.debug("Request to get TaskFile : {}", id);
        return taskFileRepository.findById(id);
    }

    /**
     * Delete the taskFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskFile : {}", id);
        taskFileRepository.deleteById(id);
    }
    
    
    public List<TaskFile> findByTaskAndType(Task task, TaskFileType type) {
    	return taskFileRepository.findByTaskAndType(task, type);
    }
    
    public TaskFile copy(TaskFile file, Task newTask) {
    	TaskFile copy = new TaskFile();
    	copy.setTask(newTask);
    	copy.setFileId(file.getFileId());
    	copy.setDescription(file.getDescription());
    	copy.setType(file.getType());
    	return taskFileRepository.save(copy);
    }
}
