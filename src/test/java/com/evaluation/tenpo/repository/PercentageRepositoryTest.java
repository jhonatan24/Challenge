package com.evaluation.tenpo.repository;

import com.evaluation.tenpo.client.PercentageClient;
import com.evaluation.tenpo.config.RetryConfig;
import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.model.AuditOperation;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PercentageRepositoryImpl.class, RetryConfig.class})
class PercentageRepositoryTest {

    @Autowired
    private PercentageRepository percentageRepository;
    @MockBean
    private PercentageClient percentageClient;
    @MockBean private AuditOperationRepository auditOperationRepository;

    @Test
    void getPercentageWhenClientResponseOk() throws RemoteServiceNotAvailableException {
        PercentageDTO percentageDTO= PercentageDTO.builder().percentage(30).build();
        Mockito.when(percentageClient.getPercentage()).thenReturn(percentageDTO);
        var resul = percentageRepository.getPercentage();
        Assertions.assertNotNull(resul);
        Mockito.verify(auditOperationRepository,Mockito.never()).findLastCreateDate();
    }

    @Test
    void getPercentageWhenClientResponseFailButExitsInDatabase() throws RemoteServiceNotAvailableException {
    Mockito.when(percentageClient.getPercentage())
        .thenThrow(new RemoteServiceNotAvailableException());
       Mockito.when(auditOperationRepository.findLastCreateDate()).thenReturn(Optional.of(AuditOperation.builder().percentage(34).build()));
       var resul = percentageRepository.getPercentage();
       Assertions.assertNotNull(resul);
       Mockito.verify(auditOperationRepository,Mockito.times(1)).findLastCreateDate();
    }

    @Test
    void getPercentageWhenClientResponseFailAndNoExitsDataInDatabase() throws RemoteServiceNotAvailableException {
        Mockito.when(percentageClient.getPercentage()).thenThrow(new RemoteServiceNotAvailableException());
        Mockito.when(auditOperationRepository.findLastCreateDate()).thenReturn(Optional.empty());
        var resul = percentageRepository.getPercentage();
        Assertions.assertTrue(resul.isEmpty());
        Mockito.verify(auditOperationRepository,Mockito.times(1)).findLastCreateDate();
    }

    @Test
    void getPercentageWhenClientReturnNull() throws RemoteServiceNotAvailableException {
        Mockito.when(percentageClient.getPercentage()).thenReturn(null);
        Mockito.when(auditOperationRepository.findLastCreateDate()).thenReturn(Optional.of(AuditOperation.builder().percentage(34).build()));
        var resul = percentageRepository.getPercentage();
        Assertions.assertTrue(resul.isPresent());
        Mockito.verify(auditOperationRepository,Mockito.times(1)).findLastCreateDate();
    }

    @Test
    void getPercentageWhenClientResponseFailAndFailDatabase() throws RemoteServiceNotAvailableException {
        Mockito.when(percentageClient.getPercentage()).thenThrow(new RemoteServiceNotAvailableException());
        Mockito.when(auditOperationRepository.findLastCreateDate()).thenThrow(new IllegalArgumentException());
        var resul = percentageRepository.getPercentage();
        Assertions.assertTrue(resul.isEmpty());
        Mockito.verify(auditOperationRepository,Mockito.times(1)).findLastCreateDate();
    }
    
}