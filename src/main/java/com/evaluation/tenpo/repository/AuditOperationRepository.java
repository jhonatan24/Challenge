package com.evaluation.tenpo.repository;

import com.evaluation.tenpo.model.AuditOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditOperationRepository extends JpaRepository<AuditOperation,Long> {

    @Query("from AuditOperation order by createDate desc limit 1 ")
    Optional<AuditOperation> findLastCreateDate();

}
