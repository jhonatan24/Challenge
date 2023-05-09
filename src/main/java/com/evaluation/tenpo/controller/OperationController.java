package com.evaluation.tenpo.controller;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.OperationResponseDTO;
import com.evaluation.tenpo.exception.DataNotAvailableException;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.service.OperationService;
import com.evaluation.tenpo.service.impl.OperationServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/operation")
@Slf4j
public class OperationController  {
    

    private OperationService operationService;

    public OperationController(OperationServiceImpl operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/calculate")
    @RateLimiter(name="operationCalculate")
    public OperationResponseDTO calculate(@Validated @RequestBody OperationRequestDTO operationDTO) throws DataNotAvailableException, RemoteServiceNotAvailableException {
        return  operationService.calculated(operationDTO);
    }




}
