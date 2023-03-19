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

import com.mshz.domain.Process;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.ProcessRepository;
import com.mshz.service.dto.ProcessCriteria;

/**
 * Service for executing complex queries for {@link Process} entities in the database.
 * The main input is a {@link ProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Process} or a {@link Page} of {@link Process} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessQueryService extends QueryService<Process> {

    private final Logger log = LoggerFactory.getLogger(ProcessQueryService.class);

    private final ProcessRepository processRepository;

    public ProcessQueryService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    /**
     * Return a {@link List} of {@link Process} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Process> findByCriteria(ProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Process} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Process> findByCriteria(ProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Process> specification = createSpecification(criteria);
        return processRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Process> createSpecification(ProcessCriteria criteria) {
        Specification<Process> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Process_.id));
            }
            if (criteria.getPriorityLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getPriorityLevel(), Process_.priorityLevel));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), Process_.valid));
            }
            if (criteria.getPreviewStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviewStartAt(), Process_.previewStartAt));
            }
            if (criteria.getStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAt(), Process_.startAt));
            }
            if (criteria.getPreviewFinishAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviewFinishAt(), Process_.previewFinishAt));
            }
            if (criteria.getFinishedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishedAt(), Process_.finishedAt));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Process_.createdAt));
            }
            if (criteria.getStartCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartCount(), Process_.startCount));
            }
            if (criteria.getModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModelId(), Process_.modelId));
            }
            if (criteria.getEditorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditorId(), Process_.editorId));
            }
            if (criteria.getProcedureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcedureId(), Process_.procedureId));
            }
            if (criteria.getRunnableProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunnableProcessId(), Process_.runnableProcessId));
            }
            if (criteria.getQueryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQueryId(), Process_.queryId));
            }
            if (criteria.getCanceledAt() != null) {
                specification = specification.and(buildSpecification(criteria.getCanceledAt(), root -> root.get("canceledAt"))); 
                // .and(buildRangeSpecification(criteria.getCanceledAt(), Process_.canceledAt));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Process_.category, JoinType.LEFT).get(ProcessCategory_.id)));
            }
        }
        return specification;
    }
}
