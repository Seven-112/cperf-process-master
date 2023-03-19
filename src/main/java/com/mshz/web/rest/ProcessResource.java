package com.mshz.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mshz.domain.Process;
import com.mshz.model.ChronoUtil;
import com.mshz.model.PonctualTaskUtil;
import com.mshz.service.ProcessQueryService;
import com.mshz.service.ProcessService;
import com.mshz.service.dto.ProcessCriteria;
import com.mshz.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
 * REST controller for managing {@link com.mshz.domain.Process}.
 */
@RestController
@RequestMapping("/api")
public class ProcessResource {

    private final Logger log = LoggerFactory.getLogger(ProcessResource.class);

    private static final String ENTITY_NAME = "microprocessProcess";
    private static final String PRIVILEGE_ENTITY_NAME = "Process";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessService processService;

    private final ProcessQueryService processQueryService;

    public ProcessResource(ProcessService processService, ProcessQueryService processQueryService) {
        this.processService = processService;
        this.processQueryService = processQueryService;
    }

    /**
     * {@code POST  /processes} : Create a new process.
     *
     * @param process the process to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new process, or with status {@code 400 (Bad Request)} if the process has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processes")
    public ResponseEntity<Process> createProcess(@Valid @RequestBody Process process) throws URISyntaxException {
        log.debug("REST request to save Process : {}", process);
        if (process.getId() != null) {
            throw new BadRequestAlertException("A new process cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Process result = null;
        if(process.getModelId() !=null)
            result = processService.createInstance(process.getLabel(), process.getModelId(), process.getRunnableProcessId(), process.getQueryId());
        else
            result = processService.save(process);
        return ResponseEntity.created(new URI("/api/processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processes} : Updates an existing process.
     *
     * @param process the process to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated process,
     * or with status {@code 400 (Bad Request)} if the process is not valid,
     * or with status {@code 500 (Internal Server Error)} if the process couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processes")
    public ResponseEntity<Process> updateProcess(@Valid @RequestBody Process process) throws URISyntaxException {
        log.debug("REST request to update Process : {}", process);
        if (process.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Process result = processService.save(process);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, process.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processes} : get all the processes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processes in body.
     */
    @GetMapping("/processes")
    public ResponseEntity<List<Process>> getAllProcesses(ProcessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Processes by criteria: {}", criteria);
        Page<Process> page = processQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processes/count} : count all the processes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/processes/count")
    public ResponseEntity<Long> countProcesses(ProcessCriteria criteria) {
        log.debug("REST request to count Processes by criteria: {}", criteria);
        return ResponseEntity.ok().body(processQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /processes/:id} : get the "id" process.
     *
     * @param id the id of the process to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the process, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processes/{id}")
    public ResponseEntity<Process> getProcess(@PathVariable Long id) {
        log.debug("REST request to get Process : {}", id);
        Optional<Process> process = processService.findOne(id);
        return ResponseUtil.wrapOrNotFound(process);
    }

    /**
     * {@code DELETE  /processes/:id} : delete the "id" process.
     *
     * @param id the id of the process to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processes/{id}")
    @PreAuthorize("@customPermissionEvalutor.hasPermission(\""+PRIVILEGE_ENTITY_NAME+"\",'DELETE')")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long id) {
        log.debug("REST request to delete Process : {}", id);
        processService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * 
     * @param processId
     * @param procedureId
     * @return lsit of saved processes
     * @throws URISyntaxException
     */
    @GetMapping("/processes/{processId}/bind-or-detach-precedure")
    public ResponseEntity<List<Process>> baindOrDetachProcedure(
        @PathVariable Long processId, @RequestParam(name="procedureId", defaultValue = "0") Long procedureId
        ) throws URISyntaxException {
        log.debug("REST request to bind or detache procedure : {} to a process : {}", procedureId, processId);
        if(procedureId == 0)
            procedureId = null;
        List<Process> result = processService.bindOrDetachAProcedure(procedureId, processId);
        return ResponseEntity.ok().body(result);
    }
    
    /**
     * {@code GET  /processes/copy/{modelId} : Copy a process.
     *
     * @param label the label for a copy process.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new process copied, or with status {@code 400 (Bad Request)} if the process has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/processes/copy/{modelId}")
    public ResponseEntity<Process> copyProcess(@PathVariable Long modelId, @RequestParam("label") String label) throws URISyntaxException {
        log.debug("REST request to copy Process with model id : {}", modelId);
        Process result = processService.copyProcess(modelId, label);
        return ResponseEntity.created(new URI("/api/processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@Code GET /processes/playOrPause/:processId/:play}
     * @param processId : id of process
     * @param play : true if playing or false if else
     * @return
     */
    @GetMapping("/processes/playOrPause/{processId}/{play}")
    public ResponseEntity<Void> playOrPause(@PathVariable Long processId, @PathVariable Boolean play){
        log.debug("Play or pause tasks for process {} and play {} ", processId, play);
        processService.playOrPauseTasks(processId, play);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/processes/change/manualMode/{processId}/{manualMode}")
    public ResponseEntity<Void> chnageManualMode(@PathVariable Long processId, @PathVariable Boolean manualMode){
        log.debug("change process {} tasks manual mode {}", processId, manualMode);
        processService.UpdateAllTaskManualMode(processId, manualMode);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/processes/changeValidValue/{processId}")
    public ResponseEntity<Integer> toggleValidValue(@PathVariable Long processId, Boolean valid){
        log.debug("change process {} valid {}", processId, valid);
        Integer updated  = processService.toggleValidValue(processId, valid);
        return ResponseEntity.ok().body(updated);
    }

    @GetMapping("/processes/getChronoUtil/{processId}")
    public ResponseEntity<ChronoUtil> getChronoUtil(@PathVariable Long processId){
        log.debug("change process chrono util by id {}", processId);
        ChronoUtil result = processService.getChronoUtil(processId);
        if(result == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/processes/getProcessesChronoUtils")
    public ResponseEntity<List<ChronoUtil>> getProcessesChronoUtils(List<Long> pIds){
        List<ChronoUtil> result = processService.getProcessesChronoUtils(pIds);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/processes/updatePonctualTaskExecTime")
    public ResponseEntity<Void> updatePonctualTaskExecTime(@Valid @RequestBody PonctualTaskUtil ponctualTaskUtil){
        log.debug("update ponctual task execution time: {}", ponctualTaskUtil);
        processService.updatePonctualTaskExecTime(ponctualTaskUtil);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/processes/list")
    public ResponseEntity<Void> deleteList(@RequestParam("ids") List<Long> ids){
        log.debug("delete process id list of ids: {}", ids);
        processService.deleteAllByIds(ids);
        return ResponseEntity.noContent().build();
    }

}
