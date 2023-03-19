package com.mshz.service;

import com.mshz.domain.TaskValidationControl;
import com.mshz.repository.TaskValidationControlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskValidationControl}.
 */
@Service
@Transactional
public class TaskValidationControlService {

    private final Logger log = LoggerFactory.getLogger(TaskValidationControlService.class);

    private final TaskValidationControlRepository taskValidationControlRepository;

    public TaskValidationControlService(TaskValidationControlRepository taskValidationControlRepository) {
        this.taskValidationControlRepository = taskValidationControlRepository;
    }

    /**
     * Save a taskValidationControl.
     *
     * @param taskValidationControl the entity to save.
     * @return the persisted entity.
     */
    public TaskValidationControl save(TaskValidationControl taskValidationControl) {
        log.debug("Request to save TaskValidationControl : {}", taskValidationControl);
        if(taskValidationControl.getId() == null)
            taskValidationControl.setValid(true);
        return taskValidationControlRepository.save(taskValidationControl);
    }

    /**
     * Get all the taskValidationControls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskValidationControl> findAll(Pageable pageable) {
        log.debug("Request to get all TaskValidationControls");
        return taskValidationControlRepository.findAll(pageable);
    }


    /**
     * Get one taskValidationControl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskValidationControl> findOne(Long id) {
        log.debug("Request to get TaskValidationControl : {}", id);
        return taskValidationControlRepository.findById(id);
    }

    /**
     * Delete the taskValidationControl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskValidationControl : {}", id);
        taskValidationControlRepository.deleteById(id);
    }
}
