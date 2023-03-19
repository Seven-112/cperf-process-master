package com.mshz.repository;

import com.mshz.domain.KPI;
import com.mshz.model.TaskKpiGroupBy;

import io.github.jhipster.config.JHipsterDefaults.Cache.Infinispan.Local;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KPI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KPIRepository extends JpaRepository<KPI, Long>, JpaSpecificationExecutor<KPI> {

    KPI findFirstByUserIdAndDteOrderByIdDesc(Long userId, LocalDate dte);

    List<KPI> findByUserIdAndDteBetween(Long userId, LocalDate minDate, LocalDate maxDate);

    List<KPI> findByUserIdInAndDte(List<Long> userIds, LocalDate dte);

    List<KPI> findByUserIdInAndDteBetween(List<Long> userIds, LocalDate dateMin, LocalDate dateMax);

    List<KPI> findByUserIdIn(List<Long> userIds);

    List<KPI> findByDteBetween(LocalDate dateMin, LocalDate dateMax);

}
