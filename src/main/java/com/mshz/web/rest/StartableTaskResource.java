package com.mshz.web.rest;

import com.mshz.domain.StartableTask;
import com.mshz.service.StartableTaskService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.StartableTaskCriteria;
import com.mshz.service.StartableTaskQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.domain.StartableTask}.
 */
@RestController
@RequestMapping("/api")
public class StartableTaskResource {

    private final Logger log = LoggerFactory.getLogger(StartableTaskResource.class);

    private static final String ENTITY_NAME = "microprocessStartableTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StartableTaskService startableTaskService;

    private final StartableTaskQueryService startableTaskQueryService;

    public StartableTaskResource(StartableTaskService startableTaskService, StartableTaskQueryService startableTaskQueryService) {
        this.startableTaskService = startableTaskService;
        this.startableTaskQueryService = startableTaskQueryService;
    }

    /**
     * {@code POST  /startable-tasks} : Create a new startableTask.
     *
     * @param startableTask the startableTask to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new startableTask, or with status {@code 400 (Bad Request)} if the startableTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/startable-tasks")
    public ResponseEntity<StartableTask> createStartableTask(@RequestBody StartableTask startableTask) throws URISyntaxException {
        log.debug("REST request to save StartableTask : {}", startableTask);
        if (startableTask.getId() != null) {
            throw new BadRequestAlertException("A new startableTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StartableTask result = startableTaskService.save(startableTask);
        return ResponseEntity.created(new URI("/api/startable-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /startable-tasks} : Updates an existing startableTask.
     *
     * @param startableTask the startableTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated startableTask,
     * or with status {@code 400 (Bad Request)} if the startableTask is not valid,
     * or with status {@code 500 (Internal Server Error)} if the startableTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/startable-tasks")
    public ResponseEntity<StartableTask> updateStartableTask(@RequestBody StartableTask startableTask) throws URISyntaxException {
        log.debug("REST request to update StartableTask : {}", startableTask);
        if (startableTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StartableTask result = startableTaskService.save(startableTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, startableTask.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /startable-tasks} : get all the startableTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of startableTasks in body.
     */
    @GetMapping("/startable-tasks")
    public ResponseEntity<List<StartableTask>> getAllStartableTasks(StartableTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StartableTasks by criteria: {}", criteria);
        Page<StartableTask> page = startableTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /startable-tasks/count} : count all the startableTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/startable-tasks/count")
    public ResponseEntity<Long> countStartableTasks(StartableTaskCriteria criteria) {
        log.debug("REST request to count StartableTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(startableTaskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /startable-tasks/:id} : get the "id" startableTask.
     *
     * @param id the id of the startableTask to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the startableTask, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/startable-tasks/{id}")
    public ResponseEntity<StartableTask> getStartableTask(@PathVariable Long id) {
        log.debug("REST request to get StartableTask : {}", id);
        Optional<StartableTask> startableTask = startableTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(startableTask);
    }

    /**
     * {@code DELETE  /startable-tasks/:id} : delete the "id" startableTask.
     *
     * @param id the id of the startableTask to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/startable-tasks/{id}")
    public ResponseEntity<Void> deleteStartableTask(@PathVariable Long id) {
        log.debug("REST request to delete StartableTask : {}", id);
        startableTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
