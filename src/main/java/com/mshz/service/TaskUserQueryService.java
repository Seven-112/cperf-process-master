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

import com.mshz.domain.TaskUser;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskUserRepository;
import com.mshz.service.dto.TaskUserCriteria;

/**
 * Service for executing complex queries for {@link TaskUser} entities in the database.
 * The main input is a {@link TaskUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskUser} or a {@link Page} of {@link TaskUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskUserQueryService extends QueryService<TaskUser> {

    private final Logger log = LoggerFactory.getLogger(TaskUserQueryService.class);

    private final TaskUserRepository taskUserRepository;

    public TaskUserQueryService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    /**
     * Return a {@link List} of {@link TaskUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskUser> findByCriteria(TaskUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskUser> specification = createSpecification(criteria);
        return taskUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskUser> findByCriteria(TaskUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskUser> specification = createSpecification(criteria);
        return taskUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskUser> specification = createSpecification(criteria);
        return taskUserRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskUser> createSpecification(TaskUserCriteria criteria) {
        Specification<TaskUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskUser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), TaskUser_.userId));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildSpecification(criteria.getRole(), TaskUser_.role));
            }
            if (criteria.getUserFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserFullName(), TaskUser_.userFullName));
            }
            if (criteria.getUserEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserEmail(), TaskUser_.userEmail));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(TaskUser_.task, JoinType.LEFT).get(Task_.id)));
            }
        }
        return specification;
    }
}
