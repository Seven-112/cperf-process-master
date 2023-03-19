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

import com.mshz.domain.StartableTask;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.StartableTaskRepository;
import com.mshz.service.dto.StartableTaskCriteria;

/**
 * Service for executing complex queries for {@link StartableTask} entities in the database.
 * The main input is a {@link StartableTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StartableTask} or a {@link Page} of {@link StartableTask} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StartableTaskQueryService extends QueryService<StartableTask> {

    private final Logger log = LoggerFactory.getLogger(StartableTaskQueryService.class);

    private final StartableTaskRepository startableTaskRepository;

    public StartableTaskQueryService(StartableTaskRepository startableTaskRepository) {
        this.startableTaskRepository = startableTaskRepository;
    }

    /**
     * Return a {@link List} of {@link StartableTask} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StartableTask> findByCriteria(StartableTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StartableTask> specification = createSpecification(criteria);
        return startableTaskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StartableTask} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StartableTask> findByCriteria(StartableTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StartableTask> specification = createSpecification(criteria);
        return startableTaskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StartableTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StartableTask> specification = createSpecification(criteria);
        return startableTaskRepository.count(specification);
    }

    /**
     * Function to convert {@link StartableTaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StartableTask> createSpecification(StartableTaskCriteria criteria) {
        Specification<StartableTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StartableTask_.id));
            }
            if (criteria.getTriggerTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTriggerTaskId(), StartableTask_.triggerTaskId));
            }
            if (criteria.getStartableTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartableTaskId(), StartableTask_.startableTaskId));
            }
            if (criteria.getTriggerTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTriggerTaskName(), StartableTask_.triggerTaskName));
            }
            if (criteria.getStartableTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartableTaskName(), StartableTask_.startableTaskName));
            }
            if (criteria.getTriggerProcessName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTriggerProcessName(), StartableTask_.triggerProcessName));
            }
            if (criteria.getStartableProcessName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartableProcessName(), StartableTask_.startableProcessName));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), StartableTask_.userId));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), StartableTask_.createdAt));
            }
            if (criteria.getStartCond() != null) {
                specification = specification.and(buildSpecification(criteria.getStartCond(), StartableTask_.startCond));
            }
        }
        return specification;
    }
}
