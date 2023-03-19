package com.mshz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mshz.domain.ItemCheckJustification;

/**
 * Spring Data  repository for the ItemCheckJustification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCheckJustificationRepository extends JpaRepository<ItemCheckJustification, Long>, JpaSpecificationExecutor<ItemCheckJustification> {
    

    List<ItemCheckJustification> findByTaskItemIdIn(List<Long> ids);

    void deleteByTaskItemIdIn(List<Long> ids);
}
