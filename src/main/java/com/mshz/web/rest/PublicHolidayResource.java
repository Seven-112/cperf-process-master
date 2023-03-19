package com.mshz.web.rest;

import com.mshz.domain.PublicHoliday;
import com.mshz.service.PublicHolidayService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.PublicHolidayCriteria;
import com.mshz.service.PublicHolidayQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.domain.PublicHoliday}.
 */
@RestController
@RequestMapping("/api")
public class PublicHolidayResource {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayResource.class);

    private static final String ENTITY_NAME = "microprocessPublicHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicHolidayService publicHolidayService;

    private final PublicHolidayQueryService publicHolidayQueryService;

    public PublicHolidayResource(PublicHolidayService publicHolidayService, PublicHolidayQueryService publicHolidayQueryService) {
        this.publicHolidayService = publicHolidayService;
        this.publicHolidayQueryService = publicHolidayQueryService;
    }

    /**
     * {@code POST  /public-holidays} : Create a new publicHoliday.
     *
     * @param publicHoliday the publicHoliday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicHoliday, or with status {@code 400 (Bad Request)} if the publicHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-holidays")
    public ResponseEntity<PublicHoliday> createPublicHoliday(@Valid @RequestBody PublicHoliday publicHoliday) throws URISyntaxException {
        log.debug("REST request to save PublicHoliday : {}", publicHoliday);
        if (publicHoliday.getId() != null) {
            throw new BadRequestAlertException("A new publicHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicHoliday result = publicHolidayService.save(publicHoliday);
        return ResponseEntity.created(new URI("/api/public-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-holidays} : Updates an existing publicHoliday.
     *
     * @param publicHoliday the publicHoliday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHoliday,
     * or with status {@code 400 (Bad Request)} if the publicHoliday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicHoliday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-holidays")
    public ResponseEntity<PublicHoliday> updatePublicHoliday(@Valid @RequestBody PublicHoliday publicHoliday) throws URISyntaxException {
        log.debug("REST request to update PublicHoliday : {}", publicHoliday);
        if (publicHoliday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicHoliday result = publicHolidayService.save(publicHoliday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publicHoliday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /public-holidays} : get all the publicHolidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicHolidays in body.
     */
    @GetMapping("/public-holidays")
    public ResponseEntity<List<PublicHoliday>> getAllPublicHolidays(PublicHolidayCriteria criteria) {
        log.debug("REST request to get PublicHolidays by criteria: {}", criteria);
        List<PublicHoliday> entityList = publicHolidayQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /public-holidays/count} : count all the publicHolidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/public-holidays/count")
    public ResponseEntity<Long> countPublicHolidays(PublicHolidayCriteria criteria) {
        log.debug("REST request to count PublicHolidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(publicHolidayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /public-holidays/:id} : get the "id" publicHoliday.
     *
     * @param id the id of the publicHoliday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicHoliday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-holidays/{id}")
    public ResponseEntity<PublicHoliday> getPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to get PublicHoliday : {}", id);
        Optional<PublicHoliday> publicHoliday = publicHolidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicHoliday);
    }

    /**
     * {@code DELETE  /public-holidays/:id} : delete the "id" publicHoliday.
     *
     * @param id the id of the publicHoliday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-holidays/{id}")
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        log.debug("REST request to delete PublicHoliday : {}", id);
        publicHolidayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/public-holidays/between")
    public ResponseEntity<List<PublicHoliday>> getPublicHolidaysBeteewn(LocalDate dateMin, LocalDate dateMax) {
        log.debug("REST request to get PublicHolidays between data: {} {}", dateMin, dateMax);
        List<PublicHoliday> entityList = publicHolidayService.findByOfDateBetween(dateMin, dateMax);
        return ResponseEntity.ok().body(entityList);
    }
}
