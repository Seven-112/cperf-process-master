package com.mshz.web.rest;

import com.mshz.domain.TaskStatusTraking;
import com.mshz.service.TaskStatusTrakingService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskStatusTrakingCriteria;
import com.mshz.service.TaskStatusTrakingQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.domain.TaskStatusTraking}.
 */
@RestController
@RequestMapping("/api")
public class TaskStatusTrakingResource {

    private final Logger log = LoggerFactory.getLogger(TaskStatusTrakingResource.class);

    private static final String ENTITY_NAME = "microprocessTaskStatusTraking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskStatusTrakingService taskStatusTrakingService;

    private final TaskStatusTrakingQueryService taskStatusTrakingQueryService;

    public TaskStatusTrakingResource(TaskStatusTrakingService taskStatusTrakingService, TaskStatusTrakingQueryService taskStatusTrakingQueryService) {
        this.taskStatusTrakingService = taskStatusTrakingService;
        this.taskStatusTrakingQueryService = taskStatusTrakingQueryService;
    }

    /**
     * {@code POST  /task-status-trakings} : Create a new taskStatusTraking.
     *
     * @param taskStatusTraking the taskStatusTraking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskStatusTraking, or with status {@code 400 (Bad Request)} if the taskStatusTraking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-status-trakings")
    public ResponseEntity<TaskStatusTraking> createTaskStatusTraking(@Valid @RequestBody TaskStatusTraking taskStatusTraking) throws URISyntaxException {
        log.debug("REST request to save TaskStatusTraking : {}", taskStatusTraking);
        if (taskStatusTraking.getId() != null) {
            throw new BadRequestAlertException("A new taskStatusTraking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStatusTraking result = taskStatusTrakingService.save(taskStatusTraking);
        return ResponseEntity.created(new URI("/api/task-status-trakings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-status-trakings} : Updates an existing taskStatusTraking.
     *
     * @param taskStatusTraking the taskStatusTraking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStatusTraking,
     * or with status {@code 400 (Bad Request)} if the taskStatusTraking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskStatusTraking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-status-trakings")
    public ResponseEntity<TaskStatusTraking> updateTaskStatusTraking(@Valid @RequestBody TaskStatusTraking taskStatusTraking) throws URISyntaxException {
        log.debug("REST request to update TaskStatusTraking : {}", taskStatusTraking);
        if (taskStatusTraking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskStatusTraking result = taskStatusTrakingService.save(taskStatusTraking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStatusTraking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-status-trakings} : get all the taskStatusTrakings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskStatusTrakings in body.
     */
    @GetMapping("/task-status-trakings")
    public ResponseEntity<List<TaskStatusTraking>> getAllTaskStatusTrakings(TaskStatusTrakingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskStatusTrakings by criteria: {}", criteria);
        Page<TaskStatusTraking> page = taskStatusTrakingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-status-trakings/count} : count all the taskStatusTrakings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-status-trakings/count")
    public ResponseEntity<Long> countTaskStatusTrakings(TaskStatusTrakingCriteria criteria) {
        log.debug("REST request to count TaskStatusTrakings by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskStatusTrakingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-status-trakings/:id} : get the "id" taskStatusTraking.
     *
     * @param id the id of the taskStatusTraking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskStatusTraking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-status-trakings/{id}")
    public ResponseEntity<TaskStatusTraking> getTaskStatusTraking(@PathVariable Long id) {
        log.debug("REST request to get TaskStatusTraking : {}", id);
        Optional<TaskStatusTraking> taskStatusTraking = taskStatusTrakingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskStatusTraking);
    }

    /**
     * {@code DELETE  /task-status-trakings/:id} : delete the "id" taskStatusTraking.
     *
     * @param id the id of the taskStatusTraking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-status-trakings/{id}")
    public ResponseEntity<Void> deleteTaskStatusTraking(@PathVariable Long id) {
        log.debug("REST request to delete TaskStatusTraking : {}", id);
        taskStatusTrakingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
