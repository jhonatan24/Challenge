package com.evaluation.tenpo.repository;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;

import java.util.Optional;

public interface PercentageRepository {
  Optional<PercentageDTO> getPercentage() throws RemoteServiceNotAvailableException;
  void saveHistoryPercentage(PercentageDTO dto, OperationRequestDTO request, double result);
}
