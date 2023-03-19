package com.mshz.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.mshz.domain.PublicHoliday;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PublicHoliday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long>, JpaSpecificationExecutor<PublicHoliday> {
    Optional<PublicHoliday> findByOfDate(LocalDate localDate);

    List<PublicHoliday> findByOfDateBetween(LocalDate dateMin, LocalDate dateMax);
}
