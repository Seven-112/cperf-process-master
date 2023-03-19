package com.mshz.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mshz.domain.ItemCheckJustification;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.ItemCheckJustificationRepository;
import com.mshz.service.dto.ItemCheckJustificationCriteria;

/**
 * Service for executing complex queries for {@link ItemCheckJustification} entities in the database.
 * The main input is a {@link ItemCheckJustificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemCheckJustification} or a {@link Page} of {@link ItemCheckJustification} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemCheckJustificationQueryService extends QueryService<ItemCheckJustification> {

    private final Logger log = LoggerFactory.getLogger(ItemCheckJustificationQueryService.class);

    private final ItemCheckJustificationRepository itemCheckJustificationRepository;

    public ItemCheckJustificationQueryService(ItemCheckJustificationRepository itemCheckJustificationRepository) {
        this.itemCheckJustificationRepository = itemCheckJustificationRepository;
    }

    /**
     * Return a {@link List} of {@link ItemCheckJustification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemCheckJustification> findByCriteria(ItemCheckJustificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemCheckJustification> specification = createSpecification(criteria);
        return itemCheckJustificationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemCheckJustification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCheckJustification> findByCriteria(ItemCheckJustificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemCheckJustification> specification = createSpecification(criteria);
        return itemCheckJustificationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCheckJustificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemCheckJustification> specification = createSpecification(criteria);
        return itemCheckJustificationRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemCheckJustificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemCheckJustification> createSpecification(ItemCheckJustificationCriteria criteria) {
        Specification<ItemCheckJustification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemCheckJustification_.id));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), ItemCheckJustification_.checked));
            }
            if (criteria.getTaskItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskItemId(), ItemCheckJustification_.taskItemId));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), ItemCheckJustification_.date));
            }
        }
        return specification;
    }
}
