package com.mshz.web.rest;

import com.mshz.domain.ItemCheckJustification;
import com.mshz.service.ItemCheckJustificationService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.ItemCheckJustificationCriteria;
import com.mshz.service.ItemCheckJustificationQueryService;

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
 * REST controller for managing {@link com.mshz.domain.ItemCheckJustification}.
 */
@RestController
@RequestMapping("/api")
public class ItemCheckJustificationResource {

    private final Logger log = LoggerFactory.getLogger(ItemCheckJustificationResource.class);

    private static final String ENTITY_NAME = "microprocessItemCheckJustification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemCheckJustificationService itemCheckJustificationService;

    private final ItemCheckJustificationQueryService itemCheckJustificationQueryService;

    public ItemCheckJustificationResource(ItemCheckJustificationService itemCheckJustificationService, ItemCheckJustificationQueryService itemCheckJustificationQueryService) {
        this.itemCheckJustificationService = itemCheckJustificationService;
        this.itemCheckJustificationQueryService = itemCheckJustificationQueryService;
    }

    /**
     * {@code POST  /item-check-justifications} : Create a new itemCheckJustification.
     *
     * @param itemCheckJustification the itemCheckJustification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemCheckJustification, or with status {@code 400 (Bad Request)} if the itemCheckJustification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-check-justifications")
    public ResponseEntity<ItemCheckJustification> createItemCheckJustification(@Valid @RequestBody ItemCheckJustification itemCheckJustification) throws URISyntaxException {
        log.debug("REST request to save ItemCheckJustification : {}", itemCheckJustification);
        if (itemCheckJustification.getId() != null) {
            throw new BadRequestAlertException("A new itemCheckJustification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCheckJustification result = itemCheckJustificationService.save(itemCheckJustification);
        return ResponseEntity.created(new URI("/api/item-check-justifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-check-justifications} : Updates an existing itemCheckJustification.
     *
     * @param itemCheckJustification the itemCheckJustification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCheckJustification,
     * or with status {@code 400 (Bad Request)} if the itemCheckJustification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemCheckJustification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-check-justifications")
    public ResponseEntity<ItemCheckJustification> updateItemCheckJustification(@Valid @RequestBody ItemCheckJustification itemCheckJustification) throws URISyntaxException {
        log.debug("REST request to update ItemCheckJustification : {}", itemCheckJustification);
        if (itemCheckJustification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemCheckJustification result = itemCheckJustificationService.save(itemCheckJustification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCheckJustification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-check-justifications} : get all the itemCheckJustifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemCheckJustifications in body.
     */
    @GetMapping("/item-check-justifications")
    public ResponseEntity<List<ItemCheckJustification>> getAllItemCheckJustifications(ItemCheckJustificationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemCheckJustifications by criteria: {}", criteria);
        Page<ItemCheckJustification> page = itemCheckJustificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-check-justifications/count} : count all the itemCheckJustifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-check-justifications/count")
    public ResponseEntity<Long> countItemCheckJustifications(ItemCheckJustificationCriteria criteria) {
        log.debug("REST request to count ItemCheckJustifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemCheckJustificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-check-justifications/:id} : get the "id" itemCheckJustification.
     *
     * @param id the id of the itemCheckJustification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemCheckJustification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-check-justifications/{id}")
    public ResponseEntity<ItemCheckJustification> getItemCheckJustification(@PathVariable Long id) {
        log.debug("REST request to get ItemCheckJustification : {}", id);
        Optional<ItemCheckJustification> itemCheckJustification = itemCheckJustificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCheckJustification);
    }

    /**
     * {@code DELETE  /item-check-justifications/:id} : delete the "id" itemCheckJustification.
     *
     * @param id the id of the itemCheckJustification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-check-justifications/{id}")
    public ResponseEntity<Void> deleteItemCheckJustification(@PathVariable Long id) {
        log.debug("REST request to delete ItemCheckJustification : {}", id);
        itemCheckJustificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
