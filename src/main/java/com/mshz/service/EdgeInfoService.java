package com.mshz.service;

import com.mshz.domain.EdgeInfo;
import com.mshz.domain.Task;
import com.mshz.repository.EdgeInfoRepository;
import com.mshz.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EdgeInfo}.
 */
@Service
@Transactional
public class EdgeInfoService {

    private final Logger log = LoggerFactory.getLogger(EdgeInfoService.class);

    private final EdgeInfoRepository edgeInfoRepository;

    private final TaskRepository taskRepository;

    public EdgeInfoService(EdgeInfoRepository edgeInfoRepository, TaskRepository taskRepository) {
        this.edgeInfoRepository = edgeInfoRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Save a edgeInfo.
     *
     * @param edgeInfo the entity to save.
     * @return the persisted entity.
     */
    public EdgeInfo save(EdgeInfo edgeInfo) {
        log.debug("Request to save EdgeInfo : {}", edgeInfo);
        if(normalizeTaskParentId(edgeInfo)){
            return edgeInfoRepository.saveAndFlush(edgeInfo);
        }
        return null;
    }

    /**
     * Get all the edgeInfos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EdgeInfo> findAll() {
        log.debug("Request to get all EdgeInfos");
        return edgeInfoRepository.findAll();
    }

    /**
     * Get one edgeInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EdgeInfo> findOne(Long id) {
        log.debug("Request to get EdgeInfo : {}", id);
        return edgeInfoRepository.findById(id);
    }

    /**
     * Delete the edgeInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EdgeInfo : {}", id);
        EdgeInfo edgeInfo = edgeInfoRepository.findById(id).orElse(null);
        if(edgeInfo != null && edgeInfo.getTarget() != null){
            try {
                Task chirld = taskRepository.getOne(Long.valueOf(edgeInfo.getTarget()));
                if(chirld != null && chirld.getParentId() != null){
                    chirld.setParentId(null);
                    taskRepository.save(chirld);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        edgeInfoRepository.deleteById(id);
    }

    public List<EdgeInfo> findBySourceOrTarget(String source, String target) {
        return edgeInfoRepository.findBySourceOrTarget(source, target);
    }

    public void reproduceEdgeInfo(String sourceOrTarget, String sourceOrTargetRef, Long instanceId) {
        // find all model egdes referenced by source or target
        List<EdgeInfo> edgeInfos = edgeInfoRepository.findBySourceOrTarget(sourceOrTargetRef, sourceOrTargetRef);
        if (!edgeInfos.isEmpty()) {
            edgeInfos.forEach(ei -> {
                try {
                    EdgeInfo edgeInfo = ei.clone();
                    edgeInfo.setId(null);
                    // setting target if current edge is referenced by target
                    if(edgeInfo.getTarget().equals(sourceOrTargetRef)){
                        edgeInfo.setTarget(sourceOrTarget);
                        edgeInfo.setSource(getSourceOrTargetFromSourceOrTargetModel(ei.getSource(), instanceId));
                    }
                    // setting source if current edge is referenced by source
                    if(edgeInfo.getSource().equals(sourceOrTargetRef)){
                        edgeInfo.setSource(sourceOrTarget);
                        edgeInfo.setTarget(getSourceOrTargetFromSourceOrTargetModel(ei.getTarget(), instanceId));
                    }
                    edgeInfo.setProcessId(instanceId);
                    edgeInfoRepository.save(edgeInfo);
                } catch (CloneNotSupportedException e) {
                    log.debug("normalize task parent id error {}", e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }

    private String getSourceOrTargetFromSourceOrTargetModel(String sourceOrTarget, Long instanceId){
        if(sourceOrTarget != null){
            boolean sourceOrTargetIsCondition = (sourceOrTarget.toLowerCase().indexOf("d") != -1) ;
            String modelTaskIdStr =   sourceOrTargetIsCondition ? 
                                       sourceOrTarget.substring(sourceOrTarget.toLowerCase().indexOf("d")+1, sourceOrTarget.length())
                                      : sourceOrTarget;
            log.debug("sourceOrTarget: {} modelTaskId: {}", sourceOrTarget, modelTaskIdStr);
            Task task = taskRepository.findByTaskModelIdAndProcessId(Long.valueOf(modelTaskIdStr), instanceId).orElse(null);
            if(task != null){
                return sourceOrTargetIsCondition  ? "cond"+modelTaskIdStr : modelTaskIdStr;
            }
        }
        return sourceOrTarget;
    }


    private boolean normalizeTaskParentId(EdgeInfo edgeInfo){
        boolean result = false;
        if(edgeInfo != null && edgeInfo.getSource() != null && edgeInfo.getTarget() != null){
            result = true;
            if(edgeInfo.getSource().toLowerCase().indexOf("cond") == -1 
                && edgeInfo.getTarget().toLowerCase().indexOf("cond") == -1){
                try {
                    Task chirld = taskRepository.findById(Long.parseLong(edgeInfo.getTarget())).orElse(null);
                    Task parent = taskRepository.findById(Long.parseLong(edgeInfo.getSource())).orElse(null);
                    if(chirld != null && parent != null){
                       if(chirld.getParentId() != parent.getId()){
                            chirld.setParentId(parent.getId());
                            if(taskRepository.saveAndFlush(chirld) != null){
                                if(parent.getParentId() == chirld.getId()){
                                    parent.setParentId(null);
                                    taskRepository.saveAndFlush(parent);
                                }
                            }else{
                                result = false;
                            }
                       }else{
                           result = true;
                       }
                    }else{
                        result = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }
            } 
        }

        return result;
    }

    public void deleteBySourceOrTarget(String source, String target){
       edgeInfoRepository.deleteBySourceOrTarget(source, target);
    }

    public void deleteByProcessId(Long processId) {
        edgeInfoRepository.deleteByProcessId(processId);
    }
}
