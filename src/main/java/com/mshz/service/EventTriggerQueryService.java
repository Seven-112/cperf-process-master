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

import com.mshz.domain.EventTrigger;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.EventTriggerRepository;
import com.mshz.service.dto.EventTriggerCriteria;

/**
 * Service for executing complex queries for {@link EventTrigger} entities in the database.
 * The main input is a {@link EventTriggerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventTrigger} or a {@link Page} of {@link EventTrigger} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventTriggerQueryService extends QueryService<EventTrigger> {

    private final Logger log = LoggerFactory.getLogger(EventTriggerQueryService.class);

    private final EventTriggerRepository eventTriggerRepository;

    public EventTriggerQueryService(EventTriggerRepository eventTriggerRepository) {
        this.eventTriggerRepository = eventTriggerRepository;
    }

    /**
     * Return a {@link List} of {@link EventTrigger} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventTrigger> findByCriteria(EventTriggerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventTrigger> specification = createSpecification(criteria);
        return eventTriggerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EventTrigger} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventTrigger> findByCriteria(EventTriggerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventTrigger> specification = createSpecification(criteria);
        return eventTriggerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventTriggerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventTrigger> specification = createSpecification(criteria);
        return eventTriggerRepository.count(specification);
    }

    /**
     * Function to convert {@link EventTriggerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventTrigger> createSpecification(EventTriggerCriteria criteria) {
        Specification<EventTrigger> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventTrigger_.id));
            }
            if (criteria.getEditorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditorId(), EventTrigger_.editorId));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), EventTrigger_.createdAt));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EventTrigger_.name));
            }
            if (criteria.getRecurrence() != null) {
                specification = specification.and(buildSpecification(criteria.getRecurrence(), EventTrigger_.recurrence));
            }
            if (criteria.getDisabled() != null) {
                specification = specification.and(buildSpecification(criteria.getDisabled(), EventTrigger_.disabled));
            }
            if (criteria.getEditorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditorName(), EventTrigger_.editorName));
            }
            if (criteria.getFirstStartedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstStartedAt(), EventTrigger_.firstStartedAt));
            }
            if (criteria.getNextStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextStartAt(), EventTrigger_.nextStartAt));
            }
            if (criteria.getStartCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartCount(), EventTrigger_.startCount));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessId(),
                    root -> root.join(EventTrigger_.process, JoinType.LEFT).get(Process_.id)));
            }
        }
        return specification;
    }
}
