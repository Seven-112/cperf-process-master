package com.mshz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshz.domain.KPI;
import com.mshz.domain.TaskUser;
import com.mshz.domain.enumeration.TaskStatus;
import com.mshz.model.TaskKpiGroupBy;
import com.mshz.repository.KPIRepository;
import com.mshz.repository.TaskUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link KPI}.
 */
@Service
@Transactional
public class KPIService {

    private final Logger log = LoggerFactory.getLogger(KPIService.class);

    private final KPIRepository kPIRepository;

    private final TaskUserRepository taskUserRepository;

    private final String KPI_EXCECEED_PREFIX = "_EXCE";

    private final String KPI_TOTAL_PREFIX = "_TOTAL";

    public KPIService(KPIRepository kPIRepository,TaskUserRepository taskUserRepository) {
        this.kPIRepository = kPIRepository;
        this.taskUserRepository = taskUserRepository;
    }

    /**
     * Save a kPI.
     *
     * @param kPI the entity to save.
     * @return the persisted entity.
     */
    public KPI save(KPI kPI) {
        log.debug("Request to save KPI : {}", kPI);
        return kPIRepository.save(kPI);
    }

    /**
     * Get all the kPIS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KPI> findAll(Pageable pageable) {
        log.debug("Request to get all KPIS");
        return kPIRepository.findAll(pageable);
    }


    /**
     * Get one kPI by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KPI> findOne(Long id) {
        log.debug("Request to get KPI : {}", id);
        return kPIRepository.findById(id);
    }

    /**
     * Delete the kPI by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete KPI : {}", id);
        kPIRepository.deleteById(id);
    }
    
    KPI getByUserIdAndDte(Long userId, LocalDate dte){
        return kPIRepository.findFirstByUserIdAndDteOrderByIdDesc(userId,dte);
    }

    List<KPI> getByUserIdAndDteBetween(Long userId, LocalDate minDate, LocalDate maxDate){
        return kPIRepository.findByUserIdAndDteBetween(userId, minDate, maxDate);
    }

    List<KPI> getByUserIdInAndDte(List<Long> userIds, LocalDate dte){
        return kPIRepository.findByUserIdInAndDte(userIds, dte);
    }

    List<KPI> getByUserIdInAndDteBetween(List<Long> userIds, LocalDate dateMin, LocalDate dateMax){
        return kPIRepository.findByUserIdInAndDteBetween(userIds, dateMin,dateMax);
    }

    List<KPI> getByUserIdIn(List<Long> userIds){
        return kPIRepository.findByUserIdIn(userIds);
    }
    
    List<KPI> getByDteBetween(LocalDate dateMin, LocalDate dateMax){
        return kPIRepository.findByDteBetween(dateMin,dateMax);
    }

    /**
     * calclul and save general kpis without user depends
     * @param localDate date 
     * @return kip: object of saved KPI or null
     */
    public KPI calculAndSaveGlobalKPIs(LocalDate localDate) {
        if(localDate != null)
            return calculeAndSaveUserKPIs(null, localDate);
        return null;
    }


    /**
     * calcul and save all kpi for all users
     * @param localDate
     */
    public void calculAndSaveAllUsersKPis(LocalDate localDate){
        if(localDate != null){
            Pageable pageable = PageRequest.of(0, 200);
            while(pageable != null){
                // finding and save task status traking
                Page<TaskUser> page = taskUserRepository.findAll(pageable);
                for (TaskUser tu : page.getContent()) {
                    calculeAndSaveUserKPIs(tu.getUserId(), localDate);;
                }
                // increment or breack loop by setting pageable to null
                pageable = page.hasNext() ? page.nextPageable() : null;
            }
            pageable = null;
        }
    }

    /**
     * calclule and save kpi for all accociated usersto task
     * @param taskId : id for task with found associated users
     * @param localDate
     */
    public void calculeAndSaveAllTaskUserKPis(Long taskId, LocalDate localDate){
        List<TaskUser> taskUsers = taskUserRepository.findByTask_id(taskId);
        for (TaskUser tu : taskUsers) {
            calculeAndSaveUserKPIs(tu.getUserId(), localDate);
        }
    }

    /**
     * calacule user Kpi at date, if user id is null all app kpi 
     * will generated and saved without specified user
     * @param userId
     * @param localDate
     * @return kpi: generated and saved kpi or null
     */
    public KPI calculeAndSaveUserKPIs(Long userId, LocalDate localDate){
       KPI kpi = null;
       List<TaskKpiGroupBy> list = getUserKPIs(userId, localDate);
       if(list != null && list.size() > 0){
          KPI oldKpi = kPIRepository.findFirstByUserIdAndDteOrderByIdDesc(userId, localDate.minusDays(1));
          kpi = new KPI();
          kpi.setDte(localDate);
          kpi.setUserId(userId);
          // set exetued data
          Long[] counts = countDataByStatusFromList(list, TaskStatus.COMPLETED);
          kpi.setExecuted(counts[0]);
          float rate = (oldKpi != null) ? calculeRate(counts[0].floatValue(), oldKpi.getExecuted().floatValue()) : 0f;
          kpi.setExecutedRate(rate);
            
          kpi.setExecutedLate(counts[1]);
          rate = (oldKpi != null) ? calculeRate(counts[1].floatValue(), oldKpi.getExecutedLate().floatValue()) : 0f;
          kpi.setExecutedLateRate(rate);

          kpi.setTotalExecuted(counts[2]);
          rate = (oldKpi != null) ? calculeRate(counts[2].floatValue(), oldKpi.getTotalExecuted().floatValue()) : 0f;
          kpi.setTotalExecutedRate(rate);

          // set started data
          counts = countDataByStatusFromList(list, TaskStatus.STARTED);
          kpi.setStarted(counts[0]);
          rate = (oldKpi != null) ? calculeRate(counts[0].floatValue(), oldKpi.getStarted().floatValue()) : 0f;
          kpi.setStartedRate(0f);

          kpi.setStartedLate(counts[1]);
          rate = (oldKpi != null) ? calculeRate(counts[1].floatValue(), oldKpi.getStartedLate().floatValue()) : 0f;
          kpi.setStartedLateRate(rate);

          kpi.setTotalStarted(counts[2]);
          rate = (oldKpi != null) ? calculeRate(counts[2].floatValue(), oldKpi.getTotalStarted().floatValue()) : 0f;
          kpi.setTotalStartedRate(rate);
          // set no started data
          counts = countDataByStatusFromList(list, TaskStatus.VALID);
          kpi.setNoStarted(counts[0]);
          rate = (oldKpi != null) ? calculeRate(counts[0].floatValue(), oldKpi.getNoStarted().floatValue()) : 0f;
          kpi.setNoStartedRate(rate);
          // set execution level data
          kpi.setExecutionLevel(calculeExecutionLevel(kpi));
          rate = (oldKpi != null) ? calculeRate(kpi.getExecutionLevel().floatValue(), oldKpi.getExecutionLevel().floatValue()) : 0f;
          kpi.setExecutionLevelRate(rate);

          // savind or update kpi
          kpi = saveOrUpdateKPI(kpi, localDate);
       }
       return kpi;
    }


    public KPI getAvgKPIFromList(List<KPI> kpis){
        KPI kpi = null;
        if(kpis != null && !kpis.isEmpty()){
            kpi = kpis.get(0);
            int size = kpis.size();
            kpis.remove(0);
            for (KPI item : kpis) {
                kpi.setExecuted(kpi.getExecuted().longValue() + item.getExecuted().longValue());
                kpi.setExecutedRate(kpi.getExecutedRate().floatValue() + item.getExecutedRate().floatValue());

                kpi.setExecutedLate(kpi.getExecutedLate().longValue()+ item.getExecutedLate().longValue());
                kpi.setExecutedLateRate(kpi.getExecutedLateRate().floatValue() + item.getExecutedLateRate().floatValue());

                kpi.setTotalExecuted(kpi.getTotalExecuted().longValue()+item.getTotalExecuted().longValue());
                kpi.setTotalExecutedRate(kpi.getTotalExecutedRate().floatValue() + item.getTotalExecutedRate().floatValue());

                // update started data
                kpi.setStarted(kpi.getStarted().longValue() + item.getStarted().longValue());
                kpi.setStartedRate(kpi.getStartedRate().floatValue() + item.getStartedRate().floatValue());

                kpi.setStartedLate(kpi.getStartedLate().longValue() + item.getStartedLate().longValue());
                kpi.setStartedLateRate(kpi.getStartedLateRate().floatValue() + item.getStartedLateRate().floatValue());

                kpi.setTotalStarted(kpi.getTotalStarted().longValue() + item.getTotalStarted().longValue());
                kpi.setTotalStartedRate(kpi.getTotalStartedRate().floatValue() + item.getTotalStartedRate().floatValue());

                // update started data
                kpi.setNoStarted(kpi.getNoStarted().longValue() + item.getNoStarted().longValue());
                kpi.setNoStartedRate(kpi.getNoStartedRate().floatValue() + item.getNoStartedRate().floatValue());

                // update execution level data
                kpi.setExecutionLevel(kpi.getExecutionLevel().floatValue() + item.getExecutionLevel().floatValue());
                kpi.setExecutionLevelRate(kpi.getExecutionLevelRate().floatValue() + kpi.getExecutionLevelRate().floatValue());
            }

            // calcule avgs
            kpi.setExecuted(Long.valueOf(kpi.getExecuted().longValue()/size));
            kpi.setExecutedRate(kpi.getExecutedRate().floatValue()/size);

            kpi.setExecutedLate(Long.valueOf(kpi.getExecutedLate().longValue()/size));
            kpi.setExecutedLateRate(kpi.getExecutedLateRate().floatValue()/size);

            kpi.setTotalExecuted(Long.valueOf(kpi.getTotalExecuted().longValue()/size));
            kpi.setTotalExecutedRate(kpi.getTotalExecutedRate().floatValue()/size);

            // update started data
            kpi.setStarted(Long.valueOf(kpi.getStarted().longValue()/size));
            kpi.setStartedRate(kpi.getStartedRate().floatValue()/size);

            kpi.setStartedLate(Long.valueOf(kpi.getStartedLate().longValue()/size));
            kpi.setStartedLateRate(kpi.getStartedLateRate().floatValue()/size);

            kpi.setTotalStarted(Long.valueOf(kpi.getTotalStarted().longValue()/size));
            kpi.setTotalStartedRate(kpi.getTotalStartedRate().floatValue()/size);

            // update started data
            kpi.setNoStarted(Long.valueOf(kpi.getNoStarted().longValue()/size));
            kpi.setNoStartedRate(kpi.getNoStartedRate().floatValue()/size);

            // update execution level data
            kpi.setExecutionLevel(kpi.getExecutionLevel().floatValue()/size);
            kpi.setExecutionLevelRate(kpi.getExecutionLevelRate().floatValue()/size);
        }
        return kpi;
    }

    private KPI saveOrUpdateKPI(KPI kpi, LocalDate date){
        if(kpi != null && kpi.getDte() != null){
            KPI exists = kPIRepository.findFirstByUserIdAndDteOrderByIdDesc(kpi.getUserId(), kpi.getDte());
            if(exists != null){
                // update exeted data
                exists.setExecuted(kpi.getExecuted());
                exists.setExecutedRate(kpi.getExecutedRate());

                exists.setExecutedLate(kpi.getExecutedLate());
                exists.setExecutedLateRate(kpi.getExecutedLateRate());

                exists.setTotalExecuted(kpi.getTotalExecuted());
                exists.setTotalExecutedRate(kpi.getTotalExecutedRate());

                // update started data
                exists.setStarted(kpi.getStarted());
                exists.setStartedRate(kpi.getStartedRate());

                exists.setStartedLate(kpi.getStartedLate());
                exists.setStartedLateRate(kpi.getStartedLateRate());

                exists.setTotalStarted(kpi.getTotalStarted());
                exists.setTotalStartedRate(kpi.getTotalStartedRate());

                // update started data
                exists.setNoStarted(kpi.getNoStarted());
                exists.setNoStartedRate(kpi.getNoStartedRate());

                // update execution level data
                exists.setExecutionLevel(kpi.getExecutionLevel());
                exists.setExecutionLevelRate(kpi.getExecutionLevelRate());

                return kPIRepository.save(exists);
            }
        }
        return kPIRepository.save(kpi);
    }

    private List<TaskKpiGroupBy> getUserKPIs(Long userId, LocalDate localDate) {
        Instant minDate = LocalTime.MIN.atDate(localDate)
                    .atZone(ZoneId.systemDefault()).toInstant();

        Instant maxDate = LocalTime.MAX.atDate(localDate)
                        .atZone(ZoneId.systemDefault()).toInstant();
        
        List<TaskKpiGroupBy> data = null;
        if(userId != null)
            data = taskUserRepository.groupingByStatusAndExceceedAndCount(userId, minDate, maxDate);
        else
            data = taskUserRepository.groupingByStatusAndExceceedAndCount(minDate, maxDate);
        data = normalizeDataStatus(data);
        return data;
    }

    private List<TaskKpiGroupBy> normalizeDataStatus(List<TaskKpiGroupBy> data){
        if(data != null){
            data.stream().map(item -> {
                if(Arrays.asList(TaskStatus.STARTED,TaskStatus.ON_PAUSE,
                       TaskStatus.SUBMITTED,TaskStatus.EXECUTED)
                    .contains(item.getStatus())){
                        item.setStatus(TaskStatus.STARTED);
                    }
                return item;
            }).collect(Collectors.toList());
        }
        return data;
    }

    private Long[] countDataByStatusFromList(List<TaskKpiGroupBy> data, TaskStatus status){
        Long[] result = new Long[]{0l,0l,0l};
        if(data != null){
            Map<Boolean, Long> map = data.stream().filter(el -> el.getStatus().equals(status))
            .map(el -> {
                if(el.getExceceed() == null )
                    el.setExceceed(Boolean.FALSE);
                if(el.getCount() == null)
                    el.setCount(0l);
                return el;
            }).collect(
                Collectors.groupingBy(el -> el.getExceceed(),
                Collectors.summingLong(el -> el.getCount()))
            );
            if(map.get(Boolean.FALSE) != null)
                result[0] =  map.get(Boolean.FALSE);
            if(map.get(Boolean.TRUE) != null)
                result[1] =  map.get(Boolean.TRUE);

            result[2] = result[0] + result[1];
        }
        return result;
    }

    private Float calculeExecutionLevel(KPI kpi){
        float executed = kpi.getTotalExecuted() != null ? kpi.getTotalExecuted().floatValue() : 0;
        float started = kpi.getTotalStarted() != null ? kpi.getTotalStarted().floatValue() : 0;
        if(executed != 0)
            return (executed / (executed + started));
        return 0f;
    }

    private Float calculeRate(float a, float b){
        if(a != 0){
            float diff = a-b;
            if(diff != 0)
                return (diff/(a * 100));
        }
        return 0f;
    }

}
