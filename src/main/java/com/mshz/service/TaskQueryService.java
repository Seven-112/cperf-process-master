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

import com.mshz.domain.Task;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskRepository;
import com.mshz.service.dto.TaskCriteria;

/**
 * Service for executing complex queries for {@link Task} entities in the database.
 * The main input is a {@link TaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Task} or a {@link Page} of {@link Task} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskQueryService extends QueryService<Task> {

    private final Logger log = LoggerFactory.getLogger(TaskQueryService.class);

    private final TaskRepository taskRepository;

    public TaskQueryService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Return a {@link List} of {@link Task} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Task> findByCriteria(TaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Task} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Task> findByCriteria(TaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Task> createSpecification(TaskCriteria criteria) {
        Specification<Task> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Task_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Task_.name));
            }
            if (criteria.getNbMinuites() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbMinuites(), Task_.nbMinuites));
            }
            if (criteria.getNbHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbHours(), Task_.nbHours));
            }
            if (criteria.getNbDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbDays(), Task_.nbDays));
            }
            if (criteria.getNbMonths() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbMonths(), Task_.nbMonths));
            }
            if (criteria.getNbYears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbYears(), Task_.nbYears));
            }
            if (criteria.getStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAt(), Task_.startAt));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Task_.status));
            }
            if (criteria.getPriorityLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getPriorityLevel(), Task_.priorityLevel));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Task_.type));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), Task_.valid));
            }
            if (criteria.getFinishAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishAt(), Task_.finishAt));
            }
            if (criteria.getStartWithProcess() != null) {
                specification = specification.and(buildSpecification(criteria.getStartWithProcess(), Task_.startWithProcess));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), Task_.processId));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentId(), Task_.parentId));
            }
            if (criteria.getTaskModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskModelId(), Task_.taskModelId));
            }
            if (criteria.getPauseAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPauseAt(), Task_.pauseAt));
            }
            if (criteria.getNbPause() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbPause(), Task_.nbPause));
            }
            if (criteria.getLogigramPosX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosX(), Task_.logigramPosX));
            }
            if (criteria.getLogigramPosY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosY(), Task_.logigramPosY));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGroupId(), Task_.groupId));
            }
            if (criteria.getRiskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRiskId(), Task_.riskId));
            }
            if (criteria.getManualMode() != null) {
                specification = specification.and(buildSpecification(criteria.getManualMode(), Task_.manualMode));
            }
            if (criteria.getSheduledStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledStartAt(), Task_.sheduledStartAt));
            }
            if (criteria.getSheduledStartHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledStartHour(), Task_.sheduledStartHour));
            }
            if (criteria.getSheduledStartMinute() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledStartMinute(), Task_.sheduledStartMinute));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), Task_.checked));
            }
            if (criteria.getCurrentPauseHistoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentPauseHistoryId(), Task_.currentPauseHistoryId));
            }
            if (criteria.getExceceed() != null) {
                specification = specification.and(buildSpecification(criteria.getExceceed(), Task_.exceceed));
            }
            if (criteria.getStartupTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getStartupTaskId(),
                    root -> root.join(Task_.startupTask, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
