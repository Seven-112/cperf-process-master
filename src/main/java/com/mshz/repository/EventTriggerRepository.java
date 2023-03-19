package com.mshz.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.mshz.domain.EventTrigger;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventTrigger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventTriggerRepository extends JpaRepository<EventTrigger, Long>, JpaSpecificationExecutor<EventTrigger> {

    void deleteByProcess_id(Long id);

    List<EventTrigger> findByNextStartAtLessThanEqualAndDisabledNotAndProcessIsNotNull(LocalDate localDate,
            Boolean disabled);
}
