package com.mshz.service;

import com.mshz.domain.TaskSubmission;
import com.mshz.repository.TaskSubmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskSubmission}.
 */
@Service
@Transactional
public class TaskSubmissionService {

    private final Logger log = LoggerFactory.getLogger(TaskSubmissionService.class);

    private final TaskSubmissionRepository taskSubmissionRepository;

    public TaskSubmissionService(TaskSubmissionRepository taskSubmissionRepository) {
        this.taskSubmissionRepository = taskSubmissionRepository;
    }

    /**
     * Save a taskSubmission.
     *
     * @param taskSubmission the entity to save.
     * @return the persisted entity.
     */
    public TaskSubmission save(TaskSubmission taskSubmission) {
        log.debug("Request to save TaskSubmission : {}", taskSubmission);
        return taskSubmissionRepository.save(taskSubmission);
    }

    /**
     * Get all the taskSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskSubmission> findAll(Pageable pageable) {
        log.debug("Request to get all TaskSubmissions");
        return taskSubmissionRepository.findAll(pageable);
    }


    /**
     * Get one taskSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskSubmission> findOne(Long id) {
        log.debug("Request to get TaskSubmission : {}", id);
        return taskSubmissionRepository.findById(id);
    }

    /**
     * Delete the taskSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskSubmission : {}", id);
        taskSubmissionRepository.deleteById(id);
    }
}
