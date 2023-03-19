package com.mshz.repository;

import com.mshz.domain.TaskStatusTrakingFile;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskStatusTrakingFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStatusTrakingFileRepository extends JpaRepository<TaskStatusTrakingFile, Long>, JpaSpecificationExecutor<TaskStatusTrakingFile> {

    @Modifying(flushAutomatically = true)
    @Query("delete TaskStatusTrakingFile tstf where tstf.track != null and tstf.track.id=:tid")
    void deleteByTrackId(@Param("tid") Long id);

    void deleteByTrack_idIn(List<Long> trackIds);

    /*
     * @Modifying(flushAutomatically = true)
     * 
     * @Query("delete TaskStatusTrakingFile tstf where tstf.track != null and tstf.track.taskId=:taskId"
     * )
     * void deleteByTaskId(@Param("taskId") Long taskId);
     */
}
