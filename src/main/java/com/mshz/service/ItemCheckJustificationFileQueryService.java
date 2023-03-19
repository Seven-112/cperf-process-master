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

import com.mshz.domain.ItemCheckJustificationFile;
import com.mshz.domain.*; // for static metamodels
import com.mshz.repository.ItemCheckJustificationFileRepository;
import com.mshz.service.dto.ItemCheckJustificationFileCriteria;

/**
 * Service for executing complex queries for {@link ItemCheckJustificationFile} entities in the database.
 * The main input is a {@link ItemCheckJustificationFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemCheckJustificationFile} or a {@link Page} of {@link ItemCheckJustificationFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemCheckJustificationFileQueryService extends QueryService<ItemCheckJustificationFile> {

    private final Logger log = LoggerFactory.getLogger(ItemCheckJustificationFileQueryService.class);

    private final ItemCheckJustificationFileRepository itemCheckJustificationFileRepository;

    public ItemCheckJustificationFileQueryService(ItemCheckJustificationFileRepository itemCheckJustificationFileRepository) {
        this.itemCheckJustificationFileRepository = itemCheckJustificationFileRepository;
    }

    /**
     * Return a {@link List} of {@link ItemCheckJustificationFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemCheckJustificationFile> findByCriteria(ItemCheckJustificationFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemCheckJustificationFile> specification = createSpecification(criteria);
        return itemCheckJustificationFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemCheckJustificationFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCheckJustificationFile> findByCriteria(ItemCheckJustificationFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemCheckJustificationFile> specification = createSpecification(criteria);
        return itemCheckJustificationFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCheckJustificationFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemCheckJustificationFile> specification = createSpecification(criteria);
        return itemCheckJustificationFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemCheckJustificationFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemCheckJustificationFile> createSpecification(ItemCheckJustificationFileCriteria criteria) {
        Specification<ItemCheckJustificationFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemCheckJustificationFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), ItemCheckJustificationFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), ItemCheckJustificationFile_.fileName));
            }
            if (criteria.getTicjId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTicjId(), ItemCheckJustificationFile_.ticjId));
            }
        }
        return specification;
    }
}
