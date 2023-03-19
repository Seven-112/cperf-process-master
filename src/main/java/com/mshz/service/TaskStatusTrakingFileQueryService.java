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

import com.mshz.domain.TaskStatusTrakingFile;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.TaskStatusTrakingFileRepository;
import com.mshz.service.dto.TaskStatusTrakingFileCriteria;

/**
 * Service for executing complex queries for {@link TaskStatusTrakingFile} entities in the database.
 * The main input is a {@link TaskStatusTrakingFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskStatusTrakingFile} or a {@link Page} of {@link TaskStatusTrakingFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskStatusTrakingFileQueryService extends QueryService<TaskStatusTrakingFile> {

    private final Logger log = LoggerFactory.getLogger(TaskStatusTrakingFileQueryService.class);

    private final TaskStatusTrakingFileRepository taskStatusTrakingFileRepository;

    public TaskStatusTrakingFileQueryService(TaskStatusTrakingFileRepository taskStatusTrakingFileRepository) {
        this.taskStatusTrakingFileRepository = taskStatusTrakingFileRepository;
    }

    /**
     * Return a {@link List} of {@link TaskStatusTrakingFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskStatusTrakingFile> findByCriteria(TaskStatusTrakingFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskStatusTrakingFile> specification = createSpecification(criteria);
        return taskStatusTrakingFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskStatusTrakingFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStatusTrakingFile> findByCriteria(TaskStatusTrakingFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskStatusTrakingFile> specification = createSpecification(criteria);
        return taskStatusTrakingFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskStatusTrakingFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskStatusTrakingFile> specification = createSpecification(criteria);
        return taskStatusTrakingFileRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskStatusTrakingFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskStatusTrakingFile> createSpecification(TaskStatusTrakingFileCriteria criteria) {
        Specification<TaskStatusTrakingFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskStatusTrakingFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), TaskStatusTrakingFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), TaskStatusTrakingFile_.fileName));
            }
            if (criteria.getTrackId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrackId(),
                    root -> root.join(TaskStatusTrakingFile_.track, JoinType.LEFT).get(TaskStatusTraking_.id)));
            }
        }
        return specification;
    }
}
