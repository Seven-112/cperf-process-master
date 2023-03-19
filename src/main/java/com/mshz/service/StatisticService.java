package com.mshz.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mshz.domain.KPI;
import com.mshz.domain.enumeration.PerfIndicatorUnity;

@Service
public class StatisticService {
    
    private final Logger log = LoggerFactory.getLogger(StatisticService.class);

    private final KPIService kpiService;

    public StatisticService(KPIService kpiService){
        this.kpiService = kpiService;
    }

    public LocalDate getNextDateFormDate(LocalDate date,PerfIndicatorUnity unity, LocalDate maxDate){
        LocalDate nexDate = null;
        PerfIndicatorUnity localUnity = unity != null ? unity : PerfIndicatorUnity.DAY;
        if(date != null){
            switch (localUnity) {
                case WEEK:
                    nexDate = date.plusDays(1);
                    break;
                case MONTH:
                    nexDate = date.plusMonths(1);
                    break;
                case SEMESTER:
                    nexDate = date.plusWeeks(1);
                    break;
                case TRIMESTER:
                    nexDate = date.plusMonths(4);
                    break;
                case YEAR:
                    nexDate = date.plusYears(1);
                    break;
                default:
                    nexDate = date.plusDays(1);
                    break;
            }

            if(maxDate != null && nexDate != null && nexDate.isAfter(maxDate)){
               nexDate = maxDate;
            }
            
        }
        return nexDate;
    }

    /**
     * 
     * @param minDate
     * @param maxDate
     * @param userIds
     * @param unity
     * @return list of kpis
     */
    public List<KPI> getIndicators(LocalDate minDate,LocalDate maxDate,List<Long> userIds, PerfIndicatorUnity unity){
        List<KPI> kpis = new ArrayList<>();
        if(minDate != null && maxDate != null){
            LocalDate startDate = minDate.isAfter(maxDate) ? maxDate : minDate;
            LocalDate endDate = maxDate.isBefore(minDate) ? minDate : maxDate;
            do{
                LocalDate nextDate = getNextDateFormDate(startDate, unity, endDate);
                List<KPI> list = null;
                if(userIds != null && !userIds.isEmpty())
                    list = kpiService.getByUserIdInAndDteBetween(userIds, startDate, nextDate);
                else
                    list = kpiService.getByDteBetween(startDate, nextDate);

                if(list != null && !list.isEmpty())
                kpis.add(kpiService.getAvgKPIFromList(list));
                // incrementation
                startDate = nextDate.plusDays(1);
            }while(startDate.isBefore(endDate));
        }
        // resturn sorted indicators by start date
        return kpis.stream().sorted((k,l) -> k.getDte().compareTo(l.getDte())).collect(Collectors.toList());
    }
    
    public void generateAllTaskTrakinData() {
        log.debug("request to generate all task traking data");
        LocalDate localDate = LocalDate.now();
        kpiService.calculAndSaveAllUsersKPis(localDate);
        kpiService.calculAndSaveGlobalKPIs(localDate);
    }

    /**
     * 
     * @param userIds
     * @param localDate
     * @return
     */
    public KPI find(List<Long> userIds, LocalDate localDate) {
        localDate = localDate != null ? localDate : LocalDate.now();
        if(userIds != null && !userIds.isEmpty()){
            userIds = userIds.stream()
                .filter(id -> id != null)
                .map(id -> id.longValue()).distinct()
                .collect(Collectors.toList());
            if(userIds.size()==1){
                return kpiService.getByUserIdAndDte(userIds.get(0), localDate);
            }else{
                List<KPI> kpis = kpiService.getByUserIdInAndDte(userIds, localDate);
                return kpiService.getAvgKPIFromList(kpis);
            }
        }
            
        return kpiService.getByUserIdAndDte(null, localDate);
    }

    public List<KPI> list(List<Long> userIds, LocalDate minDate, LocalDate maxDate, PerfIndicatorUnity unity) {
        maxDate = maxDate == null ? LocalDate.now() : maxDate;
        minDate = minDate == null ? maxDate.minusDays(1) : minDate;
        if(unity == null || PerfIndicatorUnity.DAY.equals(unity)){
            if(userIds != null && !userIds.isEmpty())
                return kpiService.getByUserIdInAndDteBetween(userIds, minDate, maxDate);;
            return kpiService.getByDteBetween(minDate, maxDate);
        }
        return getIndicators(minDate, maxDate, userIds, unity);
    }
    
}
