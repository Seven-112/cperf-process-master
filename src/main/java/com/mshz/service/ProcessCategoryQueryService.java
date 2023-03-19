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

import com.mshz.domain.ProcessCategory;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.ProcessCategoryRepository;
import com.mshz.service.dto.ProcessCategoryCriteria;

/**
 * Service for executing complex queries for {@link ProcessCategory} entities in the database.
 * The main input is a {@link ProcessCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessCategory} or a {@link Page} of {@link ProcessCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessCategoryQueryService extends QueryService<ProcessCategory> {

    private final Logger log = LoggerFactory.getLogger(ProcessCategoryQueryService.class);

    private final ProcessCategoryRepository processCategoryRepository;

    public ProcessCategoryQueryService(ProcessCategoryRepository processCategoryRepository) {
        this.processCategoryRepository = processCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link ProcessCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessCategory> findByCriteria(ProcessCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProcessCategory> specification = createSpecification(criteria);
        return processCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProcessCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessCategory> findByCriteria(ProcessCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProcessCategory> specification = createSpecification(criteria);
        return processCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProcessCategory> specification = createSpecification(criteria);
        return processCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProcessCategory> createSpecification(ProcessCategoryCriteria criteria) {
        Specification<ProcessCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProcessCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProcessCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProcessCategory_.description));
            }
        }
        return specification;
    }
}
