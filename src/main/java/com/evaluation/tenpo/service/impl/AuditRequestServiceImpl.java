package com.evaluation.tenpo.service.impl;

import com.evaluation.tenpo.model.AuditRequest;
import com.evaluation.tenpo.repository.AuditRequestRepository;
import com.evaluation.tenpo.service.AuditRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditRequestServiceImpl implements AuditRequestService {

  @Autowired private AuditRequestRepository auditRequestRepository;

  @Override
  @Async
  public void save(AuditRequest auditRequest) {
    try {
      auditRequestRepository.save(auditRequest);
    } catch (Exception ex) {
      log.error("fail save auditRequest", ex);
    }
  }

  @Override
  public Page<AuditRequest> finAllPage(Pageable pageable) {
    return auditRequestRepository.findAll(pageable);
  }
}
