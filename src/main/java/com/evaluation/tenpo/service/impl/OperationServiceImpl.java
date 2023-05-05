package com.evaluation.tenpo.service.impl;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.OperationResponseDTO;
import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.DataNotAvailableException;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.repository.PercentageRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperationServiceImpl implements com.evaluation.tenpo.service.OperationService {

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
    Integer result = this.sum(formulaDTO.getValueA(), formulaDTO.getValueB(), infoPercentage.get().getPercentage());
    repository.saveHistoryPercentage(infoPercentage.get(),formulaDTO,result);
    return OperationResponseDTO.builder().result(result).build();
  }

  private Integer sum(Integer a, Integer b, Integer percentage) {
    return a + b + percentage;
  }
  
}
