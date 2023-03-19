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

import com.mshz.domain.ProcessCategoryUser;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.ProcessCategoryUserRepository;
import com.mshz.service.dto.ProcessCategoryUserCriteria;

/**
 * Service for executing complex queries for {@link ProcessCategoryUser} entities in the database.
 * The main input is a {@link ProcessCategoryUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessCategoryUser} or a {@link Page} of {@link ProcessCategoryUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessCategoryUserQueryService extends QueryService<ProcessCategoryUser> {

    private final Logger log = LoggerFactory.getLogger(ProcessCategoryUserQueryService.class);

    private final ProcessCategoryUserRepository processCategoryUserRepository;

    public ProcessCategoryUserQueryService(ProcessCategoryUserRepository processCategoryUserRepository) {
        this.processCategoryUserRepository = processCategoryUserRepository;
    }

    /**
     * Return a {@link List} of {@link ProcessCategoryUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessCategoryUser> findByCriteria(ProcessCategoryUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProcessCategoryUser> specification = createSpecification(criteria);
        return processCategoryUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProcessCategoryUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessCategoryUser> findByCriteria(ProcessCategoryUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProcessCategoryUser> specification = createSpecification(criteria);
        return processCategoryUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessCategoryUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProcessCategoryUser> specification = createSpecification(criteria);
        return processCategoryUserRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessCategoryUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProcessCategoryUser> createSpecification(ProcessCategoryUserCriteria criteria) {
        Specification<ProcessCategoryUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProcessCategoryUser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), ProcessCategoryUser_.userId));
            }
            if (criteria.getUserFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserFullName(), ProcessCategoryUser_.userFullName));
            }
            if (criteria.getUserEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserEmail(), ProcessCategoryUser_.userEmail));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), ProcessCategoryUser_.categoryId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), ProcessCategoryUser_.processId));
            }
        }
        return specification;
    }
}
