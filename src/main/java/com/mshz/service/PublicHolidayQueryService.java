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

import com.mshz.domain.PublicHoliday;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.PublicHolidayRepository;
import com.mshz.service.dto.PublicHolidayCriteria;

/**
 * Service for executing complex queries for {@link PublicHoliday} entities in the database.
 * The main input is a {@link PublicHolidayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PublicHoliday} or a {@link Page} of {@link PublicHoliday} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PublicHolidayQueryService extends QueryService<PublicHoliday> {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayQueryService.class);

    private final PublicHolidayRepository publicHolidayRepository;

    public PublicHolidayQueryService(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
    }

    /**
     * Return a {@link List} of {@link PublicHoliday} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PublicHoliday> findByCriteria(PublicHolidayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PublicHoliday> specification = createSpecification(criteria);
        return publicHolidayRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PublicHoliday} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicHoliday> findByCriteria(PublicHolidayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PublicHoliday> specification = createSpecification(criteria);
        return publicHolidayRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PublicHolidayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PublicHoliday> specification = createSpecification(criteria);
        return publicHolidayRepository.count(specification);
    }

    /**
     * Function to convert {@link PublicHolidayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PublicHoliday> createSpecification(PublicHolidayCriteria criteria) {
        Specification<PublicHoliday> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PublicHoliday_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PublicHoliday_.name));
            }
            if (criteria.getOfDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfDate(), PublicHoliday_.ofDate));
            }
        }
        return specification;
    }
}
