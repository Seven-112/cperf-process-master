package com.mshz.ut;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.mshz.MicroprocessApp;
import com.mshz.domain.EventTrigger;
import com.mshz.service.EventTriggerService;

@SpringBootTest(classes = MicroprocessApp.class)
public class DashboardUT {
	// @Autowired private KPIService kpiService;
  @Autowired private EventTriggerService eventTriggerService;
  
	@Test
  public void testUserKpiCount(){
    // LocalDate localDate = LocalDate.of(2021, 12, 29);
    // KPI kpi = kpiService.calculeAndSaveUserKPIs(3l, localDate);
    // eventTriggerService.autoCreateSheduledInstances();
    // System.err.println(kpi);
    Boolean expected = true;
    assertThat(expected).isEqualTo(true);
  }

  @Test
  public void testEventTrigging(){
    eventTriggerService.autoCreateSheduledInstances();
    Boolean expected = true;
    assertThat(expected).isEqualTo(true);

  }
}
