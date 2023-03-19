package com.mshz.web.rest;

import com.mshz.domain.EdgeInfo;
import com.mshz.service.EdgeInfoService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.EdgeInfoCriteria;
import com.mshz.service.EdgeInfoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.domain.EdgeInfo}.
 */
@RestController
@RequestMapping("/api")
public class EdgeInfoResource {

    private final Logger log = LoggerFactory.getLogger(EdgeInfoResource.class);

    private static final String ENTITY_NAME = "microprocessEdgeInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EdgeInfoService edgeInfoService;

    private final EdgeInfoQueryService edgeInfoQueryService;

    public EdgeInfoResource(EdgeInfoService edgeInfoService, EdgeInfoQueryService edgeInfoQueryService) {
        this.edgeInfoService = edgeInfoService;
        this.edgeInfoQueryService = edgeInfoQueryService;
    }

    /**
     * {@code POST  /edge-infos} : Create a new edgeInfo.
     *
     * @param edgeInfo the edgeInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new edgeInfo, or with status {@code 400 (Bad Request)} if the edgeInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/edge-infos")
    public ResponseEntity<EdgeInfo> createEdgeInfo(@RequestBody EdgeInfo edgeInfo) throws URISyntaxException {
        log.debug("REST request to save EdgeInfo : {}", edgeInfo);
        if (edgeInfo.getId() != null) {
            throw new BadRequestAlertException("A new edgeInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EdgeInfo result = edgeInfoService.save(edgeInfo);
        return ResponseEntity.created(new URI("/api/edge-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /edge-infos} : Updates an existing edgeInfo.
     *
     * @param edgeInfo the edgeInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated edgeInfo,
     * or with status {@code 400 (Bad Request)} if the edgeInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the edgeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/edge-infos")
    public ResponseEntity<EdgeInfo> updateEdgeInfo(@RequestBody EdgeInfo edgeInfo) throws URISyntaxException {
        log.debug("REST request to update EdgeInfo : {}", edgeInfo);
        if (edgeInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EdgeInfo result = edgeInfoService.save(edgeInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, edgeInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /edge-infos} : get all the edgeInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of edgeInfos in body.
     */
    @GetMapping("/edge-infos")
    public ResponseEntity<List<EdgeInfo>> getAllEdgeInfos(EdgeInfoCriteria criteria) {
        log.debug("REST request to get EdgeInfos by criteria: {}", criteria);
        List<EdgeInfo> entityList = edgeInfoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /edge-infos/count} : count all the edgeInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/edge-infos/count")
    public ResponseEntity<Long> countEdgeInfos(EdgeInfoCriteria criteria) {
        log.debug("REST request to count EdgeInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(edgeInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /edge-infos/:id} : get the "id" edgeInfo.
     *
     * @param id the id of the edgeInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the edgeInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/edge-infos/{id}")
    public ResponseEntity<EdgeInfo> getEdgeInfo(@PathVariable Long id) {
        log.debug("REST request to get EdgeInfo : {}", id);
        Optional<EdgeInfo> edgeInfo = edgeInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(edgeInfo);
    }

    /**
     * {@code DELETE  /edge-infos/:id} : delete the "id" edgeInfo.
     *
     * @param id the id of the edgeInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/edge-infos/{id}")
    public ResponseEntity<Void> deleteEdgeInfo(@PathVariable Long id) {
        log.debug("REST request to delete EdgeInfo : {}", id);
        edgeInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /edge-infos} : get all the edgeInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of edgeInfos in body.
     */
    @GetMapping("/edge-infos/bySourceOrTarget")
    public ResponseEntity<List<EdgeInfo>> getAllEdgeInfosBySourceOrTarget(
        @RequestParam("source") String source, @RequestParam("target") String target) {
        log.debug("REST request to get EdgeInfos by source or target");
        List<EdgeInfo> entityList = edgeInfoService.findBySourceOrTarget(source, target);
        return ResponseEntity.ok().body(entityList);
    }

    @DeleteMapping("/edge-infos/bySourceOrTarget")
    public ResponseEntity<Void> deleteEdgeInfo(String source, String target) {
        log.debug("REST request to delete by source {} or target {}", source, target);
        edgeInfoService.deleteBySourceOrTarget(source, target);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, source+' '+target)).build();
    }
}
