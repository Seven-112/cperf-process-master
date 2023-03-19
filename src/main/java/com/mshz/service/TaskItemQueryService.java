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

import com.mshz.domain.TaskItem;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskItemRepository;
import com.mshz.service.dto.TaskItemCriteria;

/**
 * Service for executing complex queries for {@link TaskItem} entities in the database.
 * The main input is a {@link TaskItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskItem} or a {@link Page} of {@link TaskItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskItemQueryService extends QueryService<TaskItem> {

    private final Logger log = LoggerFactory.getLogger(TaskItemQueryService.class);

    private final TaskItemRepository taskItemRepository;

    public TaskItemQueryService(TaskItemRepository taskItemRepository) {
        this.taskItemRepository = taskItemRepository;
    }

    /**
     * Return a {@link List} of {@link TaskItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskItem> findByCriteria(TaskItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskItem> specification = createSpecification(criteria);
        return taskItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskItem> findByCriteria(TaskItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskItem> specification = createSpecification(criteria);
        return taskItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskItem> specification = createSpecification(criteria);
        return taskItemRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskItem> createSpecification(TaskItemCriteria criteria) {
        Specification<TaskItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskItem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TaskItem_.name));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), TaskItem_.taskId));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), TaskItem_.checked));
            }
            if (criteria.getCheckerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckerId(), TaskItem_.checkerId));
            }
            if (criteria.getCheckerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckerName(), TaskItem_.checkerName));
            }
            if (criteria.getCheckerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckerEmail(), TaskItem_.checkerEmail));
            }
            if (criteria.getEditorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditorId(), TaskItem_.editorId));
            }
            if (criteria.getEditorEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditorEmail(), TaskItem_.editorEmail));
            }
            if (criteria.getEditorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditorName(), TaskItem_.editorName));
            }
            if (criteria.getRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getRequired(), TaskItem_.required));
            }
        }
        return specification;
    }
}
