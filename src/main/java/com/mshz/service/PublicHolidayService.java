package com.mshz.service;

import com.mshz.domain.PublicHoliday;
import com.mshz.repository.PublicHolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PublicHoliday}.
 */
@Service
@Transactional
public class PublicHolidayService {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayService.class);

    private final PublicHolidayRepository publicHolidayRepository;

    public PublicHolidayService(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
    }

    /**
     * Save a publicHoliday.
     *
     * @param publicHoliday the entity to save.
     * @return the persisted entity.
     */
    public PublicHoliday save(PublicHoliday publicHoliday) {
        log.debug("Request to save PublicHoliday : {}", publicHoliday);
        return publicHolidayRepository.save(publicHoliday);
    }

    /**
     * Get all the publicHolidays.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicHoliday> findAll() {
        log.debug("Request to get all PublicHolidays");
        return publicHolidayRepository.findAll();
    }


    /**
     * Get one publicHoliday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicHoliday> findOne(Long id) {
        log.debug("Request to get PublicHoliday : {}", id);
        return publicHolidayRepository.findById(id);
    }

    /**
     * Delete the publicHoliday by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PublicHoliday : {}", id);
        publicHolidayRepository.deleteById(id);
    }

    /* custom methodes */

    public boolean isPublicHoliday(Date date){
        if(date == null)
            date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        return publicHolidayRepository.findByOfDate(localDate).orElse(null) != null;
    }

    public List<PublicHoliday> findByOfDateBetween(LocalDate dateMin,LocalDate dateMax){
        return publicHolidayRepository.findByOfDateBetween(dateMin, dateMax);
    }
}
