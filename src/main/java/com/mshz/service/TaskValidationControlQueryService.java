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

import com.mshz.domain.TaskValidationControl;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskValidationControlRepository;
import com.mshz.service.dto.TaskValidationControlCriteria;

/**
 * Service for executing complex queries for {@link TaskValidationControl} entities in the database.
 * The main input is a {@link TaskValidationControlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskValidationControl} or a {@link Page} of {@link TaskValidationControl} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskValidationControlQueryService extends QueryService<TaskValidationControl> {

    private final Logger log = LoggerFactory.getLogger(TaskValidationControlQueryService.class);

    private final TaskValidationControlRepository taskValidationControlRepository;

    public TaskValidationControlQueryService(TaskValidationControlRepository taskValidationControlRepository) {
        this.taskValidationControlRepository = taskValidationControlRepository;
    }

    /**
     * Return a {@link List} of {@link TaskValidationControl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskValidationControl> findByCriteria(TaskValidationControlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskValidationControl> specification = createSpecification(criteria);
        return taskValidationControlRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskValidationControl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskValidationControl> findByCriteria(TaskValidationControlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskValidationControl> specification = createSpecification(criteria);
        return taskValidationControlRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskValidationControlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskValidationControl> specification = createSpecification(criteria);
        return taskValidationControlRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskValidationControlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskValidationControl> createSpecification(TaskValidationControlCriteria criteria) {
        Specification<TaskValidationControl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskValidationControl_.id));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), TaskValidationControl_.label));
            }
            if (criteria.getRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getRequired(), TaskValidationControl_.required));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), TaskValidationControl_.valid));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(TaskValidationControl_.task, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
