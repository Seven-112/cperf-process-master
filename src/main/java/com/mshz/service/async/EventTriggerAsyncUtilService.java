package com.mshz.service.async;

import com.mshz.service.EventTriggerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EventTriggerAsyncUtilService {
    private final Logger log = LoggerFactory.getLogger(EventTriggerAsyncUtilService.class);

    private final EventTriggerService triggerService;

    public EventTriggerAsyncUtilService(EventTriggerService triggerService){
        this.triggerService = triggerService;
    }

    @Async
    @Scheduled(cron = "${sheduling.job.cron.event-trigger}")
    public void autoCreatePlannedInstances(){
        log.info("event trigger intance auto-ceated");
        triggerService.autoCreateSheduledInstances();
    }
}
