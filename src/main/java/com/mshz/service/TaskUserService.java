package com.mshz.service;

import com.mshz.domain.Task;
import com.mshz.domain.TaskUser;
import com.mshz.repository.TaskUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskUser}.
 */
@Service
@Transactional
public class TaskUserService {

    private final Logger log = LoggerFactory.getLogger(TaskUserService.class);

    private final TaskUserRepository taskUserRepository;

    public TaskUserService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    /**
     * Save a taskUser.
     *
     * @param taskUser the entity to save.
     * @return the persisted entity.
     */
    public TaskUser save(TaskUser taskUser) {
        log.debug("Request to save TaskUser : {}", taskUser);
        return taskUserRepository.save(taskUser);
    }

    /**
     * Get all the taskUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskUser> findAll(Pageable pageable) {
        log.debug("Request to get all TaskUsers");
        return taskUserRepository.findAll(pageable);
    }


    /**
     * Get one taskUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskUser> findOne(Long id) {
        log.debug("Request to get TaskUser : {}", id);
        return taskUserRepository.findById(id);
    }

    /**
     * Delete the taskUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskUser : {}", id);
        taskUserRepository.deleteById(id);
    }
    
    public List<TaskUser> findByTask(Task task){
    	return taskUserRepository.findByTask(task);
    }
    
    public TaskUser copy(TaskUser taskUser, Task newTask) {
    	TaskUser copy = new TaskUser();
    	copy.setUserId(taskUser.getUserId());
    	copy.setTask(newTask);
    	copy.setRole(taskUser.getRole());
    	return taskUserRepository.save(copy);
    }
}
