package com.mshz.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.domain.ItemCheckJustification;
import com.mshz.repository.ItemCheckJustificationRepository;

/**
 * Service Implementation for managing {@link ItemCheckJustification}.
 */
@Service
@Transactional
public class ItemCheckJustificationService {

    private final Logger log = LoggerFactory.getLogger(ItemCheckJustificationService.class);

    private final ItemCheckJustificationRepository itemCheckJustificationRepository;
    
    private final ItemCheckJustificationFileService checkJustificationFileService;

    public ItemCheckJustificationService(ItemCheckJustificationRepository itemCheckJustificationRepository,
            ItemCheckJustificationFileService checkJustificationFileService) {
        this.itemCheckJustificationRepository = itemCheckJustificationRepository;
        this.checkJustificationFileService = checkJustificationFileService;
    }

    /**
     * Save a itemCheckJustification.
     *
     * @param itemCheckJustification the entity to save.
     * @return the persisted entity.
     */
    public ItemCheckJustification save(ItemCheckJustification itemCheckJustification) {
        log.debug("Request to save ItemCheckJustification : {}", itemCheckJustification);
        if(itemCheckJustification != null){
            if(itemCheckJustification.isChecked() == null)
                itemCheckJustification.setChecked(Boolean.FALSE);
            if(itemCheckJustification.getDate() == null)
                itemCheckJustification.setDate(Instant.now());
            
        }
        return itemCheckJustificationRepository.save(itemCheckJustification);
    }

    /**
     * Get all the itemCheckJustifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCheckJustification> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCheckJustifications");
        return itemCheckJustificationRepository.findAll(pageable);
    }


    /**
     * Get one itemCheckJustification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemCheckJustification> findOne(Long id) {
        log.debug("Request to get ItemCheckJustification : {}", id);
        return itemCheckJustificationRepository.findById(id);
    }

    /**
     * Delete the itemCheckJustification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemCheckJustification : {}", id);
        itemCheckJustificationRepository.deleteById(id);
    }

    public void deleteByItemsIds(List<Long> ids) {
        List<ItemCheckJustification> justifications = itemCheckJustificationRepository.findByTaskItemIdIn(ids);
        List<Long> jIds = justifications.stream().map(j -> j.getId()).collect(Collectors.toList());
        checkJustificationFileService.deletByItemJustificationIdIn(jIds);
        itemCheckJustificationRepository.deleteByTaskItemIdIn(ids);
    }
}
