package com.mshz.web.rest;

import com.mshz.domain.TaskFile;
import com.mshz.service.TaskFileService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskFileCriteria;
import com.mshz.service.TaskFileQueryService;

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
 * REST controller for managing {@link com.mshz.domain.TaskFile}.
 */
@RestController
@RequestMapping("/api")
public class TaskFileResource {

    private final Logger log = LoggerFactory.getLogger(TaskFileResource.class);

    private static final String ENTITY_NAME = "microprocessTaskFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskFileService taskFileService;

    private final TaskFileQueryService taskFileQueryService;

    public TaskFileResource(TaskFileService taskFileService, TaskFileQueryService taskFileQueryService) {
        this.taskFileService = taskFileService;
        this.taskFileQueryService = taskFileQueryService;
    }

    /**
     * {@code POST  /task-files} : Create a new taskFile.
     *
     * @param taskFile the taskFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskFile, or with status {@code 400 (Bad Request)} if the taskFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-files")
    public ResponseEntity<TaskFile> createTaskFile(@Valid @RequestBody TaskFile taskFile) throws URISyntaxException {
        log.debug("REST request to save TaskFile : {}", taskFile);
        if (taskFile.getId() != null) {
            throw new BadRequestAlertException("A new taskFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskFile result = taskFileService.save(taskFile);
        return ResponseEntity.created(new URI("/api/task-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-files} : Updates an existing taskFile.
     *
     * @param taskFile the taskFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskFile,
     * or with status {@code 400 (Bad Request)} if the taskFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-files")
    public ResponseEntity<TaskFile> updateTaskFile(@Valid @RequestBody TaskFile taskFile) throws URISyntaxException {
        log.debug("REST request to update TaskFile : {}", taskFile);
        if (taskFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskFile result = taskFileService.save(taskFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-files} : get all the taskFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskFiles in body.
     */
    @GetMapping("/task-files")
    public ResponseEntity<List<TaskFile>> getAllTaskFiles(TaskFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskFiles by criteria: {}", criteria);
        Page<TaskFile> page = taskFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-files/count} : count all the taskFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-files/count")
    public ResponseEntity<Long> countTaskFiles(TaskFileCriteria criteria) {
        log.debug("REST request to count TaskFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-files/:id} : get the "id" taskFile.
     *
     * @param id the id of the taskFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-files/{id}")
    public ResponseEntity<TaskFile> getTaskFile(@PathVariable Long id) {
        log.debug("REST request to get TaskFile : {}", id);
        Optional<TaskFile> taskFile = taskFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskFile);
    }

    /**
     * {@code DELETE  /task-files/:id} : delete the "id" taskFile.
     *
     * @param id the id of the taskFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-files/{id}")
    public ResponseEntity<Void> deleteTaskFile(@PathVariable Long id) {
        log.debug("REST request to delete TaskFile : {}", id);
        taskFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
