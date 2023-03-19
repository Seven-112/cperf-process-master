package com.mshz.service;

import com.mshz.domain.CondNode;
import com.mshz.repository.CondNodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CondNode}.
 */
@Service
@Transactional
public class CondNodeService {

    private final Logger log = LoggerFactory.getLogger(CondNodeService.class);

    private final CondNodeRepository condNodeRepository;
    private final EdgeInfoService edgeInfoService;

    public CondNodeService(CondNodeRepository condNodeRepository, EdgeInfoService edgeInfoService) {
        this.condNodeRepository = condNodeRepository;
        this.edgeInfoService = edgeInfoService;
    }

    /**
     * Save a condNode.
     *
     * @param condNode the entity to save.
     * @return the persisted entity.
     */
    public CondNode save(CondNode condNode) {
        log.debug("Request to save CondNode : {}", condNode);
        return condNodeRepository.save(condNode);
    }

    /**
     * Get all the condNodes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CondNode> findAll() {
        log.debug("Request to get all CondNodes");
        return condNodeRepository.findAll();
    }

    /**
     * Get one condNode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CondNode> findOne(Long id) {
        log.debug("Request to get CondNode : {}", id);
        return condNodeRepository.findById(id);
    }

    /**
     * Delete the condNode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CondNode : {}", id);
        condNodeRepository.deleteById(id);
    }

    public void reproduceCondNodes(Long processId, Long instanceId) {
        List<CondNode> condNodes = condNodeRepository.findByProcessId(processId);
        if (!condNodes.isEmpty()) {
            condNodes.forEach(cnd -> {
                try {
                    CondNode condNode = cnd.clone();
                    condNode.setId(null);
                    condNode.modelId(cnd.getId());
                    condNode.setProcessId(instanceId);
                    condNode = condNodeRepository.save(condNode);
                    if(condNode.getId() != null)
                        edgeInfoService.reproduceEdgeInfo("cond"+condNode.getId(), "cond"+cnd.getId(), instanceId);
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
          });
        }
    }
}
