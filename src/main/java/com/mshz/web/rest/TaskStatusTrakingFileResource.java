package com.mshz.web.rest;

import com.mshz.domain.TaskStatusTrakingFile;
import com.mshz.service.TaskStatusTrakingFileService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskStatusTrakingFileCriteria;
import com.mshz.service.TaskStatusTrakingFileQueryService;

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
 * REST controller for managing {@link com.mshz.domain.TaskStatusTrakingFile}.
 */
@RestController
@RequestMapping("/api")
public class TaskStatusTrakingFileResource {

    private final Logger log = LoggerFactory.getLogger(TaskStatusTrakingFileResource.class);

    private static final String ENTITY_NAME = "microprocessTaskStatusTrakingFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskStatusTrakingFileService taskStatusTrakingFileService;

    private final TaskStatusTrakingFileQueryService taskStatusTrakingFileQueryService;

    public TaskStatusTrakingFileResource(TaskStatusTrakingFileService taskStatusTrakingFileService, TaskStatusTrakingFileQueryService taskStatusTrakingFileQueryService) {
        this.taskStatusTrakingFileService = taskStatusTrakingFileService;
        this.taskStatusTrakingFileQueryService = taskStatusTrakingFileQueryService;
    }

    /**
     * {@code POST  /task-status-traking-files} : Create a new taskStatusTrakingFile.
     *
     * @param taskStatusTrakingFile the taskStatusTrakingFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskStatusTrakingFile, or with status {@code 400 (Bad Request)} if the taskStatusTrakingFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-status-traking-files")
    public ResponseEntity<TaskStatusTrakingFile> createTaskStatusTrakingFile(@Valid @RequestBody TaskStatusTrakingFile taskStatusTrakingFile) throws URISyntaxException {
        log.debug("REST request to save TaskStatusTrakingFile : {}", taskStatusTrakingFile);
        if (taskStatusTrakingFile.getId() != null) {
            throw new BadRequestAlertException("A new taskStatusTrakingFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStatusTrakingFile result = taskStatusTrakingFileService.save(taskStatusTrakingFile);
        return ResponseEntity.created(new URI("/api/task-status-traking-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-status-traking-files} : Updates an existing taskStatusTrakingFile.
     *
     * @param taskStatusTrakingFile the taskStatusTrakingFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStatusTrakingFile,
     * or with status {@code 400 (Bad Request)} if the taskStatusTrakingFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskStatusTrakingFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-status-traking-files")
    public ResponseEntity<TaskStatusTrakingFile> updateTaskStatusTrakingFile(@Valid @RequestBody TaskStatusTrakingFile taskStatusTrakingFile) throws URISyntaxException {
        log.debug("REST request to update TaskStatusTrakingFile : {}", taskStatusTrakingFile);
        if (taskStatusTrakingFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskStatusTrakingFile result = taskStatusTrakingFileService.save(taskStatusTrakingFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStatusTrakingFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-status-traking-files} : get all the taskStatusTrakingFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskStatusTrakingFiles in body.
     */
    @GetMapping("/task-status-traking-files")
    public ResponseEntity<List<TaskStatusTrakingFile>> getAllTaskStatusTrakingFiles(TaskStatusTrakingFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskStatusTrakingFiles by criteria: {}", criteria);
        Page<TaskStatusTrakingFile> page = taskStatusTrakingFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-status-traking-files/count} : count all the taskStatusTrakingFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-status-traking-files/count")
    public ResponseEntity<Long> countTaskStatusTrakingFiles(TaskStatusTrakingFileCriteria criteria) {
        log.debug("REST request to count TaskStatusTrakingFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskStatusTrakingFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-status-traking-files/:id} : get the "id" taskStatusTrakingFile.
     *
     * @param id the id of the taskStatusTrakingFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskStatusTrakingFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-status-traking-files/{id}")
    public ResponseEntity<TaskStatusTrakingFile> getTaskStatusTrakingFile(@PathVariable Long id) {
        log.debug("REST request to get TaskStatusTrakingFile : {}", id);
        Optional<TaskStatusTrakingFile> taskStatusTrakingFile = taskStatusTrakingFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskStatusTrakingFile);
    }

    /**
     * {@code DELETE  /task-status-traking-files/:id} : delete the "id" taskStatusTrakingFile.
     *
     * @param id the id of the taskStatusTrakingFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-status-traking-files/{id}")
    public ResponseEntity<Void> deleteTaskStatusTrakingFile(@PathVariable Long id) {
        log.debug("REST request to delete TaskStatusTrakingFile : {}", id);
        taskStatusTrakingFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
