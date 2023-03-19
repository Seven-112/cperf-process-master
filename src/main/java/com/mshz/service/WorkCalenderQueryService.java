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

import com.mshz.domain.WorkCalender;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.WorkCalenderRepository;
import com.mshz.service.dto.WorkCalenderCriteria;

/**
 * Service for executing complex queries for {@link WorkCalender} entities in the database.
 * The main input is a {@link WorkCalenderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkCalender} or a {@link Page} of {@link WorkCalender} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkCalenderQueryService extends QueryService<WorkCalender> {

    private final Logger log = LoggerFactory.getLogger(WorkCalenderQueryService.class);

    private final WorkCalenderRepository workCalenderRepository;

    public WorkCalenderQueryService(WorkCalenderRepository workCalenderRepository) {
        this.workCalenderRepository = workCalenderRepository;
    }

    /**
     * Return a {@link List} of {@link WorkCalender} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkCalender> findByCriteria(WorkCalenderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkCalender> specification = createSpecification(criteria);
        return workCalenderRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkCalender} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkCalender> findByCriteria(WorkCalenderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkCalender> specification = createSpecification(criteria);
        return workCalenderRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkCalenderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkCalender> specification = createSpecification(criteria);
        return workCalenderRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkCalenderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkCalender> createSpecification(WorkCalenderCriteria criteria) {
        Specification<WorkCalender> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkCalender_.id));
            }
            if (criteria.getDayNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDayNumber(), WorkCalender_.dayNumber));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), WorkCalender_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), WorkCalender_.endTime));
            }
        }
        return specification;
    }
}
