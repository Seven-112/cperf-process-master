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

import com.mshz.domain.TaskPauseHistory;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskPauseHistoryRepository;
import com.mshz.service.dto.TaskPauseHistoryCriteria;

/**
 * Service for executing complex queries for {@link TaskPauseHistory} entities in the database.
 * The main input is a {@link TaskPauseHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskPauseHistory} or a {@link Page} of {@link TaskPauseHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskPauseHistoryQueryService extends QueryService<TaskPauseHistory> {

    private final Logger log = LoggerFactory.getLogger(TaskPauseHistoryQueryService.class);

    private final TaskPauseHistoryRepository taskPauseHistoryRepository;

    public TaskPauseHistoryQueryService(TaskPauseHistoryRepository taskPauseHistoryRepository) {
        this.taskPauseHistoryRepository = taskPauseHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link TaskPauseHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskPauseHistory> findByCriteria(TaskPauseHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskPauseHistory> specification = createSpecification(criteria);
        return taskPauseHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskPauseHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskPauseHistory> findByCriteria(TaskPauseHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskPauseHistory> specification = createSpecification(criteria);
        return taskPauseHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskPauseHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskPauseHistory> specification = createSpecification(criteria);
        return taskPauseHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskPauseHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskPauseHistory> createSpecification(TaskPauseHistoryCriteria criteria) {
        Specification<TaskPauseHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskPauseHistory_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), TaskPauseHistory_.taskId));
            }
            if (criteria.getPausedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPausedAt(), TaskPauseHistory_.pausedAt));
            }
            if (criteria.getRestartedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRestartedAt(), TaskPauseHistory_.restartedAt));
            }
            if (criteria.getOldTaskstatus() != null) {
                specification = specification.and(buildSpecification(criteria.getOldTaskstatus(), TaskPauseHistory_.oldTaskstatus));
            }
            if (criteria.getTaskExecutionDeleyExeceed() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskExecutionDeleyExeceed(), TaskPauseHistory_.taskExecutionDeleyExeceed));
            }
        }
        return specification;
    }
}
