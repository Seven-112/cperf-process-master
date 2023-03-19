package com.mshz.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mshz.domain.TaskUser;
import com.mshz.service.TaskUserQueryService;
import com.mshz.service.TaskUserService;
import com.mshz.service.dto.TaskUserCriteria;
import com.mshz.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mshz.domain.TaskUser}.
 */
@RestController
@RequestMapping("/api")
public class TaskUserResource {

    private final Logger log = LoggerFactory.getLogger(TaskUserResource.class);

    private static final String ENTITY_NAME = "microprocessTaskUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskUserService taskUserService;

    private final TaskUserQueryService taskUserQueryService;

    public TaskUserResource(TaskUserService taskUserService, TaskUserQueryService taskUserQueryService) {
        this.taskUserService = taskUserService;
        this.taskUserQueryService = taskUserQueryService;
    }

    /**
     * {@code POST  /task-users} : Create a new taskUser.
     *
     * @param taskUser the taskUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskUser, or with status {@code 400 (Bad Request)} if the taskUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-users")
    public ResponseEntity<TaskUser> createTaskUser(@Valid @RequestBody TaskUser taskUser) throws URISyntaxException {
        log.debug("REST request to save TaskUser : {}", taskUser);
        if (taskUser.getId() != null) {
            throw new BadRequestAlertException("A new taskUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskUser result = taskUserService.save(taskUser);
        return ResponseEntity.created(new URI("/api/task-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-users} : Updates an existing taskUser.
     *
     * @param taskUser the taskUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskUser,
     * or with status {@code 400 (Bad Request)} if the taskUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-users")
    public ResponseEntity<TaskUser> updateTaskUser(@Valid @RequestBody TaskUser taskUser) throws URISyntaxException {
        log.debug("REST request to update TaskUser : {}", taskUser);
        if (taskUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskUser result = taskUserService.save(taskUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-users} : get all the taskUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskUsers in body.
     */
    @GetMapping("/task-users")
    public ResponseEntity<List<TaskUser>> getAllTaskUsers(TaskUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskUsers by criteria: {}", criteria);
        Page<TaskUser> page = taskUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-users/count} : count all the taskUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-users/count")
    public ResponseEntity<Long> countTaskUsers(TaskUserCriteria criteria) {
        log.debug("REST request to count TaskUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-users/:id} : get the "id" taskUser.
     *
     * @param id the id of the taskUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-users/{id}")
    public ResponseEntity<TaskUser> getTaskUser(@PathVariable Long id) {
        log.debug("REST request to get TaskUser : {}", id);
        Optional<TaskUser> taskUser = taskUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskUser);
    }

    /**
     * {@code DELETE  /task-users/:id} : delete the "id" taskUser.
     *
     * @param id the id of the taskUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-users/{id}")
    public ResponseEntity<Void> deleteTaskUser(@PathVariable Long id) {
        log.debug("REST request to delete TaskUser : {}", id);
        taskUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
