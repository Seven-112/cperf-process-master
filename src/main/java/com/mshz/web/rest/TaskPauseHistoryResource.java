package com.mshz.web.rest;

import com.mshz.domain.TaskPauseHistory;
import com.mshz.service.TaskPauseHistoryService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskPauseHistoryCriteria;
import com.mshz.service.TaskPauseHistoryQueryService;

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
 * REST controller for managing {@link com.mshz.domain.TaskPauseHistory}.
 */
@RestController
@RequestMapping("/api")
public class TaskPauseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TaskPauseHistoryResource.class);

    private static final String ENTITY_NAME = "microprocessTaskPauseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskPauseHistoryService taskPauseHistoryService;

    private final TaskPauseHistoryQueryService taskPauseHistoryQueryService;

    public TaskPauseHistoryResource(TaskPauseHistoryService taskPauseHistoryService, TaskPauseHistoryQueryService taskPauseHistoryQueryService) {
        this.taskPauseHistoryService = taskPauseHistoryService;
        this.taskPauseHistoryQueryService = taskPauseHistoryQueryService;
    }

    /**
     * {@code POST  /task-pause-histories} : Create a new taskPauseHistory.
     *
     * @param taskPauseHistory the taskPauseHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskPauseHistory, or with status {@code 400 (Bad Request)} if the taskPauseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-pause-histories")
    public ResponseEntity<TaskPauseHistory> createTaskPauseHistory(@Valid @RequestBody TaskPauseHistory taskPauseHistory) throws URISyntaxException {
        log.debug("REST request to save TaskPauseHistory : {}", taskPauseHistory);
        if (taskPauseHistory.getId() != null) {
            throw new BadRequestAlertException("A new taskPauseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskPauseHistory result = taskPauseHistoryService.save(taskPauseHistory);
        return ResponseEntity.created(new URI("/api/task-pause-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-pause-histories} : Updates an existing taskPauseHistory.
     *
     * @param taskPauseHistory the taskPauseHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskPauseHistory,
     * or with status {@code 400 (Bad Request)} if the taskPauseHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskPauseHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-pause-histories")
    public ResponseEntity<TaskPauseHistory> updateTaskPauseHistory(@Valid @RequestBody TaskPauseHistory taskPauseHistory) throws URISyntaxException {
        log.debug("REST request to update TaskPauseHistory : {}", taskPauseHistory);
        if (taskPauseHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskPauseHistory result = taskPauseHistoryService.save(taskPauseHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskPauseHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-pause-histories} : get all the taskPauseHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskPauseHistories in body.
     */
    @GetMapping("/task-pause-histories")
    public ResponseEntity<List<TaskPauseHistory>> getAllTaskPauseHistories(TaskPauseHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskPauseHistories by criteria: {}", criteria);
        Page<TaskPauseHistory> page = taskPauseHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-pause-histories/count} : count all the taskPauseHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-pause-histories/count")
    public ResponseEntity<Long> countTaskPauseHistories(TaskPauseHistoryCriteria criteria) {
        log.debug("REST request to count TaskPauseHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskPauseHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-pause-histories/:id} : get the "id" taskPauseHistory.
     *
     * @param id the id of the taskPauseHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskPauseHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-pause-histories/{id}")
    public ResponseEntity<TaskPauseHistory> getTaskPauseHistory(@PathVariable Long id) {
        log.debug("REST request to get TaskPauseHistory : {}", id);
        Optional<TaskPauseHistory> taskPauseHistory = taskPauseHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskPauseHistory);
    }

    /**
     * {@code DELETE  /task-pause-histories/:id} : delete the "id" taskPauseHistory.
     *
     * @param id the id of the taskPauseHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-pause-histories/{id}")
    public ResponseEntity<Void> deleteTaskPauseHistory(@PathVariable Long id) {
        log.debug("REST request to delete TaskPauseHistory : {}", id);
        taskPauseHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
