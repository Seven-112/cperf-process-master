package com.mshz.service;

import com.mshz.domain.ProcessCategory;
import com.mshz.repository.ProcessCategoryRepository;
import com.mshz.repository.ProcessCategoryUserRepository;
import com.mshz.repository.ProcessRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessCategory}.
 */
@Service
@Transactional
public class ProcessCategoryService {

    private final Logger log = LoggerFactory.getLogger(ProcessCategoryService.class);

    private final ProcessCategoryRepository processCategoryRepository;
    private final ProcessRepository processRepository;
    private final ProcessCategoryUserRepository processCategoryUserRepository;

    public ProcessCategoryService(ProcessCategoryRepository processCategoryRepository,
        ProcessRepository processRepository, ProcessCategoryUserRepository processCategoryUserRepository) {
        this.processCategoryRepository = processCategoryRepository;
        this.processRepository = processRepository;
        this.processCategoryUserRepository = processCategoryUserRepository;
    }

    /**
     * Save a processCategory.
     *
     * @param processCategory the entity to save.
     * @return the persisted entity.
     */
    public ProcessCategory save(ProcessCategory processCategory) {
        log.debug("Request to save ProcessCategory : {}", processCategory);
        return processCategoryRepository.save(processCategory);
    }

    /**
     * Get all the processCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessCategories");
        return processCategoryRepository.findAll(pageable);
    }


    /**
     * Get one processCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessCategory> findOne(Long id) {
        log.debug("Request to get ProcessCategory : {}", id);
        return processCategoryRepository.findById(id);
    }

    /**
     * Delete the processCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessCategory : {}", id);
        processRepository.detachCategory(id);
        processCategoryUserRepository.deleteByCategoryId(id);
        processCategoryRepository.deleteById(id);
    }

    public List<ProcessCategory> findAll() {
        return processCategoryRepository.findAll();
    }
}
