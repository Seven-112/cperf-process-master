package com.mshz.service;

import com.mshz.domain.Task;
import com.mshz.domain.TaskItem;
import com.mshz.repository.TaskItemRepository;
import com.mshz.repository.TaskRepository;
import com.mshz.webflux.ProcessNotifService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskItem}.
 */
@Service
@Transactional
public class TaskItemService {

    private final Logger log = LoggerFactory.getLogger(TaskItemService.class);

    private final TaskItemRepository taskItemRepository;
    private final TaskRepository taskRepository;
    private final ProcessNotifService notifService;

    public TaskItemService(TaskItemRepository taskItemRepository,
    TaskRepository taskRepository,ProcessNotifService notifService) {
        this.taskItemRepository = taskItemRepository;
        this.taskRepository = taskRepository;
        this.notifService = notifService;
    }

    /**
     * Save a taskItem.
     *
     * @param taskItem the entity to save.
     * @return the persisted entity.
     */
    public TaskItem save(TaskItem taskItem) {
        log.debug("Request to save TaskItem : {}", taskItem);
        boolean isNew = false;
        if(taskItem != null){
            if(taskItem.isChecked() == null)
                taskItem.setChecked(Boolean.FALSE);
            if(taskItem.isRequired() == null)
                taskItem.setRequired(Boolean.FALSE);
            if(taskItem.getId() == null)
                isNew = true;
        }
        taskItem = taskItemRepository.save(taskItem);
        notifService.sendTaskItemCheckNotification(taskItem, isNew);
        autoCheckTaskByItem(taskItem);
        return taskItem;
    }

    /**
     * Get all the taskItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskItem> findAll(Pageable pageable) {
        log.debug("Request to get all TaskItems");
        return taskItemRepository.findAll(pageable);
    }


    /**
     * Get one taskItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskItem> findOne(Long id) {
        log.debug("Request to get TaskItem : {}", id);
        return taskItemRepository.findById(id);
    }

    /**
     * Delete the taskItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskItem : {}", id);
        TaskItem itemToDelete = taskItemRepository.findById(id).orElse(null);
        taskItemRepository.deleteById(id);
        if(itemToDelete != null)
            autoCheckTaskByItem(itemToDelete);
    }

    public List<TaskItem> copyFromTask(Long taskModelId, Task task){
        List<TaskItem> items = new ArrayList<>();
        if(taskModelId != null && task != null && task.getId() != null){
           if(taskItemRepository.countByTaskId(task.getId()) == 0){
                List<TaskItem> itemsToCopy = taskItemRepository.findByTaskId(taskModelId);
                for (TaskItem taskItem : itemsToCopy) {
                    List<TaskItem> exists = taskItemRepository
                        .findByNameAndTaskIdAndCheckerId(taskItem.getName(),
                                 taskItem.getTaskId(), taskItem.getCheckerId());
                    if(exists.isEmpty()){
                        TaskItem copy = clone(taskItem);
                        if(copy != null){
                            copy.setChecked(Boolean.FALSE);
                            copy.setTaskId(task.getId());
                            copy = taskItemRepository.saveAndFlush(copy);
                            if(copy != null && copy.getId() != null)
                                items.add(copy);
                        }
                    }
                }
                // auto check task
                if(!items.isEmpty()){
                    autoCheckTaskByItem(items.get(0));
                }
            }
        }
        return items;
    }

    public TaskItem clone(TaskItem item){
        if(item != null){
            TaskItem clone = new TaskItem();
            clone.setChecked(Boolean.FALSE);
            clone.setCheckerEmail(item.getCheckerEmail());
            clone.setCheckerId(item.getCheckerId());
            clone.setCheckerName(item.getCheckerName());
            clone.setEditorEmail(item.getEditorEmail());
            clone.setEditorId(item.getEditorId());
            clone.setName(item.getName());
            clone.setRequired(item.isRequired());
            clone.setTaskId(item.getTaskId());
            clone.setEditorName(item.getEditorName());
            return clone;
        }
        return null;
    }

    private void autoCheckTaskByItem(TaskItem item){
        if(item != null && item.getTaskId() != null){
            try {
                Task task = taskRepository.getOne(item.getTaskId());
                if(task != null){
                    TaskItem requiredUnchecked = taskItemRepository
                            .findFirstByTaskIdAndRequiredAndChecked(item.getTaskId(), Boolean.TRUE, Boolean.FALSE);
                    if(requiredUnchecked != null)
                        task.setChecked(Boolean.FALSE);
                    else
                        task.setChecked(Boolean.TRUE);
                    taskRepository.saveAndFlush(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
