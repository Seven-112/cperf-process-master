package com.mshz.web.rest;

import com.mshz.domain.CondNode;
import com.mshz.service.CondNodeService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.CondNodeCriteria;
import com.mshz.service.CondNodeQueryService;

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
 * REST controller for managing {@link com.mshz.domain.CondNode}.
 */
@RestController
@RequestMapping("/api")
public class CondNodeResource {

    private final Logger log = LoggerFactory.getLogger(CondNodeResource.class);

    private static final String ENTITY_NAME = "microprocessCondNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CondNodeService condNodeService;

    private final CondNodeQueryService condNodeQueryService;

    public CondNodeResource(CondNodeService condNodeService, CondNodeQueryService condNodeQueryService) {
        this.condNodeService = condNodeService;
        this.condNodeQueryService = condNodeQueryService;
    }

    /**
     * {@code POST  /cond-nodes} : Create a new condNode.
     *
     * @param condNode the condNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new condNode, or with status {@code 400 (Bad Request)} if the condNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cond-nodes")
    public ResponseEntity<CondNode> createCondNode(@RequestBody CondNode condNode) throws URISyntaxException {
        log.debug("REST request to save CondNode : {}", condNode);
        if (condNode.getId() != null) {
            throw new BadRequestAlertException("A new condNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CondNode result = condNodeService.save(condNode);
        return ResponseEntity.created(new URI("/api/cond-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cond-nodes} : Updates an existing condNode.
     *
     * @param condNode the condNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated condNode,
     * or with status {@code 400 (Bad Request)} if the condNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the condNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cond-nodes")
    public ResponseEntity<CondNode> updateCondNode(@RequestBody CondNode condNode) throws URISyntaxException {
        log.debug("REST request to update CondNode : {}", condNode);
        if (condNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CondNode result = condNodeService.save(condNode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, condNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cond-nodes} : get all the condNodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of condNodes in body.
     */
    @GetMapping("/cond-nodes")
    public ResponseEntity<List<CondNode>> getAllCondNodes(CondNodeCriteria criteria) {
        log.debug("REST request to get CondNodes by criteria: {}", criteria);
        List<CondNode> entityList = condNodeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cond-nodes/count} : count all the condNodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cond-nodes/count")
    public ResponseEntity<Long> countCondNodes(CondNodeCriteria criteria) {
        log.debug("REST request to count CondNodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(condNodeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cond-nodes/:id} : get the "id" condNode.
     *
     * @param id the id of the condNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the condNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cond-nodes/{id}")
    public ResponseEntity<CondNode> getCondNode(@PathVariable Long id) {
        log.debug("REST request to get CondNode : {}", id);
        Optional<CondNode> condNode = condNodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(condNode);
    }

    /**
     * {@code DELETE  /cond-nodes/:id} : delete the "id" condNode.
     *
     * @param id the id of the condNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cond-nodes/{id}")
    public ResponseEntity<Void> deleteCondNode(@PathVariable Long id) {
        log.debug("REST request to delete CondNode : {}", id);
        condNodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
