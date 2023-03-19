package com.mshz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mshz.domain.Task;
import com.mshz.service.TaskStatusTrakingService;

@Aspect
@Component
public class TaskAspect {
    
    private final Logger log = LoggerFactory.getLogger(TaskAspect.class);
    
    private final TaskStatusTrakingService taskStatusTrakingService;
    
   public TaskAspect(TaskStatusTrakingService taskStatusTrakingService) {
       this.taskStatusTrakingService = taskStatusTrakingService;
   }
    
    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.sav*(..)) ")
    private void anyTaskSave() {}
    
    @AfterReturning(value = "anyTaskSave()", returning = "result")
    public void trackSavingTask(JoinPoint joinPoint, Object result) {
        try {
            Task task = (Task) result;
            log.debug("Task save tracking by aspet => task: {}", task);
            if(task != null && task.getId() != null) {
                taskStatusTrakingService.traceTaskStatus(task);
            }
        }catch (ClassCastException e) {
            log.info("Task tracking  error: {}", e.getMessage());
        }catch (Exception e) {
            System.err.println("Task tracking  error2");
            e.printStackTrace();
        }
    }
}
