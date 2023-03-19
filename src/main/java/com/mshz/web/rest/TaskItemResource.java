package com.mshz.web.rest;

import com.mshz.domain.TaskItem;
import com.mshz.service.TaskItemService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.TaskItemCriteria;
import com.mshz.service.TaskItemQueryService;

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
 * REST controller for managing {@link com.mshz.domain.TaskItem}.
 */
@RestController
@RequestMapping("/api")
public class TaskItemResource {

    private final Logger log = LoggerFactory.getLogger(TaskItemResource.class);

    private static final String ENTITY_NAME = "microprocessTaskItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskItemService taskItemService;

    private final TaskItemQueryService taskItemQueryService;

    public TaskItemResource(TaskItemService taskItemService, TaskItemQueryService taskItemQueryService) {
        this.taskItemService = taskItemService;
        this.taskItemQueryService = taskItemQueryService;
    }

    /**
     * {@code POST  /task-items} : Create a new taskItem.
     *
     * @param taskItem the taskItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskItem, or with status {@code 400 (Bad Request)} if the taskItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-items")
    public ResponseEntity<TaskItem> createTaskItem(@Valid @RequestBody TaskItem taskItem) throws URISyntaxException {
        log.debug("REST request to save TaskItem : {}", taskItem);
        if (taskItem.getId() != null) {
            throw new BadRequestAlertException("A new taskItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskItem result = taskItemService.save(taskItem);
        return ResponseEntity.created(new URI("/api/task-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-items} : Updates an existing taskItem.
     *
     * @param taskItem the taskItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskItem,
     * or with status {@code 400 (Bad Request)} if the taskItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-items")
    public ResponseEntity<TaskItem> updateTaskItem(@Valid @RequestBody TaskItem taskItem) throws URISyntaxException {
        log.debug("REST request to update TaskItem : {}", taskItem);
        if (taskItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskItem result = taskItemService.save(taskItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-items} : get all the taskItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskItems in body.
     */
    @GetMapping("/task-items")
    public ResponseEntity<List<TaskItem>> getAllTaskItems(TaskItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskItems by criteria: {}", criteria);
        Page<TaskItem> page = taskItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-items/count} : count all the taskItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-items/count")
    public ResponseEntity<Long> countTaskItems(TaskItemCriteria criteria) {
        log.debug("REST request to count TaskItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-items/:id} : get the "id" taskItem.
     *
     * @param id the id of the taskItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-items/{id}")
    public ResponseEntity<TaskItem> getTaskItem(@PathVariable Long id) {
        log.debug("REST request to get TaskItem : {}", id);
        Optional<TaskItem> taskItem = taskItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskItem);
    }

    /**
     * {@code DELETE  /task-items/:id} : delete the "id" taskItem.
     *
     * @param id the id of the taskItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-items/{id}")
    public ResponseEntity<Void> deleteTaskItem(@PathVariable Long id) {
        log.debug("REST request to delete TaskItem : {}", id);
        taskItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
