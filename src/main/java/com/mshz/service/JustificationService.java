package com.mshz.service;

import com.mshz.domain.Justification;
import com.mshz.repository.JustificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Justification}.
 */
@Service
@Transactional
public class JustificationService {

    private final Logger log = LoggerFactory.getLogger(JustificationService.class);

    private final JustificationRepository justificationRepository;

    public JustificationService(JustificationRepository justificationRepository) {
        this.justificationRepository = justificationRepository;
    }

    /**
     * Save a justification.
     *
     * @param justification the entity to save.
     * @return the persisted entity.
     */
    public Justification save(Justification justification) {
        log.debug("Request to save Justification : {}", justification);
        return justificationRepository.save(justification);
    }

    /**
     * Get all the justifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Justification> findAll(Pageable pageable) {
        log.debug("Request to get all Justifications");
        return justificationRepository.findAll(pageable);
    }


    /**
     * Get one justification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Justification> findOne(Long id) {
        log.debug("Request to get Justification : {}", id);
        return justificationRepository.findById(id);
    }

    /**
     * Delete the justification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Justification : {}", id);
        justificationRepository.deleteById(id);
    }

    public void deleteByTaskId(Long taskId) {
        justificationRepository.deleteByTaskId(taskId);
    }
}
