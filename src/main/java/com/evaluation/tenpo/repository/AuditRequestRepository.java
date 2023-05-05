package com.evaluation.tenpo.repository;

import com.evaluation.tenpo.model.AuditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRequestRepository extends JpaRepository<AuditRequest,Long> {

}
