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

import com.mshz.domain.KPI;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.KPIRepository;
import com.mshz.service.dto.KPICriteria;

/**
 * Service for executing complex queries for {@link KPI} entities in the database.
 * The main input is a {@link KPICriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KPI} or a {@link Page} of {@link KPI} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KPIQueryService extends QueryService<KPI> {

    private final Logger log = LoggerFactory.getLogger(KPIQueryService.class);

    private final KPIRepository kPIRepository;

    public KPIQueryService(KPIRepository kPIRepository) {
        this.kPIRepository = kPIRepository;
    }

    /**
     * Return a {@link List} of {@link KPI} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KPI> findByCriteria(KPICriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KPI> specification = createSpecification(criteria);
        return kPIRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KPI} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KPI> findByCriteria(KPICriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KPI> specification = createSpecification(criteria);
        return kPIRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KPICriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KPI> specification = createSpecification(criteria);
        return kPIRepository.count(specification);
    }

    /**
     * Function to convert {@link KPICriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KPI> createSpecification(KPICriteria criteria) {
        Specification<KPI> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KPI_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), KPI_.userId));
            }
            if (criteria.getDte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDte(), KPI_.dte));
            }
            if (criteria.getExecuted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecuted(), KPI_.executed));
            }
            if (criteria.getExecutedRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedRate(), KPI_.executedRate));
            }
            if (criteria.getExecutedLate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedLate(), KPI_.executedLate));
            }
            if (criteria.getExecutedLateRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedLateRate(), KPI_.executedLateRate));
            }
            if (criteria.getTotalExecuted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalExecuted(), KPI_.totalExecuted));
            }
            if (criteria.getTotalExecutedRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalExecutedRate(), KPI_.totalExecutedRate));
            }
            if (criteria.getStarted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStarted(), KPI_.started));
            }
            if (criteria.getStartedRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartedRate(), KPI_.startedRate));
            }
            if (criteria.getStartedLate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartedLate(), KPI_.startedLate));
            }
            if (criteria.getStartedLateRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartedLateRate(), KPI_.startedLateRate));
            }
            if (criteria.getTotalStarted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalStarted(), KPI_.totalStarted));
            }
            if (criteria.getTotalStartedRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalStartedRate(), KPI_.totalStartedRate));
            }
            if (criteria.getNoStarted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoStarted(), KPI_.noStarted));
            }
            if (criteria.getNoStartedRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoStartedRate(), KPI_.noStartedRate));
            }
            if (criteria.getExecutionLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutionLevel(), KPI_.executionLevel));
            }
            if (criteria.getExecutionLevelRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutionLevelRate(), KPI_.executionLevelRate));
            }
        }
        return specification;
    }
}
