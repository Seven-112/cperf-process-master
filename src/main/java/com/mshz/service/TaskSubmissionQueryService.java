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

import com.mshz.domain.TaskSubmission;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskSubmissionRepository;
import com.mshz.service.dto.TaskSubmissionCriteria;

/**
 * Service for executing complex queries for {@link TaskSubmission} entities in the database.
 * The main input is a {@link TaskSubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskSubmission} or a {@link Page} of {@link TaskSubmission} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskSubmissionQueryService extends QueryService<TaskSubmission> {

    private final Logger log = LoggerFactory.getLogger(TaskSubmissionQueryService.class);

    private final TaskSubmissionRepository taskSubmissionRepository;

    public TaskSubmissionQueryService(TaskSubmissionRepository taskSubmissionRepository) {
        this.taskSubmissionRepository = taskSubmissionRepository;
    }

    /**
     * Return a {@link List} of {@link TaskSubmission} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskSubmission> findByCriteria(TaskSubmissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskSubmission> specification = createSpecification(criteria);
        return taskSubmissionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskSubmission} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskSubmission> findByCriteria(TaskSubmissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskSubmission> specification = createSpecification(criteria);
        return taskSubmissionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskSubmissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskSubmission> specification = createSpecification(criteria);
        return taskSubmissionRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskSubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskSubmission> createSpecification(TaskSubmissionCriteria criteria) {
        Specification<TaskSubmission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskSubmission_.id));
            }
            if (criteria.getSubmitorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitorId(), TaskSubmission_.submitorId));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), TaskSubmission_.comment));
            }
            if (criteria.getStoreUp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStoreUp(), TaskSubmission_.storeUp));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), TaskSubmission_.valid));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(TaskSubmission_.task, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
