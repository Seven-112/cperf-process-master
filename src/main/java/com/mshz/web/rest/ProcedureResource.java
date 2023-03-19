package com.mshz.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mshz.domain.Procedure;
import com.mshz.service.ProcedureQueryService;
import com.mshz.service.ProcedureService;
import com.mshz.service.dto.ProcedureCriteria;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mshz.domain.Procedure}.
 */
@RestController
@RequestMapping("/api")
public class ProcedureResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureResource.class);

    private static final String ENTITY_NAME = "microprocessProcedure";
    private static final String PRIVILEGE_ENTITY_NAME = "Procedure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcedureService procedureService;

    private final ProcedureQueryService procedureQueryService;

    public ProcedureResource(ProcedureService procedureService, ProcedureQueryService procedureQueryService) {
        this.procedureService = procedureService;
        this.procedureQueryService = procedureQueryService;
    }

    /**
     * {@code POST  /procedures} : Create a new procedure.
     *
     * @param procedure the procedure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procedure, or with status {@code 400 (Bad Request)} if the procedure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procedures")
    @PreAuthorize("@customPermissionEvalutor.hasPermission(\""+PRIVILEGE_ENTITY_NAME+"\",'CREATE')")
    public ResponseEntity<Procedure> createProcedure(@Valid @RequestBody Procedure procedure) throws URISyntaxException {
        log.debug("REST request to save Procedure : {}", procedure);
        if (procedure.getId() != null) {
            throw new BadRequestAlertException("A new procedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Procedure result = procedureService.save(procedure);
        return ResponseEntity.created(new URI("/api/procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procedures} : Updates an existing procedure.
     *
     * @param procedure the procedure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedure,
     * or with status {@code 400 (Bad Request)} if the procedure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procedure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procedures")
    @PreAuthorize("@customPermissionEvalutor.hasPermission(\""+PRIVILEGE_ENTITY_NAME+"\",'UPDATE')")
    public ResponseEntity<Procedure> updateProcedure(@Valid @RequestBody Procedure procedure) throws URISyntaxException {
        log.debug("REST request to update Procedure : {}", procedure);
        if (procedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Procedure result = procedureService.save(procedure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procedure.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /procedures} : get all the procedures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procedures in body.
     */
    @GetMapping("/procedures")
    public ResponseEntity<List<Procedure>> getAllProcedures(ProcedureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Procedures by criteria: {}", criteria);
        Page<Procedure> page = procedureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /procedures/count} : count all the procedures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/procedures/count")
    public ResponseEntity<Long> countProcedures(ProcedureCriteria criteria) {
        log.debug("REST request to count Procedures by criteria: {}", criteria);
        return ResponseEntity.ok().body(procedureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /procedures/:id} : get the "id" procedure.
     *
     * @param id the id of the procedure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procedure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procedures/{id}")
    public ResponseEntity<Procedure> getProcedure(@PathVariable Long id) {
        log.debug("REST request to get Procedure : {}", id);
        Optional<Procedure> procedure = procedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedure);
    }

    /**
     * {@code DELETE  /procedures/:id} : delete the "id" procedure.
     *
     * @param id the id of the procedure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procedures/{id}")
    @PreAuthorize("@customPermissionEvalutor.hasPermission(\""+PRIVILEGE_ENTITY_NAME+"\",'DELETE')")
    public ResponseEntity<Void> deleteProcedure(@PathVariable Long id) {
        log.debug("REST request to delete Procedure : {}", id);
        procedureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
