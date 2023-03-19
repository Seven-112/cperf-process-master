package com.mshz.repository;

import java.time.Instant;
import java.util.List;

import com.mshz.domain.WorkCalender;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkCalender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkCalenderRepository extends JpaRepository<WorkCalender, Long>, JpaSpecificationExecutor<WorkCalender> {
    List<WorkCalender> findByDayNumber(Integer weekDayNumber);
}
