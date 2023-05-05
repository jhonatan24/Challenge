package com.evaluation.tenpo.controller;

import com.evaluation.tenpo.model.AuditRequest;
import com.evaluation.tenpo.service.AuditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auditRequest")
public class AuditRequestController {

    @Autowired
    private AuditRequestService service;

    @GetMapping
    public Page<AuditRequest> findAllPage(Pageable pageable){
        return service.finAllPage(pageable);
    }

}
