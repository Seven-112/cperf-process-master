package com.mshz.web.rest;

import com.mshz.domain.TaskValidationControl;
import com.mshz.service.TaskValidationControlService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskValidationControlCriteria;
import com.mshz.service.TaskValidationControlQueryService;

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
 * REST controller for managing {@link com.mshz.domain.TaskValidationControl}.
 */
@RestController
@RequestMapping("/api")
public class TaskValidationControlResource {

    private final Logger log = LoggerFactory.getLogger(TaskValidationControlResource.class);

    private static final String ENTITY_NAME = "microprocessTaskValidationControl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskValidationControlService taskValidationControlService;

    private final TaskValidationControlQueryService taskValidationControlQueryService;

    public TaskValidationControlResource(TaskValidationControlService taskValidationControlService, TaskValidationControlQueryService taskValidationControlQueryService) {
        this.taskValidationControlService = taskValidationControlService;
        this.taskValidationControlQueryService = taskValidationControlQueryService;
    }

    /**
     * {@code POST  /task-validation-controls} : Create a new taskValidationControl.
     *
     * @param taskValidationControl the taskValidationControl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskValidationControl, or with status {@code 400 (Bad Request)} if the taskValidationControl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-validation-controls")
    public ResponseEntity<TaskValidationControl> createTaskValidationControl(@Valid @RequestBody TaskValidationControl taskValidationControl) throws URISyntaxException {
        log.debug("REST request to save TaskValidationControl : {}", taskValidationControl);
        if (taskValidationControl.getId() != null) {
            throw new BadRequestAlertException("A new taskValidationControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskValidationControl result = taskValidationControlService.save(taskValidationControl);
        return ResponseEntity.created(new URI("/api/task-validation-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-validation-controls} : Updates an existing taskValidationControl.
     *
     * @param taskValidationControl the taskValidationControl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskValidationControl,
     * or with status {@code 400 (Bad Request)} if the taskValidationControl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskValidationControl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-validation-controls")
    public ResponseEntity<TaskValidationControl> updateTaskValidationControl(@Valid @RequestBody TaskValidationControl taskValidationControl) throws URISyntaxException {
        log.debug("REST request to update TaskValidationControl : {}", taskValidationControl);
        if (taskValidationControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskValidationControl result = taskValidationControlService.save(taskValidationControl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskValidationControl.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-validation-controls} : get all the taskValidationControls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskValidationControls in body.
     */
    @GetMapping("/task-validation-controls")
    public ResponseEntity<List<TaskValidationControl>> getAllTaskValidationControls(TaskValidationControlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskValidationControls by criteria: {}", criteria);
        Page<TaskValidationControl> page = taskValidationControlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-validation-controls/count} : count all the taskValidationControls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-validation-controls/count")
    public ResponseEntity<Long> countTaskValidationControls(TaskValidationControlCriteria criteria) {
        log.debug("REST request to count TaskValidationControls by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskValidationControlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-validation-controls/:id} : get the "id" taskValidationControl.
     *
     * @param id the id of the taskValidationControl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskValidationControl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-validation-controls/{id}")
    public ResponseEntity<TaskValidationControl> getTaskValidationControl(@PathVariable Long id) {
        log.debug("REST request to get TaskValidationControl : {}", id);
        Optional<TaskValidationControl> taskValidationControl = taskValidationControlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskValidationControl);
    }

    /**
     * {@code DELETE  /task-validation-controls/:id} : delete the "id" taskValidationControl.
     *
     * @param id the id of the taskValidationControl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-validation-controls/{id}")
    public ResponseEntity<Void> deleteTaskValidationControl(@PathVariable Long id) {
        log.debug("REST request to delete TaskValidationControl : {}", id);
        taskValidationControlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
