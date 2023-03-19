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

import com.mshz.domain.TaskFile;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskFileRepository;
import com.mshz.service.dto.TaskFileCriteria;

/**
 * Service for executing complex queries for {@link TaskFile} entities in the database.
 * The main input is a {@link TaskFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskFile} or a {@link Page} of {@link TaskFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskFileQueryService extends QueryService<TaskFile> {

    private final Logger log = LoggerFactory.getLogger(TaskFileQueryService.class);

    private final TaskFileRepository taskFileRepository;

    public TaskFileQueryService(TaskFileRepository taskFileRepository) {
        this.taskFileRepository = taskFileRepository;
    }

    /**
     * Return a {@link List} of {@link TaskFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskFile> findByCriteria(TaskFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskFile> specification = createSpecification(criteria);
        return taskFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskFile> findByCriteria(TaskFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskFile> specification = createSpecification(criteria);
        return taskFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskFile> specification = createSpecification(criteria);
        return taskFileRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskFile> createSpecification(TaskFileCriteria criteria) {
        Specification<TaskFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), TaskFile_.fileId));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TaskFile_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), TaskFile_.type));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(TaskFile_.task, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
