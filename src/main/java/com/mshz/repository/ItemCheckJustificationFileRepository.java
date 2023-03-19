package com.mshz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mshz.domain.ItemCheckJustificationFile;

/**
 * Spring Data  repository for the ItemCheckJustificationFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCheckJustificationFileRepository extends JpaRepository<ItemCheckJustificationFile, Long>, JpaSpecificationExecutor<ItemCheckJustificationFile> {
    /**
     * delete justification file by list of justification ids
     * @param jIds : list of item justification id 
     * @return void
     */
    void deleteByTicjIdIn(List<Long> jIds);
    
}
