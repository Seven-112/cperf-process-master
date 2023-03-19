package com.mshz.web.rest;

import com.mshz.domain.TaskSubmission;
import com.mshz.service.TaskSubmissionService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskSubmissionCriteria;
import com.mshz.service.TaskSubmissionQueryService;

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
 * REST controller for managing {@link com.mshz.domain.TaskSubmission}.
 */
@RestController
@RequestMapping("/api")
public class TaskSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(TaskSubmissionResource.class);

    private static final String ENTITY_NAME = "microprocessTaskSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskSubmissionService taskSubmissionService;

    private final TaskSubmissionQueryService taskSubmissionQueryService;

    public TaskSubmissionResource(TaskSubmissionService taskSubmissionService, TaskSubmissionQueryService taskSubmissionQueryService) {
        this.taskSubmissionService = taskSubmissionService;
        this.taskSubmissionQueryService = taskSubmissionQueryService;
    }

    /**
     * {@code POST  /task-submissions} : Create a new taskSubmission.
     *
     * @param taskSubmission the taskSubmission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskSubmission, or with status {@code 400 (Bad Request)} if the taskSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-submissions")
    public ResponseEntity<TaskSubmission> createTaskSubmission(@Valid @RequestBody TaskSubmission taskSubmission) throws URISyntaxException {
        log.debug("REST request to save TaskSubmission : {}", taskSubmission);
        if (taskSubmission.getId() != null) {
            throw new BadRequestAlertException("A new taskSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSubmission result = taskSubmissionService.save(taskSubmission);
        return ResponseEntity.created(new URI("/api/task-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-submissions} : Updates an existing taskSubmission.
     *
     * @param taskSubmission the taskSubmission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSubmission,
     * or with status {@code 400 (Bad Request)} if the taskSubmission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskSubmission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-submissions")
    public ResponseEntity<TaskSubmission> updateTaskSubmission(@Valid @RequestBody TaskSubmission taskSubmission) throws URISyntaxException {
        log.debug("REST request to update TaskSubmission : {}", taskSubmission);
        if (taskSubmission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskSubmission result = taskSubmissionService.save(taskSubmission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskSubmission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-submissions} : get all the taskSubmissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskSubmissions in body.
     */
    @GetMapping("/task-submissions")
    public ResponseEntity<List<TaskSubmission>> getAllTaskSubmissions(TaskSubmissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskSubmissions by criteria: {}", criteria);
        Page<TaskSubmission> page = taskSubmissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-submissions/count} : count all the taskSubmissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-submissions/count")
    public ResponseEntity<Long> countTaskSubmissions(TaskSubmissionCriteria criteria) {
        log.debug("REST request to count TaskSubmissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskSubmissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-submissions/:id} : get the "id" taskSubmission.
     *
     * @param id the id of the taskSubmission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskSubmission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-submissions/{id}")
    public ResponseEntity<TaskSubmission> getTaskSubmission(@PathVariable Long id) {
        log.debug("REST request to get TaskSubmission : {}", id);
        Optional<TaskSubmission> taskSubmission = taskSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskSubmission);
    }

    /**
     * {@code DELETE  /task-submissions/:id} : delete the "id" taskSubmission.
     *
     * @param id the id of the taskSubmission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-submissions/{id}")
    public ResponseEntity<Void> deleteTaskSubmission(@PathVariable Long id) {
        log.debug("REST request to delete TaskSubmission : {}", id);
        taskSubmissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
