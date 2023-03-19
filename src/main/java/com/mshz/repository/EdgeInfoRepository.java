package com.mshz.repository;

import java.util.List;

import com.mshz.domain.EdgeInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EdgeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EdgeInfoRepository extends JpaRepository<EdgeInfo, Long>, JpaSpecificationExecutor<EdgeInfo> {
    List<EdgeInfo> findBySourceOrTarget(String source, String target);

    @Modifying(flushAutomatically = true)
    @Query("delete EdgeInfo e where e.source != null and e.target != null and (e.source =:source or e.target =:target)")
    void deleteBySourceOrTarget(@Param("source") String source, @Param("target") String target);

    List<EdgeInfo> findBySourceAndProcessId(String source, Long processId);

    List<EdgeInfo> findBySource(String source);

    void deleteByProcessId(Long processId);

    List<EdgeInfo> findByTargetAndProcessId(String targetId, Long processId);
}
