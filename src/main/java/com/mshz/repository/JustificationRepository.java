package com.mshz.repository;

import com.mshz.domain.Justification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Justification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JustificationRepository extends JpaRepository<Justification, Long>, JpaSpecificationExecutor<Justification> {

    void deleteByTaskId(Long taskId);
    
    void deleteByProcessId(Long processId);
}
