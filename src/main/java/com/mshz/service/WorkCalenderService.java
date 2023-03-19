package com.mshz.service;

import com.mshz.domain.WorkCalender;
import com.mshz.repository.WorkCalenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

/**
 * Service Implementation for managing {@link WorkCalender}.
 */
@Service
@Transactional
public class WorkCalenderService {

    private final Logger log = LoggerFactory.getLogger(WorkCalenderService.class);

    private final WorkCalenderRepository workCalenderRepository;

    public WorkCalenderService(WorkCalenderRepository workCalenderRepository) {
        this.workCalenderRepository = workCalenderRepository;
    }

    /**
     * Save a workCalender.
     *
     * @param workCalender the entity to save.
     * @return the persisted entity.
     */
    public WorkCalender save(WorkCalender workCalender) {
        log.debug("Request to save WorkCalender : {}", workCalender);
        if(workCalender != null)
            workCalender.setDayNumber(formDayOfWeekToIsoNorm(workCalender.getDayNumber()));
        return workCalenderRepository.save(workCalender);
    }

    /**
     * Get all the workCalenders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WorkCalender> findAll() {
        log.debug("Request to get all WorkCalenders");
        return workCalenderRepository.findAll();
    }


    /**
     * Get one workCalender by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkCalender> findOne(Long id) {
        log.debug("Request to get WorkCalender : {}", id);
        return workCalenderRepository.findById(id);
    }

    /**
     * Delete the workCalender by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkCalender : {}", id);
        workCalenderRepository.deleteById(id);
    }

    /* custom methods */

    public boolean isWorkingTime(Date date){
        try {
            if(date == null)
                date = new Date();

            ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            List<WorkCalender> calendars = workCalenderRepository
                                  .findByDayNumber(Integer.valueOf(zdt.getDayOfWeek().getValue()));
            for (WorkCalender workCalender : calendars) {
                if(zonedDateTimeInWorkingInstants(zdt, workCalender.getStartTime(), workCalender.getEndTime()))
                    return true;
            }
            
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
        }
        return false;
    }

    private boolean zonedDateTimeInWorkingInstants(ZonedDateTime zdt, Instant startInstant, Instant endInstant){
        try {
            if(zdt != null && startInstant != null && endInstant != null){

                Instant start = startInstant.isAfter(endInstant) ? endInstant : startInstant;

                Instant end = endInstant.isBefore(startInstant) ? startInstant : endInstant;

                ZonedDateTime startZdt = ZonedDateTime.ofInstant(start,  ZoneId.systemDefault())
                                                        .withYear(zdt.getYear())
                                                        .withMonth(zdt.getMonthValue())
                                                        .withDayOfMonth(zdt.getDayOfMonth());
                                                        
                ZonedDateTime endZdt = ZonedDateTime.ofInstant(end,  ZoneId.systemDefault())
                                                        .withYear(zdt.getYear())
                                                        .withMonth(zdt.getMonthValue())
                                                        .withDayOfMonth(zdt.getDayOfMonth()); 
                
                return ((zdt.isAfter(startZdt) || zdt.isEqual(startZdt)) && (zdt.isBefore(endZdt) || zdt.isEqual(endZdt)));   
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error {}", e.getMessage());
        }
        return false;
    }

    public Integer formDayOfWeekToIsoNorm(Integer day){
        if(day != null && day.intValue() == 0)
            return Integer.valueOf(7);
        return day;
    }
}
