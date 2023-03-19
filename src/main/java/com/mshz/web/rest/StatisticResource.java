package com.mshz.web.rest;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mshz.domain.KPI;
import com.mshz.domain.enumeration.PerfIndicatorUnity;
import com.mshz.service.StatisticService;

@RestController
@RequestMapping("/api/stats/kpis")
public class StatisticResource {

    private final Logger log = LoggerFactory.getLogger(StatisticResource.class);

    private final StatisticService statisticService;

    public StatisticResource(StatisticService statisticService){
        this.statisticService = statisticService;
    }

    /**
     * retourne un idicateur de performance à l'instant t
     * @param status
     * @param startIntervalTime
     * @param endIntervalTime
     * @param userIds
     * @param execeed
     * @return response of PerfIndicator
     */
    @GetMapping("/find")
    public ResponseEntity<KPI> getOneKPI(
        @RequestParam(name = "date", required = false) LocalDate localDate,
        @RequestParam(name = "userIds", required = false) List<Long> userIds

    ){
        log.debug("request to ge kpi by date: {}  and userId: {}", localDate, userIds);
        KPI kpi = statisticService.find(userIds, localDate);
        return ResponseEntity.ok().body(kpi);
    }

    /**
     * 
     * @param minDate
     * @param maxDate
     * @param userIds
     * @param unity
     * @return list of kpis
     */
    @GetMapping("/list")
    public ResponseEntity<List<KPI>> list(
        @RequestParam(name = "minDate", required = false) LocalDate minDate,
        @RequestParam(name = "maxDate", required = false) LocalDate maxDate,
        @RequestParam(name = "userIds", required = false) List<Long> userIds,
        @RequestParam(name = "unity", required = false) PerfIndicatorUnity unity

    ){
        log.debug("request to get kpis by userIds: {} and date between {} and {}", userIds, minDate, maxDate);
        List<KPI> result = statisticService.list(userIds, minDate, maxDate, unity);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all/track")
    public ResponseEntity<Void> generateAllTaskTrakinData(){
        statisticService.generateAllTaskTrakinData();
        return ResponseEntity.noContent().build();
    }
}
