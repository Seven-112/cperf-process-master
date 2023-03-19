package com.mshz.service;

import com.mshz.domain.ItemCheckJustificationFile;
import com.mshz.repository.ItemCheckJustificationFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemCheckJustificationFile}.
 */
@Service
@Transactional
public class ItemCheckJustificationFileService {

    private final Logger log = LoggerFactory.getLogger(ItemCheckJustificationFileService.class);

    private final ItemCheckJustificationFileRepository itemCheckJustificationFileRepository;

    public ItemCheckJustificationFileService(ItemCheckJustificationFileRepository itemCheckJustificationFileRepository) {
        this.itemCheckJustificationFileRepository = itemCheckJustificationFileRepository;
    }

    /**
     * Save a itemCheckJustificationFile.
     *
     * @param itemCheckJustificationFile the entity to save.
     * @return the persisted entity.
     */
    public ItemCheckJustificationFile save(ItemCheckJustificationFile itemCheckJustificationFile) {
        log.debug("Request to save ItemCheckJustificationFile : {}", itemCheckJustificationFile);
        return itemCheckJustificationFileRepository.save(itemCheckJustificationFile);
    }

    /**
     * Get all the itemCheckJustificationFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCheckJustificationFile> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCheckJustificationFiles");
        return itemCheckJustificationFileRepository.findAll(pageable);
    }


    /**
     * Get one itemCheckJustificationFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemCheckJustificationFile> findOne(Long id) {
        log.debug("Request to get ItemCheckJustificationFile : {}", id);
        return itemCheckJustificationFileRepository.findById(id);
    }

    /**
     * Delete the itemCheckJustificationFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemCheckJustificationFile : {}", id);
        itemCheckJustificationFileRepository.deleteById(id);
    }

    public void deletByItemJustificationIdIn(List<Long> jIds) {
        itemCheckJustificationFileRepository.deleteByTicjIdIn(jIds);
    }
}
