package com.evaluation.tenpo.service.impl;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.OperationResponseDTO;
import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.DataNotAvailableException;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.repository.PercentageRepository;
import com.evaluation.tenpo.service.OperationService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperationServiceImpl implements OperationService {

  private final PercentageRepository repository;
  
  public OperationServiceImpl(PercentageRepository repository) {
    this.repository = repository;
  }

  @Override
  public OperationResponseDTO calculated(OperationRequestDTO formulaDTO) throws DataNotAvailableException, RemoteServiceNotAvailableException {
    Optional<PercentageDTO> infoPercentage = repository.getPercentage();
    if (infoPercentage.isEmpty()) {
      throw new DataNotAvailableException("no se pudo efectuar la operacion");
    }
    double result = this.sum(formulaDTO.getValueA(), formulaDTO.getValueB(), infoPercentage.get().getPercentage());
    repository.saveHistoryPercentage(infoPercentage.get(),formulaDTO,result);
    return OperationResponseDTO.builder().result(result).build();
  }

  private double sum(double a, double b, double servicePercentage) {
    log.info("service info {}",servicePercentage);
    var sum = Double.sum(a,b);
    var percentage = servicePercentage/100;
    return (sum * percentage) + sum;
  }
  
}
