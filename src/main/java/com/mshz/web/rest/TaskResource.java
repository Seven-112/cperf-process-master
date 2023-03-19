package com.mshz.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mshz.domain.Task;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.domain.enumeration.TaskUserRole;
import com.mshz.domain.projection.ITaskProcess;
import com.mshz.model.ChronoUtil;
import com.mshz.service.TaskQueryService;
import com.mshz.service.TaskService;
import com.mshz.service.dto.TaskCriteria;
import com.mshz.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mshz.domain.Task}.
 */
@RestController
@RequestMapping("/api")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "microprocessTask";
    
    private static final String PRIVILEGE_ENTITY_NAME = "Task";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskService taskService;

    private final TaskQueryService taskQueryService;

    public TaskResource(TaskService taskService, TaskQueryService taskQueryService) {
        this.taskService = taskService;
        this.taskQueryService = taskQueryService;
    }

    /**
     * {@code POST  /tasks} : Create a new task.
     *
     * @param task the task to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new task, or with status {@code 400 (Bad Request)} if the task has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks")
    @PreAuthorize("@customPermissionEvalutor.hasPermission(\""+PRIVILEGE_ENTITY_NAME+"\",'CREATE') || @customPermissionEvalutor.hasPermission('Process','CREATE')")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to save Task : {}", task);
        if (task.getId() != null) {
            throw new BadRequestAlertException("A new task cannot already have an ID", ENTITY_NAME, "idexists");
        }
        task.setManualMode(false);
        task.setValid(true);
        Task result = taskService.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks} : Updates an existing task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to update Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.save(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(TaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tasks by criteria: {}", criteria);
        Page<Task> page = taskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        // sorting by priority
        List<Task> sortedTasks = taskService.sortTasks(page.getContent());
        return ResponseEntity.ok().headers(headers).body(sortedTasks);
    }

    /**
     * {@code GET  /tasks/count} : count all the tasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tasks/count")
    public ResponseEntity<Long> countTasks(TaskCriteria criteria) {
        log.debug("REST request to count Tasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tasks/:id} : get the "id" task.
     *
     * @param id the id of the task to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.debug("REST request to get Task : {}", id);
        Optional<Task> task = taskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(task);
    }

    /**
     * {@code DELETE  /tasks/:id} : delete the "id" task.
     *
     * @param id the id of the task to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("@customPermissionEvalutor.hasPermission(\""+PRIVILEGE_ENTITY_NAME+"\",'DELETE') || @customPermissionEvalutor.hasPermission('Process','DELETE')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.debug("REST request to delete Task : {}", id);
        taskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    

    /**
     * 
     * @param empId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/tasks/by-employee/{id}")
    public ResponseEntity<Page<ITaskProcess>> getEmployeeTasksWithProcessesPage(@PathVariable Long id,
                @RequestParam("page") int page,@RequestParam("size") int size){
             log.debug("REST request to find employee tasks page by employee id : {}", id);
    Page<ITaskProcess> result = taskService.findByEmployeeId(id, PageRequest.of(page, size));
    return ResponseEntity.ok().body(result);
    }

    /**
     * 
     * @param id
     * @param status
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/tasks/by-employee-and-status/{id}")
    public ResponseEntity<Page<ITaskProcess>> getEmployeeTasksWithProcessesPageByStatus(@PathVariable Long id,
                @RequestParam("status") TaskStatus status, @RequestParam("page") int page,@RequestParam("size") int size){
             log.debug("REST request to find employee tasks page by employee id and task status: {}", id);
    Page<ITaskProcess> result = taskService.findByEmployeeIdAndTaskStatus(id, status,PageRequest.of(page, size));
    return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/findByUserIdAndRoleAndTaskStatus")
    public ResponseEntity<Page<ITaskProcess>> getUserTasksWithProcessesPageByUserIdAndRoleAndTaskStatus(
            Long userId, TaskStatus status, TaskUserRole role, Pageable pageable){
             log.debug("REST request to find usser tasks page by employee id and task status: {}", userId);
    Page<ITaskProcess> result = taskService.findByUserIdAndRoleAndTaskStatus(userId, status, role, pageable);
    return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/findByUserIdAndRoleAndTaskStatusIn")
    public ResponseEntity<Page<ITaskProcess>> getUserTasksWithProcessesPageByUserIdAndRoleAndTaskStatus(
             @RequestParam("userId") Long userId, @RequestParam("status") List<TaskStatus> status, 
             @RequestParam("role") TaskUserRole role, Pageable pageable){
             log.debug("REST request to find usser tasks page by employee id and task status: {}", userId);
    Page<ITaskProcess> result = taskService.findByUserIdAndRoleAndTaskStatusIn(userId, status, role, pageable);
    return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/by-employee-created-between/{id}")
    public ResponseEntity<List<Task>> getEmployeeTasksCreatedBetween(@PathVariable Long id,
                @RequestParam("startAt") Instant startAt, @RequestParam("endAt") Instant endAt){
        log.debug("REST request to find employee tasks filtered by process created at {} and {}: {}", id, startAt, endAt);
        List<Task> result = taskService.findEmployeeTasksBetween(id, startAt, endAt);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/startupassociatable/{taskId}/{processId}")
    public ResponseEntity<List<Task>> getTaskStartupAssiatable(@PathVariable Long taskId,
    		@PathVariable Long processId,@RequestParam("page") int page, @RequestParam("size") int size){
        log.debug("REST request to find associable startuble tasks to task by id for process {} and {}: {}", taskId, processId);
        Page<Task> result = taskService.findTasksToStartupAssociable(taskId, processId, PageRequest.of(page, size));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }
    
    /**
     * {@code GET /tasks/startups/:id} 
     * @param id
     * @return list of task finded
     */
    @GetMapping("/tasks/startups/{id}")
    public ResponseEntity<List<Task>> getStartupTasks(@PathVariable Long id){
        log.debug("REST request to find startup task for task {}", id);
        List<Task> tasks = taskService.getStratupTasks(id);
        return ResponseEntity.ok().body(tasks);
    }

    /**
     * {@code GET /tasks/children/:id} 
     * @param id
     * @return list of task finded
     */
    @GetMapping("/tasks/children/{id}")
    public ResponseEntity<List<Task>> getChildrenTasks(@PathVariable Long id){
        log.debug("REST request to find children task for task {}", id);
        List<Task> tasks = taskService.getChildrenTasks(id);
        return ResponseEntity.ok().body(tasks);
    }

    /**
     * {@code PUT  /tasks/start} : start an existing task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/start")
    public ResponseEntity<Task> startTask(@Valid @RequestBody Task task, 
        @RequestParam(name="triggerTaskId", required=false) Long triggerTaskId) throws URISyntaxException {
        log.debug("REST request to start Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.startTask(task, false);
        taskService.asyncChangeTaskStatusById(triggerTaskId, TaskStatus.ON_PAUSE);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks/execute} : start an existing task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/execute")
    public ResponseEntity<Task> executeTask(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to execute Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.executeTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks/finish} : start an finish task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/finish")
    public ResponseEntity<Task> finish(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to finish Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.finishTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /tasks/pause} : pause an existing task.
     *
     * @param task the task to pause.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/pause")
    public ResponseEntity<Task> pause(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to pause Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.pauseTask(task, false);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks/play} : paly an existing task.
     *
     * @param task the task to play.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/play")
    public ResponseEntity<Task> play(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to play Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.playTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks/cancel} : cancel an existing task.
     *
     * @param task the task to cancel.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/cancel")
    public ResponseEntity<Task> cancel(@Valid @RequestBody Task task, 
            @RequestParam("cancelProcess") Boolean cancelProcess) throws URISyntaxException {
        log.debug("REST request to cancel Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.cancelTask(task, cancelProcess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks/submit} : submit an existing task.
     *
     * @param task the task to submit.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/submit")
    public ResponseEntity<Task> submit(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to submit Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.submitTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /tasks/reset} : submit an existing task.
     *
     * @param task the task to submit.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks/reset")
    public ResponseEntity<Task> reset(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to reset Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Task result = taskService.reset(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    @GetMapping("/tasks/getCheckableTasksByUserIdAndStatus/{userId}")
    public ResponseEntity<Page<ITaskProcess>> getCheckableTasksByUserIdAndStatus(
                @PathVariable Long userId,@RequestParam(name = "status", required = false) TaskStatus status, Pageable pageable){
             log.debug("REST request to find checkable tasks page by user id {} and task status: {}", userId, status);
    Page<ITaskProcess> result = taskService.findCheckableTasksByUserIdAndTaskStatus(userId, status,pageable);
    return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/getByProcessId/{processId}")
    public ResponseEntity<List<Task>> getAllTasksByProcessIdAnValid(
        @PathVariable Long processId, @RequestParam(name="valid", defaultValue = "true") Boolean valid) {
        log.debug("REST request to get all Tasks by processId {} and valid: {}", processId, valid);
        List<Task> result = taskService.getByProcessIdAndValid(processId, valid);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/getChronoUtil/{taskId}")
    public ResponseEntity<ChronoUtil> getChronoUtil(@PathVariable Long taskId) {
        log.debug("REST request to get TasK chrono Util by id : {}", taskId);
        ChronoUtil result = taskService.getTaskChronoUtil(taskId);
        if(result == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /tasks/isChecked/:id} 
     * @param taskId
     * @return boolean in body with true if all required task items checked or false
     */
    @GetMapping("/tasks/isChecked/{taskId}")
    public ResponseEntity<Boolean> allRequiredTaskItemsIsChecked(@PathVariable Long taskId){
        log.debug("REST request to check if all required task items checked by task id : {}", taskId);
        Boolean checked = taskService.allRequiredTaskItemsIsChecked(taskId);
        return ResponseEntity.ok().body(checked);
    }


    /**
     * {@code GET  /tasks/:queryId} : get the "id" query instance.
     *
     * @param queryId the id of the query instance who created process instance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the task.
     */
    @GetMapping("/tasks/getPonctualByQueryId/{queryId}")
    public ResponseEntity<Task> getPonctualByQueryInstanceId(@PathVariable Long queryId) {
        log.debug("REST request to getPonctual task by query : {}", queryId);
        Task  result = taskService.getPonctualByQueryId(queryId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/getSortedByPriority/")
    public ResponseEntity<List<Task>> getSortedByPriority(
         @RequestParam(name="ids") List<String> ids) {
        log.debug("REST request to get all Tasks by ids {} sorted by prioriy", ids);
        List<Task> result = taskService.getSortedByPriority(ids);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tasks/{id}/preview")
    public ResponseEntity<List<Task>> getPreview(@PathVariable Long id) {
        log.debug("REST request to get preview task by current task id : {}", id);
        List<Task> result = taskService.getPreview(id);
        return ResponseEntity.ok().body(result);
    }


}
