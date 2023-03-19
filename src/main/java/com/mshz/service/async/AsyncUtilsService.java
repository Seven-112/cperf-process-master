package com.mshz.service.async;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.mshz.domain.Task;
import com.mshz.domain.enumeration.TaskFileType;
import com.mshz.model.PonctualTaskUtil;
import com.mshz.service.KPIService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AsyncUtilsService {

    private final Logger log = LoggerFactory.getLogger(AsyncUtilsService.class);

    private final UtilService utilService;

    private final KPIService kpiService;


    public AsyncUtilsService(UtilService utilService,KPIService kpiService) {
        this.utilService = utilService;
        this.kpiService = kpiService;
    }

    /**
     * util method to start all startup tasks for task
     * @param task : task asssociated startup tasks
     */
    @Async
    public void startStratupTasks(Task task, boolean sendNotification){
        try{
            utilService.startStatupTask(task, sendNotification);
        }catch(Exception e){
            e.printStackTrace();
            log.info("starting tasks error");
        }
    }

    /**
     * copy task files 
     */
    @Async
    public void copyTaskSFiles(List<Task> tasks, TaskFileType taskFileType){
        utilService.copyTaskSFiles(tasks, taskFileType);
    }

    @Async
    public void copyTasksTaskItems(List<Task> tasks){
        utilService.copyTaskitemsFormTasks(tasks);
    }

    /**
     * 
     * @param tasks
     */
    public void copyTasksUsers(List<Task> tasks) {
        utilService.copyTasksUsers(tasks);
    }

    @Async
    @Scheduled(fixedDelay = 30000, initialDelay = 10000) // 10 seconds and initialize on 10 seconds
    public void autoPlayOrPauseTasks(){
        utilService.autoPlayTasks();
        utilService.autoPauseTasks();
        utilService.autoPlaySheduledTaks();
    }

    
    @Async
     @Scheduled(cron = "${sheduling.job.cron.kpi}")
    public void autoCalCuleAndSaveKPIs(){
        LocalDate localDate = LocalDate.now();
        kpiService.calculAndSaveAllUsersKPis(localDate);
        kpiService.calculAndSaveGlobalKPIs(localDate);
     }
    

    @Async
    public void updatePonctualTaskExecTime(@Valid PonctualTaskUtil ponctualTaskUtil) {
        utilService.updatePonctualTaskExecTime(ponctualTaskUtil, 1);
    }
}
