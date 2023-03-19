package com.mshz.repository;

import java.util.List;

import com.mshz.domain.CondNode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CondNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CondNodeRepository extends JpaRepository<CondNode, Long>, JpaSpecificationExecutor<CondNode> {
    List<CondNode> findByProcessId(Long processId);
}
