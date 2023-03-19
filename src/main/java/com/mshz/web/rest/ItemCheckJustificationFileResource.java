package com.mshz.web.rest;

import com.mshz.domain.ItemCheckJustificationFile;
import com.mshz.service.ItemCheckJustificationFileService;
import com.mshz.web.rest.errors.BadRequestAlertException;
import com.mshz.service.dto.ItemCheckJustificationFileCriteria;
import com.mshz.service.ItemCheckJustificationFileQueryService;

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
 * REST controller for managing {@link com.mshz.domain.ItemCheckJustificationFile}.
 */
@RestController
@RequestMapping("/api")
public class ItemCheckJustificationFileResource {

    private final Logger log = LoggerFactory.getLogger(ItemCheckJustificationFileResource.class);

    private static final String ENTITY_NAME = "microprocessItemCheckJustificationFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemCheckJustificationFileService itemCheckJustificationFileService;

    private final ItemCheckJustificationFileQueryService itemCheckJustificationFileQueryService;

    public ItemCheckJustificationFileResource(ItemCheckJustificationFileService itemCheckJustificationFileService, ItemCheckJustificationFileQueryService itemCheckJustificationFileQueryService) {
        this.itemCheckJustificationFileService = itemCheckJustificationFileService;
        this.itemCheckJustificationFileQueryService = itemCheckJustificationFileQueryService;
    }

    /**
     * {@code POST  /item-check-justification-files} : Create a new itemCheckJustificationFile.
     *
     * @param itemCheckJustificationFile the itemCheckJustificationFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemCheckJustificationFile, or with status {@code 400 (Bad Request)} if the itemCheckJustificationFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-check-justification-files")
    public ResponseEntity<ItemCheckJustificationFile> createItemCheckJustificationFile(@Valid @RequestBody ItemCheckJustificationFile itemCheckJustificationFile) throws URISyntaxException {
        log.debug("REST request to save ItemCheckJustificationFile : {}", itemCheckJustificationFile);
        if (itemCheckJustificationFile.getId() != null) {
            throw new BadRequestAlertException("A new itemCheckJustificationFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCheckJustificationFile result = itemCheckJustificationFileService.save(itemCheckJustificationFile);
        return ResponseEntity.created(new URI("/api/item-check-justification-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-check-justification-files} : Updates an existing itemCheckJustificationFile.
     *
     * @param itemCheckJustificationFile the itemCheckJustificationFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCheckJustificationFile,
     * or with status {@code 400 (Bad Request)} if the itemCheckJustificationFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemCheckJustificationFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-check-justification-files")
    public ResponseEntity<ItemCheckJustificationFile> updateItemCheckJustificationFile(@Valid @RequestBody ItemCheckJustificationFile itemCheckJustificationFile) throws URISyntaxException {
        log.debug("REST request to update ItemCheckJustificationFile : {}", itemCheckJustificationFile);
        if (itemCheckJustificationFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemCheckJustificationFile result = itemCheckJustificationFileService.save(itemCheckJustificationFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCheckJustificationFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-check-justification-files} : get all the itemCheckJustificationFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemCheckJustificationFiles in body.
     */
    @GetMapping("/item-check-justification-files")
    public ResponseEntity<List<ItemCheckJustificationFile>> getAllItemCheckJustificationFiles(ItemCheckJustificationFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemCheckJustificationFiles by criteria: {}", criteria);
        Page<ItemCheckJustificationFile> page = itemCheckJustificationFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-check-justification-files/count} : count all the itemCheckJustificationFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-check-justification-files/count")
    public ResponseEntity<Long> countItemCheckJustificationFiles(ItemCheckJustificationFileCriteria criteria) {
        log.debug("REST request to count ItemCheckJustificationFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemCheckJustificationFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-check-justification-files/:id} : get the "id" itemCheckJustificationFile.
     *
     * @param id the id of the itemCheckJustificationFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemCheckJustificationFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-check-justification-files/{id}")
    public ResponseEntity<ItemCheckJustificationFile> getItemCheckJustificationFile(@PathVariable Long id) {
        log.debug("REST request to get ItemCheckJustificationFile : {}", id);
        Optional<ItemCheckJustificationFile> itemCheckJustificationFile = itemCheckJustificationFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCheckJustificationFile);
    }

    /**
     * {@code DELETE  /item-check-justification-files/:id} : delete the "id" itemCheckJustificationFile.
     *
     * @param id the id of the itemCheckJustificationFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-check-justification-files/{id}")
    public ResponseEntity<Void> deleteItemCheckJustificationFile(@PathVariable Long id) {
        log.debug("REST request to delete ItemCheckJustificationFile : {}", id);
        itemCheckJustificationFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
