package com.mshz.web.rest;

import com.mshz.domain.KPI;
import com.mshz.service.KPIService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.KPICriteria;
import com.mshz.service.KPIQueryService;

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
 * REST controller for managing {@link com.mshz.domain.KPI}.
 */
@RestController
@RequestMapping("/api")
public class KPIResource {

    private final Logger log = LoggerFactory.getLogger(KPIResource.class);

    private static final String ENTITY_NAME = "microprocessKpi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KPIService kPIService;

    private final KPIQueryService kPIQueryService;

    public KPIResource(KPIService kPIService, KPIQueryService kPIQueryService) {
        this.kPIService = kPIService;
        this.kPIQueryService = kPIQueryService;
    }

    /**
     * {@code POST  /kpis} : Create a new kPI.
     *
     * @param kPI the kPI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kPI, or with status {@code 400 (Bad Request)} if the kPI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kpis")
    public ResponseEntity<KPI> createKPI(@RequestBody KPI kPI) throws URISyntaxException {
        log.debug("REST request to save KPI : {}", kPI);
        if (kPI.getId() != null) {
            throw new BadRequestAlertException("A new kPI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KPI result = kPIService.save(kPI);
        return ResponseEntity.created(new URI("/api/kpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kpis} : Updates an existing kPI.
     *
     * @param kPI the kPI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kPI,
     * or with status {@code 400 (Bad Request)} if the kPI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kPI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpis")
    public ResponseEntity<KPI> updateKPI(@RequestBody KPI kPI) throws URISyntaxException {
        log.debug("REST request to update KPI : {}", kPI);
        if (kPI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KPI result = kPIService.save(kPI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kPI.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kpis} : get all the kPIS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kPIS in body.
     */
    @GetMapping("/kpis")
    public ResponseEntity<List<KPI>> getAllKPIS(KPICriteria criteria, Pageable pageable) {
        log.debug("REST request to get KPIS by criteria: {}", criteria);
        Page<KPI> page = kPIQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kpis/count} : count all the kPIS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kpis/count")
    public ResponseEntity<Long> countKPIS(KPICriteria criteria) {
        log.debug("REST request to count KPIS by criteria: {}", criteria);
        return ResponseEntity.ok().body(kPIQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kpis/:id} : get the "id" kPI.
     *
     * @param id the id of the kPI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kPI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpis/{id}")
    public ResponseEntity<KPI> getKPI(@PathVariable Long id) {
        log.debug("REST request to get KPI : {}", id);
        Optional<KPI> kPI = kPIService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kPI);
    }

    /**
     * {@code DELETE  /kpis/:id} : delete the "id" kPI.
     *
     * @param id the id of the kPI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpis/{id}")
    public ResponseEntity<Void> deleteKPI(@PathVariable Long id) {
        log.debug("REST request to delete KPI : {}", id);
        kPIService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
