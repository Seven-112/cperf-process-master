package com.mshz.webflux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import com.mshz.domain.Process;
import com.mshz.domain.Task;
import com.mshz.domain.TaskItem;
import com.mshz.domain.TaskStatusTraking;
import com.mshz.domain.TaskUser;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.TaskUserRole;
import com.mshz.repository.ProcessRepository;
import com.mshz.repository.TaskItemRepository;
import com.mshz.repository.TaskRepository;
import com.mshz.repository.TaskUserRepository;
import com.mshz.webflux.dto.NotifAction;
import com.mshz.webflux.dto.NotifDTO;
import com.mshz.webflux.dto.NotifEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessNotifService {

    Logger logger = LoggerFactory.getLogger(ProcessNotifService.class);
    
    private final GatewayClientService gatewayClientService;

    private final TaskUserRepository taskUserRepository;

    private final ProcessRepository processRepository;

    private final TaskRepository taskRepository;

    private final TaskItemRepository taskItemRepository;

    public ProcessNotifService(
    GatewayClientService gatewayClientService,
    TaskUserRepository taskUserRepository, 
    ProcessRepository processRepository,
    TaskRepository taskRepository,
    TaskItemRepository taskItemRepository){
        this.gatewayClientService = gatewayClientService;
        this.taskUserRepository = taskUserRepository;
        this.processRepository = processRepository;
        this.taskRepository = taskRepository;
        this.taskItemRepository = taskItemRepository;
    }

    @Async
    public void sendTaskSatatusChangeNote(Task task){
        logger.info("sending status change notif for task: {}", task);
        if(task != null){
            List<TaskUser> users = getNotifUserByTaskStatus(task);
            NotifAction action = null;
            switch(task.getStatus()){
                case STARTED:
                    action = NotifAction.TASK_STARTED;
                    break;
                case CANCELED:
                    action = NotifAction.TASK_CANCELED;
                    break;
                case EXECUTED:
                    action = NotifAction.TASK_EXECUTED;
                    break;
                case SUBMITTED:
                    action = NotifAction.TASK_SUBMITTED;
                    break;/* 
                case ON_PAUSE:
                    action = NotifAction.TASK_ON_PAUSE;
                    targetUserIds = users.stream()
                    .map(t -> t.getUserId()).sorted().collect(Collectors.toList());
                    break; */
                case COMPLETED:
                    action = NotifAction.TASK_COMPLETED;
                    break;
                case VALID:
                    action = NotifAction.TASK_VALID;
                    break;
                default:
                    action = null;
            } // swith end


            // entities settings
            List<NotifEntity> entities = new ArrayList<>();
            // task settings to entities
            entities.add(new NotifEntity(task.getId(), task.getName()));
            // instance and process settings to notif entities
            if(task.getProcessId() != null){
                // settings process
                Process instance = processRepository.findById(task.getProcessId()).orElse(null);
                if(instance != null){
                    entities.add(new NotifEntity(instance.getId(), instance.getLabel()));
                    // setting process
                    if(instance.getModelId() != null){
                        Process process = processRepository.findById(instance.getModelId()).orElse(null);
                        if(process != null)
                            entities.add(new NotifEntity(process.getId(), process.getLabel()));
                    }
                }
            }
            // end setting entity

            // sendiding notif to checker controller
            if(action == NotifAction.TASK_STARTED 
                || action == NotifAction.TASK_EXECUTED
                || action == NotifAction.TASK_SUBMITTED){
                    sendTaskItemCheckersNotification(task,action, entities);
            }

            if(!users.isEmpty() && action != null){
                NotifDTO note = new NotifDTO();
                note.setAction(action);
                // target unification
                List<Long> targetUserIds = users.stream()
                .filter(u -> u.getUserId() != null)
                .map(u -> u.getUserId().longValue())
                .sorted().distinct().map(id -> Long.valueOf(id)).collect(Collectors.toList());
                // targets setting to notificati
                note.setTargetUserIds(targetUserIds);
                note.setEntities(entities);
                // sending notification to gateway api service
                gatewayClientService.sendNotificationToGateway(note);
            }

            if(task.getProcessId() != null && action != null && Arrays.asList(NotifAction.TASK_CANCELED, NotifAction.TASK_COMPLETED).contains(action)){
                sendProcessExecutedOrCanceledNotification(task.getProcessId(), task.getId());
            }
        }
    }

    @Async
    public void sendTaskLogEditNote(TaskStatusTraking track,boolean isNew){
        logger.info("sending task track note {}", track);
        if(track != null){
            Task task = taskRepository.findById(track.getTaskId()).orElse(null);
            if(task != null){
                Process instance = processRepository.findById(task.getProcessId()).orElse(null);
                Process  process = (instance != null && instance.getModelId() != null)
                                ?  processRepository.findById(instance.getModelId()).orElse(null) : null;

                List<TaskUser> users = taskUserRepository.findByTask_id(track.getTaskId());
                List<Long> targetUserIds = users.stream()
                .filter(tu -> tu.getUserId() != null && !tu.getUserId().equals(track.getUserId()))
                .map(tu -> tu.getUserId().longValue()).sorted().distinct()
                .map(id -> Long.valueOf(id)).collect(Collectors.toList());

                // adding process editor user to notify target if is ponctual query
                if(process != null && processAccociatedToPonctualQuery(process) && process.getEditorId() != null){
                    targetUserIds.add(process.getEditorId());
                }
                logger.debug("notif target users: {}", targetUserIds);
                if(targetUserIds != null && !targetUserIds.isEmpty()){
                    List<NotifEntity> entities = new ArrayList<>();
                    entities.add(new NotifEntity(task.getId(), task.getName()));
                    if(instance != null){
                        entities.add(new NotifEntity(instance.getId(), instance.getLabel()));
                        if(process != null)
                            entities.add(new NotifEntity(process.getId(), process.getLabel()));
                    }
                    // setting notigication
                    NotifDTO note = new NotifDTO();
                    note.setAction(isNew ? NotifAction.TASK_LOG_CREATED : NotifAction.TASK_LOG_UPDATED);
                    note.setEntities(entities);
                    note.setTargetUserIds(targetUserIds);
                    // sending notification to gateway api service
                    gatewayClientService.sendNotificationToGateway(note);
                    // end task null check
                }
          }
        } // end track  null check
    }

    private boolean processAccociatedToPonctualQuery(final Process process) {
        long tasksCount = 0;
        if(process != null && process.getQueryId() != null){
            tasksCount = taskRepository.count((root, cq, cb) ->{
                Predicate p1 = cb.equal(root.get("processId"), process.getId());
                Predicate p2 = cb.isTrue(root.get("valid"));
                return cb.and(p1, p2);
            });
        }
        return tasksCount == 1;
    }


    @Async
    public void sendTaskItemCheckNotification(TaskItem taskItem, boolean isNew) {
        logger.info("sending task item notification {} is created: {}", taskItem, isNew);
        if(taskItem != null){
            Task task = taskRepository.findById(taskItem.getTaskId()).orElse(null);
            if(task != null && task.getStatus() != null &&
                !task.getStatus().equals(TaskStatus.VALID) && !task.getStatus().equals(TaskStatus.CANCELED)){
                // setting notications entities
                List<NotifEntity> entities = new ArrayList<>();
                // sending task item to entities
                entities.add(new NotifEntity(taskItem.getId(), taskItem.getName()));
                // sending task to entities
                entities.add(new NotifEntity(task.getId(), task.getName()));
                // sending task instance to entities
                Process instance = processRepository.findById(task.getProcessId()).orElse(null);
                if(instance != null){
                    entities.add(new NotifEntity(instance.getId(), instance.getLabel()));
                    // seting process to entities
                    Process process = processRepository.findById(instance.getModelId()).orElse(null);
                    if(process != null)
                        entities.add(new NotifEntity(process.getId(), process.getLabel()));
                }

                NotifAction action = null;
                List<Long> targetIds = new ArrayList<>();
                if(isNew){
                    targetIds.add(taskItem.getCheckerId());
                    action = NotifAction.TASK_ITEM_TO_CHECK;
                }else{
                    if(taskItem.isChecked() != null && taskItem.isChecked().booleanValue() == true){
                        if(taskItem.getEditorId() != null)
                            targetIds.add(taskItem.getEditorId());
                        action = NotifAction.TASK_ITEM_CHECKED;
                        List<Long> userIds = getNotifUserByTaskStatus(task).stream()
                            .filter(u -> u.getUserId() != null && 
                                !u.getUserId().equals(taskItem.getEditorId())
                                && !u.getUserId().equals(taskItem.getCheckerId()))
                            .map(u -> u.getUserId()).collect(Collectors.toList());
                        if(userIds != null && !userIds.isEmpty())
                            targetIds.addAll(userIds);
                    }
                }

                if(action != null && !targetIds.isEmpty()){
                    // sending notification
                    NotifDTO note = new NotifDTO();
                    note.setAction(action);
                    note.setEntities(entities);
                    note.setTargetUserIds(targetIds);

                    gatewayClientService.sendNotificationToGateway(note);
                    
                }

            } // end task null check if
        }
    }

    @Async
    public void sendProcessExecutedOrCanceledNotification(Long processId, Long triggerTaskId){
        Process process = processRepository.findById(processId).orElse(null);
        logger.debug("sendProcessExecutedOrCanceledNotification: {} ", processId);
        if(process != null && Boolean.TRUE.equals(process.isValid()) && process.getModelId() != null){
            TaskStatus processStatus =  getProcessStatusByTasks(processId, triggerTaskId);
            // prepare target user id
            Long processEditorId = process.getEditorId();
            if(processEditorId == null){
                Process parentProcess = processRepository.findById(process.getModelId()).orElse(null);
                if(parentProcess != null)
                    processEditorId = parentProcess.getEditorId();
            }

            logger.debug("sendProcessExecutedOrCanceledNotification processStatus: {} ", processStatus);
            if(processEditorId != null && processStatus != null && Arrays.asList(TaskStatus.CANCELED,TaskStatus.COMPLETED).contains(processStatus)){
                // setting notigication
                NotifDTO note = new NotifDTO();
                note.setAction(processStatus.equals(TaskStatus.CANCELED) ? NotifAction.PROCESS_CANCELED : NotifAction.PROCESS_EXECUTED);
                note.setEntities(Arrays.asList(new NotifEntity(processId, process.getLabel())));
                note.setTargetUserIds(Arrays.asList(processEditorId));
                // sending notification to gateway api service
                logger.debug("sendProcessExecutedOrCanceledNotification fullfiled by process id: {} ", processId);
                gatewayClientService.sendNotificationToGateway(note);
            }
        }
    }


   private TaskStatus getProcessStatusByTasks(Long processId, Long triggerTaskId){
    List<Task> tasks = taskRepository.findByProcessId(processId);
    if(tasks != null && !tasks.isEmpty()){
       if(triggerTaskId != null)
         tasks = tasks.stream().filter(t -> !triggerTaskId.equals(t.getId())).collect(Collectors.toList());

       if(tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.STARTED)
        || tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.SUBMITTED)
        || tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.EXECUTED)){
         return TaskStatus.STARTED;
       }

       if(tasks.stream().allMatch(t -> t.getStatus() == TaskStatus.CANCELED)){
         return TaskStatus.CANCELED;
       }

       if(tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.COMPLETED)
             // && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.VALID)
             && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.ON_PAUSE)
             && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.EXECUTED)
             && tasks.stream().noneMatch(t -> t.getStatus() == TaskStatus.SUBMITTED)){
         return TaskStatus.COMPLETED;
       }

       if(tasks.stream().anyMatch(t -> t.getStatus() == TaskStatus.ON_PAUSE)){
         return TaskStatus.ON_PAUSE;
       }
       
    }
    return TaskStatus.VALID;
}

    private List<TaskUser> getNotifUserByTaskStatus(Task task){
        List<TaskUser> users = new ArrayList<>();
        if(task != null){
            switch(task.getStatus()){
                case STARTED:
                    users = taskUserRepository.findByTask_idAndRole(
                        task.getTaskModelId() != null? task.getTaskModelId() : task.getId() , TaskUserRole.EXCEUTOR);
                    break;
                case CANCELED:
                    users = taskUserRepository.findByTask_id(task.getId());
                    break;
                case EXECUTED:
                    users = taskUserRepository.findByTask_idAndRole(task.getId(), TaskUserRole.SUBMITOR);
                    break;
                case SUBMITTED:
                    users = taskUserRepository.findByTask_idAndRole(task.getId(), TaskUserRole.VALIDATOR);
                    break;/* 
                case ON_PAUSE:
                    action = NotifAction.TASK_ON_PAUSE;
                    targetUserIds = users.stream()
                    .map(t -> t.getUserId()).sorted().collect(Collectors.toList());
                    break; */
                case COMPLETED:
                    users = taskUserRepository.findByTask_idAndRole(task.getId(), TaskUserRole.SUBMITOR);
                    break;
                case VALID:
                    users = taskUserRepository.findByTask_idAndRole(task.getId(), TaskUserRole.SUBMITOR);
                    break;
                default:
                  // notings
            } // swith end
        }
        return users;
    }

    private void sendTaskItemCheckersNotification(Task task, NotifAction triggerAction,  List<NotifEntity> entities){
        if(task != null){
            List<TaskItem> taskItems = new ArrayList<>();
            taskItems = taskItemRepository
                .findByTaskIdAndCheckedIsNot(task.getId(), Boolean.TRUE);

            if(task.getTaskModelId() != null && taskItems.isEmpty() && triggerAction == NotifAction.TASK_STARTED){
                taskItems = taskItemRepository
                    .findByTaskIdAndCheckedIsNot(task.getTaskModelId(), Boolean.TRUE);
            }
    
            List<Long> targetIds = taskItems.stream()
                .filter(item -> item.getCheckerId() != null)
                .map(item -> item.getCheckerId().longValue())
                .distinct()
                .map(id -> Long.valueOf(id))
                .collect(Collectors.toList());

            if(targetIds != null && !targetIds.isEmpty()){
                // sending notification
                NotifDTO note = new NotifDTO();
                note.setAction(NotifAction.TASK_TO_CHECK);
                note.setEntities(entities);
                note.setTargetUserIds(targetIds);
                gatewayClientService.sendNotificationToGateway(note);
            }
        }
    }
}
