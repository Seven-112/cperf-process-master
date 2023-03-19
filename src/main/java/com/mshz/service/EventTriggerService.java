package com.mshz.service;

import com.mshz.domain.EventTrigger;
import com.mshz.domain.Process;
import com.mshz.domain.enumeration.ProcessEventRecurrence;
import com.mshz.repository.EventTriggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EventTrigger}.
 */
@Service
@Transactional
public class EventTriggerService {

    private final Logger log = LoggerFactory.getLogger(EventTriggerService.class);

    private final EventTriggerRepository eventTriggerRepository;
    private final ProcessService processService;

    public EventTriggerService(EventTriggerRepository eventTriggerRepository,ProcessService processService) {
        this.eventTriggerRepository = eventTriggerRepository;
        this.processService = processService;
    }

    /**
     * Save a eventTrigger.
     *
     * @param eventTrigger the entity to save.
     * @return the persisted entity.
     */
    public EventTrigger save(EventTrigger eventTrigger) {
        log.debug("Request to save EventTrigger : {}", eventTrigger);
        if(eventTrigger != null && eventTrigger.getId() == null){
            eventTrigger.setDisabled(Boolean.FALSE);
            eventTrigger.setCreatedAt(LocalDate.now());
        }
        if(eventTrigger != null && eventTrigger.getStartCount() == null)
            eventTrigger.setStartCount(0);
        return eventTriggerRepository.save(eventTrigger);
    }

    /**
     * Get all the eventTriggers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventTrigger> findAll(Pageable pageable) {
        log.debug("Request to get all EventTriggers");
        return eventTriggerRepository.findAll(pageable);
    }


    /**
     * Get one eventTrigger by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventTrigger> findOne(Long id) {
        log.debug("Request to get EventTrigger : {}", id);
        return eventTriggerRepository.findById(id);
    }

    /**
     * Delete the eventTrigger by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventTrigger : {}", id);
        eventTriggerRepository.deleteById(id);
    }

    public void autoCreateSheduledInstances(){

        LocalDate localDate = LocalDate.now();

        List<EventTrigger> events = eventTriggerRepository
            .findByNextStartAtLessThanEqualAndDisabledNotAndProcessIsNotNull(localDate, Boolean.TRUE);
        log.info("findedEvents {} at {} ", events, localDate);
        for (EventTrigger event : events) {
            String dossier = event.getStartCount() != null && event.getStartCount().intValue() > 0 ?
                             event.getName().concat("( "+(event.getStartCount().intValue() +1) + " )") :
                             event.getName();
           Process processInstance = processService.createInstance(dossier, event.getProcess().getId(), null, null);
           if(processInstance != null){
               if(event.getFirstStartedAt() == null)
                    event.setFirstStartedAt(localDate);
                event = setNextShudeledOnAfter(event);
                Integer startCount = event.getStartCount() != null ? (event.getStartCount().intValue() +1) : 1;
                event.setStartCount(startCount);
               eventTriggerRepository.save(event);
           }
        }
        events = null;
    }

    public EventTrigger setNextShudeledOnAfter(EventTrigger event){
        if(event != null){
            if(event.getNextStartAt() != null){
                LocalDate nextStartDate = event.getNextStartAt();
                if(event.getRecurrence() != null && event.getRecurrence() != ProcessEventRecurrence.ONCE){
                    if(event.getRecurrence() == ProcessEventRecurrence.EVERY_WEEK_ON_DAY){
                        event.setNextStartAt(nextStartDate.plusDays(7));
                    }else if(event.getRecurrence() == ProcessEventRecurrence.EVERY_YEAR_ON_DATE){
                        int month = nextStartDate.getMonthValue();
                        int dayOfMonth = nextStartDate.getDayOfMonth();
                        event.setNextStartAt(nextStartDate.plusYears(1).withMonth(month).withDayOfMonth(dayOfMonth));
                    }else if(event.getRecurrence() == ProcessEventRecurrence.WEEK){
                        if(nextStartDate.getDayOfWeek() == DayOfWeek.FRIDAY)
                            event.setNextStartAt(nextStartDate.plusDays(3));
                        else if(nextStartDate.getDayOfWeek() == DayOfWeek.SATURDAY)
                            event.setNextStartAt(nextStartDate.plusDays(2));
                        else
                            event.setNextStartAt(nextStartDate.plusDays(1));
                    }else if(event.getRecurrence() == ProcessEventRecurrence.EVERY_MONTH_OF_DAY_POSITION){
                       event.setNextStartAt(getNextDateByDayPos(nextStartDate));
                    }else if(event.getRecurrence() == ProcessEventRecurrence.EVERY_MONTH){
                        LocalDate nextDt = null;
                        int monthPlusNumber = 1;
                        // lopp is nessaray if net date has not dayofMonths
                        while(nextDt == null){
                            try {
                                nextDt = nextStartDate
                                    .plusMonths(monthPlusNumber)
                                    .withDayOfMonth(nextStartDate.getDayOfMonth());
                                monthPlusNumber++;
                            } catch (Exception e) {
                                log.error("error {}", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        event.setNextStartAt(nextDt);
                    }else{
                        event.setNextStartAt(nextStartDate.plusDays(1));
                    }
                }else{
                    event.setNextStartAt(null);
                }
            }else{
                if(event.getFirstStartedAt() != null)
                    event.setNextStartAt(event.getFirstStartedAt());
            }
        }
        return event;
    }

    private LocalDate getNextDateByDayPos(LocalDate date){
        if(date != null){
            int dayPos = 0;
            
            // find day postion
            LocalDate monthZdt = LocalDate.now().withDayOfMonth(1);

            while(monthZdt.getMonthValue() == date.getMonthValue()){
                if(monthZdt.getDayOfWeek() == date.getDayOfWeek())
                    dayPos++;
                if(monthZdt.getDayOfMonth() == date.getDayOfMonth())
                    break;
                else
                    monthZdt = monthZdt.plusDays(1); // incrementation
            }

            // find next date by day pos
            LocalDate nextMothZdt = LocalDate.now()
                .withYear(date.getYear())
                .withMonth(date.getMonthValue() +1) // next month
                .withDayOfMonth(1);

            int currentMonth = nextMothZdt.getMonthValue();

            int currentPos = 0;

            while(dayPos != 0){
                // cpt reinitialization
                if(nextMothZdt.getMonthValue() != currentMonth){
                    currentPos = 0; 
                    currentMonth = nextMothZdt.getMonthValue();
                }
                // finding day by position
                if(nextMothZdt.getDayOfWeek() == date.getDayOfWeek())
                    currentPos++;
                
                if(currentPos == dayPos)
                    break;
                else
                    nextMothZdt = nextMothZdt.plusDays(1); // incrementation
            }

            return nextMothZdt;

        }
        return null;
    }
}
