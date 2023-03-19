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

import com.mshz.domain.TaskStatusTraking;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskStatusTrakingRepository;
import com.mshz.service.dto.TaskStatusTrakingCriteria;

/**
 * Service for executing complex queries for {@link TaskStatusTraking} entities in the database.
 * The main input is a {@link TaskStatusTrakingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskStatusTraking} or a {@link Page} of {@link TaskStatusTraking} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskStatusTrakingQueryService extends QueryService<TaskStatusTraking> {

    private final Logger log = LoggerFactory.getLogger(TaskStatusTrakingQueryService.class);

    private final TaskStatusTrakingRepository taskStatusTrakingRepository;

    public TaskStatusTrakingQueryService(TaskStatusTrakingRepository taskStatusTrakingRepository) {
        this.taskStatusTrakingRepository = taskStatusTrakingRepository;
    }

    /**
     * Return a {@link List} of {@link TaskStatusTraking} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskStatusTraking> findByCriteria(TaskStatusTrakingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskStatusTraking> specification = createSpecification(criteria);
        return taskStatusTrakingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskStatusTraking} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStatusTraking> findByCriteria(TaskStatusTrakingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskStatusTraking> specification = createSpecification(criteria);
        return taskStatusTrakingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskStatusTrakingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskStatusTraking> specification = createSpecification(criteria);
        return taskStatusTrakingRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskStatusTrakingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskStatusTraking> createSpecification(TaskStatusTrakingCriteria criteria) {
        Specification<TaskStatusTraking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskStatusTraking_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), TaskStatusTraking_.taskId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), TaskStatusTraking_.status));
            }
            if (criteria.getTracingAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTracingAt(), TaskStatusTraking_.tracingAt));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), TaskStatusTraking_.userId));
            }
            if (criteria.getEditable() != null) {
                specification = specification.and(buildSpecification(criteria.getEditable(), TaskStatusTraking_.editable));
            }
            if (criteria.getExeceed() != null) {
                specification = specification.and(buildSpecification(criteria.getExeceed(), TaskStatusTraking_.execeed));
            }
            if (criteria.getPerfIndicator() != null) {
                specification = specification.and(buildSpecification(criteria.getPerfIndicator(), TaskStatusTraking_.perfIndicator));
            }
        }
        return specification;
    }
}
