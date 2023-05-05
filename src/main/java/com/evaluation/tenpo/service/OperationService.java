package com.evaluation.tenpo.service;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.OperationResponseDTO;
import com.evaluation.tenpo.exception.DataNotAvailableException;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;

public interface OperationService {
  OperationResponseDTO calculated(OperationRequestDTO formulaDTO)
      throws DataNotAvailableException, RemoteServiceNotAvailableException;
}
