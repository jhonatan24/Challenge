package com.evaluation.tenpo.service;

import com.evaluation.tenpo.dto.OperationRequestDTO;
import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.DataNotAvailableException;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.repository.PercentageRepositoryImpl;
import com.evaluation.tenpo.service.impl.OperationServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

  @InjectMocks private OperationServiceImpl operationService;

  @Mock
  private PercentageRepositoryImpl repository;

  @Test
  void calculatedWhenAllOk() throws  DataNotAvailableException, RemoteServiceNotAvailableException {
    PercentageDTO percentageDTO = PercentageDTO.builder().createDate(LocalDateTime.now()).percentage(34).build();
    Mockito.when(repository.getPercentage()).thenReturn(Optional.of(percentageDTO));
    var resul = operationService.calculated(OperationRequestDTO.builder().valueA(23).valueB(43).build());
    Assertions.assertNotNull(resul);
    Assertions.assertNotNull(resul.getResult());
    Mockito.verify(repository,Mockito.times(1)).saveHistoryPercentage(Mockito.any(),Mockito.any(),Mockito.any());
  }
  
  @Test
  void calculatedWhenNotFoundGetPercentage() throws RemoteServiceNotAvailableException {
    Mockito.when(repository.getPercentage()).thenReturn(Optional.empty());
    Assertions.assertThrows(DataNotAvailableException.class,()->{
      operationService.calculated(OperationRequestDTO.builder().valueA(23).valueB(43).build());
    });
    Mockito.verify(repository,Mockito.never()).saveHistoryPercentage(Mockito.any(),Mockito.any(),Mockito.any());
  }
  

}
