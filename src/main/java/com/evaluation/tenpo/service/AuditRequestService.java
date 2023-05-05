package com.evaluation.tenpo.service;

import com.evaluation.tenpo.model.AuditRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditRequestService {
  void save(AuditRequest auditRequest);

  Page<AuditRequest> finAllPage(Pageable pageable);
}
