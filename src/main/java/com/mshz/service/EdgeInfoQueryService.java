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

import com.mshz.domain.EdgeInfo;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.EdgeInfoRepository;
import com.mshz.service.dto.EdgeInfoCriteria;

/**
 * Service for executing complex queries for {@link EdgeInfo} entities in the database.
 * The main input is a {@link EdgeInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EdgeInfo} or a {@link Page} of {@link EdgeInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EdgeInfoQueryService extends QueryService<EdgeInfo> {

    private final Logger log = LoggerFactory.getLogger(EdgeInfoQueryService.class);

    private final EdgeInfoRepository edgeInfoRepository;

    public EdgeInfoQueryService(EdgeInfoRepository edgeInfoRepository) {
        this.edgeInfoRepository = edgeInfoRepository;
    }

    /**
     * Return a {@link List} of {@link EdgeInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EdgeInfo> findByCriteria(EdgeInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EdgeInfo> specification = createSpecification(criteria);
        return edgeInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EdgeInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EdgeInfo> findByCriteria(EdgeInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EdgeInfo> specification = createSpecification(criteria);
        return edgeInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EdgeInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EdgeInfo> specification = createSpecification(criteria);
        return edgeInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link EdgeInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EdgeInfo> createSpecification(EdgeInfoCriteria criteria) {
        Specification<EdgeInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EdgeInfo_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), EdgeInfo_.source));
            }
            if (criteria.getTarget() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTarget(), EdgeInfo_.target));
            }
            if (criteria.getSourceHandle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceHandle(), EdgeInfo_.sourceHandle));
            }
            if (criteria.getTargetHandle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTargetHandle(), EdgeInfo_.targetHandle));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), EdgeInfo_.processId));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), EdgeInfo_.valid));
            }
        }
        return specification;
    }
}
