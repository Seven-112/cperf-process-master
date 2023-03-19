package com.mshz.web.rest;

import com.mshz.domain.ProcessCategoryUser;
import com.mshz.service.ProcessCategoryUserService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.ProcessCategoryUserCriteria;
import com.mshz.service.ProcessCategoryUserQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.domain.ProcessCategoryUser}.
 */
@RestController
@RequestMapping("/api")
public class ProcessCategoryUserResource {

    private final Logger log = LoggerFactory.getLogger(ProcessCategoryUserResource.class);

    private static final String ENTITY_NAME = "microprocessProcessCategoryUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessCategoryUserService processCategoryUserService;

    private final ProcessCategoryUserQueryService processCategoryUserQueryService;

    public ProcessCategoryUserResource(ProcessCategoryUserService processCategoryUserService, ProcessCategoryUserQueryService processCategoryUserQueryService) {
        this.processCategoryUserService = processCategoryUserService;
        this.processCategoryUserQueryService = processCategoryUserQueryService;
    }

    /**
     * {@code POST  /process-category-users} : Create a new processCategoryUser.
     *
     * @param processCategoryUser the processCategoryUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processCategoryUser, or with status {@code 400 (Bad Request)} if the processCategoryUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-category-users")
    public ResponseEntity<ProcessCategoryUser> createProcessCategoryUser(@RequestBody ProcessCategoryUser processCategoryUser) throws URISyntaxException {
        log.debug("REST request to save ProcessCategoryUser : {}", processCategoryUser);
        if (processCategoryUser.getId() != null) {
            throw new BadRequestAlertException("A new processCategoryUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessCategoryUser result = processCategoryUserService.save(processCategoryUser);
        return ResponseEntity.created(new URI("/api/process-category-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-category-users} : Updates an existing processCategoryUser.
     *
     * @param processCategoryUser the processCategoryUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processCategoryUser,
     * or with status {@code 400 (Bad Request)} if the processCategoryUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processCategoryUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-category-users")
    public ResponseEntity<ProcessCategoryUser> updateProcessCategoryUser(@RequestBody ProcessCategoryUser processCategoryUser) throws URISyntaxException {
        log.debug("REST request to update ProcessCategoryUser : {}", processCategoryUser);
        if (processCategoryUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessCategoryUser result = processCategoryUserService.save(processCategoryUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processCategoryUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-category-users} : get all the processCategoryUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processCategoryUsers in body.
     */
    @GetMapping("/process-category-users")
    public ResponseEntity<List<ProcessCategoryUser>> getAllProcessCategoryUsers(ProcessCategoryUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProcessCategoryUsers by criteria: {}", criteria);
        Page<ProcessCategoryUser> page = processCategoryUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-category-users/count} : count all the processCategoryUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/process-category-users/count")
    public ResponseEntity<Long> countProcessCategoryUsers(ProcessCategoryUserCriteria criteria) {
        log.debug("REST request to count ProcessCategoryUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(processCategoryUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /process-category-users/:id} : get the "id" processCategoryUser.
     *
     * @param id the id of the processCategoryUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processCategoryUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-category-users/{id}")
    public ResponseEntity<ProcessCategoryUser> getProcessCategoryUser(@PathVariable Long id) {
        log.debug("REST request to get ProcessCategoryUser : {}", id);
        Optional<ProcessCategoryUser> processCategoryUser = processCategoryUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processCategoryUser);
    }

    /**
     * {@code DELETE  /process-category-users/:id} : delete the "id" processCategoryUser.
     *
     * @param id the id of the processCategoryUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-category-users/{id}")
    public ResponseEntity<Void> deleteProcessCategoryUser(@PathVariable Long id) {
        log.debug("REST request to delete ProcessCategoryUser : {}", id);
        processCategoryUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/process-category-users/deleteByUserAndCategoryAndProcess")
    public ResponseEntity<Void> deleteProcessCategoryUserIdAndCategoryId(Long userId, Long categoryId, Long processId) {
        log.debug("REST request to delete ProcessCategoryUser by userId {} and categoryId {}", userId, categoryId);
        processCategoryUserService.deleteByUserIdAndCategoryIdAndProcessId(userId, categoryId, processId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, userId.toString())).build();
    }
}
