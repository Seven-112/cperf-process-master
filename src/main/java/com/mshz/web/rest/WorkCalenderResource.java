package com.mshz.web.rest;

import com.mshz.domain.WorkCalender;
import com.mshz.service.WorkCalenderService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.WorkCalenderCriteria;
import com.mshz.service.WorkCalenderQueryService;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.domain.WorkCalender}.
 */
@RestController
@RequestMapping("/api")
public class WorkCalenderResource {

    private final Logger log = LoggerFactory.getLogger(WorkCalenderResource.class);

    private static final String ENTITY_NAME = "microprocessWorkCalender";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkCalenderService workCalenderService;

    private final WorkCalenderQueryService workCalenderQueryService;

    public WorkCalenderResource(WorkCalenderService workCalenderService, WorkCalenderQueryService workCalenderQueryService) {
        this.workCalenderService = workCalenderService;
        this.workCalenderQueryService = workCalenderQueryService;
    }

    /**
     * {@code POST  /work-calenders} : Create a new workCalender.
     *
     * @param workCalender the workCalender to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workCalender, or with status {@code 400 (Bad Request)} if the workCalender has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-calenders")
    public ResponseEntity<WorkCalender> createWorkCalender(@Valid @RequestBody WorkCalender workCalender) throws URISyntaxException {
        log.debug("REST request to save WorkCalender : {}", workCalender);
        if (workCalender.getId() != null) {
            throw new BadRequestAlertException("A new workCalender cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkCalender result = workCalenderService.save(workCalender);
        return ResponseEntity.created(new URI("/api/work-calenders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-calenders} : Updates an existing workCalender.
     *
     * @param workCalender the workCalender to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workCalender,
     * or with status {@code 400 (Bad Request)} if the workCalender is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workCalender couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-calenders")
    public ResponseEntity<WorkCalender> updateWorkCalender(@Valid @RequestBody WorkCalender workCalender) throws URISyntaxException {
        log.debug("REST request to update WorkCalender : {}", workCalender);
        if (workCalender.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkCalender result = workCalenderService.save(workCalender);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workCalender.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-calenders} : get all the workCalenders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workCalenders in body.
     */
    @GetMapping("/work-calenders")
    public ResponseEntity<List<WorkCalender>> getAllWorkCalenders(WorkCalenderCriteria criteria) {
        log.debug("REST request to get WorkCalenders by criteria: {}", criteria);
        List<WorkCalender> entityList = workCalenderQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /work-calenders/count} : count all the workCalenders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-calenders/count")
    public ResponseEntity<Long> countWorkCalenders(WorkCalenderCriteria criteria) {
        log.debug("REST request to count WorkCalenders by criteria: {}", criteria);
        return ResponseEntity.ok().body(workCalenderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-calenders/:id} : get the "id" workCalender.
     *
     * @param id the id of the workCalender to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workCalender, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-calenders/{id}")
    public ResponseEntity<WorkCalender> getWorkCalender(@PathVariable Long id) {
        log.debug("REST request to get WorkCalender : {}", id);
        Optional<WorkCalender> workCalender = workCalenderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workCalender);
    }

    /**
     * {@code DELETE  /work-calenders/:id} : delete the "id" workCalender.
     *
     * @param id the id of the workCalender to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-calenders/{id}")
    public ResponseEntity<Void> deleteWorkCalender(@PathVariable Long id) {
        log.debug("REST request to delete WorkCalender : {}", id);
        workCalenderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
