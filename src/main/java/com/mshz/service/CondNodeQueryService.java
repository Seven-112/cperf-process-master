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

import com.mshz.domain.CondNode;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.CondNodeRepository;
import com.mshz.service.dto.CondNodeCriteria;

/**
 * Service for executing complex queries for {@link CondNode} entities in the database.
 * The main input is a {@link CondNodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CondNode} or a {@link Page} of {@link CondNode} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CondNodeQueryService extends QueryService<CondNode> {

    private final Logger log = LoggerFactory.getLogger(CondNodeQueryService.class);

    private final CondNodeRepository condNodeRepository;

    public CondNodeQueryService(CondNodeRepository condNodeRepository) {
        this.condNodeRepository = condNodeRepository;
    }

    /**
     * Return a {@link List} of {@link CondNode} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CondNode> findByCriteria(CondNodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CondNode> specification = createSpecification(criteria);
        return condNodeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CondNode} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CondNode> findByCriteria(CondNodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CondNode> specification = createSpecification(criteria);
        return condNodeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CondNodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CondNode> specification = createSpecification(criteria);
        return condNodeRepository.count(specification);
    }

    /**
     * Function to convert {@link CondNodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CondNode> createSpecification(CondNodeCriteria criteria) {
        Specification<CondNode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CondNode_.id));
            }
            if (criteria.getLogigramPosX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosX(), CondNode_.logigramPosX));
            }
            if (criteria.getLogigramPosY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosY(), CondNode_.logigramPosY));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), CondNode_.processId));
            }
            if (criteria.getModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModelId(), CondNode_.modelId));
            }
        }
        return specification;
    }
}
