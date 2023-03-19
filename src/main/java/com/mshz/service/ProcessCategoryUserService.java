package com.mshz.service;

import com.mshz.domain.ProcessCategoryUser;
import com.mshz.repository.ProcessCategoryUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessCategoryUser}.
 */
@Service
@Transactional
public class ProcessCategoryUserService {

    private final Logger log = LoggerFactory.getLogger(ProcessCategoryUserService.class);

    private final ProcessCategoryUserRepository processCategoryUserRepository;

    public ProcessCategoryUserService(ProcessCategoryUserRepository processCategoryUserRepository) {
        this.processCategoryUserRepository = processCategoryUserRepository;
    }

    /**
     * Save a processCategoryUser.
     *
     * @param processCategoryUser the entity to save.
     * @return the persisted entity.
     */
    public ProcessCategoryUser save(ProcessCategoryUser processCategoryUser) {
        log.debug("Request to save ProcessCategoryUser : {}", processCategoryUser);
        return processCategoryUserRepository.save(processCategoryUser);
    }

    /**
     * Get all the processCategoryUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessCategoryUser> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessCategoryUsers");
        return processCategoryUserRepository.findAll(pageable);
    }


    /**
     * Get one processCategoryUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessCategoryUser> findOne(Long id) {
        log.debug("Request to get ProcessCategoryUser : {}", id);
        return processCategoryUserRepository.findById(id);
    }

    /**
     * Delete the processCategoryUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessCategoryUser : {}", id);
        processCategoryUserRepository.deleteById(id);
    }

    public void deleteByUserIdAndCategoryIdAndProcessId(Long userId, Long categoryId, Long processId){
        log.debug("deleteByUserIdAndCategoryIdAndProcessId u {} c {} p {}", userId, categoryId, processId);
        processCategoryUserRepository.deleteByUserIdAndCategoryIdAndProcessId(userId, categoryId, processId);
    }
}
