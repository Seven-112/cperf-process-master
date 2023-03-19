package com.mshz.web.rest;

import com.mshz.domain.ProcessCategory;
import com.mshz.service.ProcessCategoryService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.ProcessCategoryCriteria;
import com.mshz.service.ProcessCategoryQueryService;

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
 * REST controller for managing {@link com.mshz.domain.ProcessCategory}.
 */
@RestController
@RequestMapping("/api")
public class ProcessCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProcessCategoryResource.class);

    private static final String ENTITY_NAME = "microprocessProcessCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessCategoryService processCategoryService;

    private final ProcessCategoryQueryService processCategoryQueryService;

    public ProcessCategoryResource(ProcessCategoryService processCategoryService, ProcessCategoryQueryService processCategoryQueryService) {
        this.processCategoryService = processCategoryService;
        this.processCategoryQueryService = processCategoryQueryService;
    }

    /**
     * {@code POST  /process-categories} : Create a new processCategory.
     *
     * @param processCategory the processCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processCategory, or with status {@code 400 (Bad Request)} if the processCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-categories")
    public ResponseEntity<ProcessCategory> createProcessCategory(@Valid @RequestBody ProcessCategory processCategory) throws URISyntaxException {
        log.debug("REST request to save ProcessCategory : {}", processCategory);
        if (processCategory.getId() != null) {
            throw new BadRequestAlertException("A new processCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessCategory result = processCategoryService.save(processCategory);
        return ResponseEntity.created(new URI("/api/process-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-categories} : Updates an existing processCategory.
     *
     * @param processCategory the processCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processCategory,
     * or with status {@code 400 (Bad Request)} if the processCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-categories")
    public ResponseEntity<ProcessCategory> updateProcessCategory(@Valid @RequestBody ProcessCategory processCategory) throws URISyntaxException {
        log.debug("REST request to update ProcessCategory : {}", processCategory);
        if (processCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessCategory result = processCategoryService.save(processCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-categories} : get all the processCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processCategories in body.
     */
    @GetMapping("/process-categories")
    public ResponseEntity<List<ProcessCategory>> getAllProcessCategories(ProcessCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProcessCategories by criteria: {}", criteria);
        Page<ProcessCategory> page = processCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-categories/all} : get all the processCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processCategories in body.
     */
    @GetMapping("/process-categories/all")
    public ResponseEntity<List<ProcessCategory>> getAllProcessCategories() {
        log.debug("REST request to get all ProcessCategories");
        List<ProcessCategory> result = processCategoryService.findAll();
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /process-categories/count} : count all the processCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/process-categories/count")
    public ResponseEntity<Long> countProcessCategories(ProcessCategoryCriteria criteria) {
        log.debug("REST request to count ProcessCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(processCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /process-categories/:id} : get the "id" processCategory.
     *
     * @param id the id of the processCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-categories/{id}")
    public ResponseEntity<ProcessCategory> getProcessCategory(@PathVariable Long id) {
        log.debug("REST request to get ProcessCategory : {}", id);
        Optional<ProcessCategory> processCategory = processCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processCategory);
    }

    /**
     * {@code DELETE  /process-categories/:id} : delete the "id" processCategory.
     *
     * @param id the id of the processCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-categories/{id}")
    public ResponseEntity<Void> deleteProcessCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProcessCategory : {}", id);
        processCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
