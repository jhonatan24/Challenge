package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import com.evaluation.tenpo.filter.AuditRequestFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(value = PercentageClientImpl.class,excludeFilters= @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuditRequestFilter.class))
@ExtendWith(MockitoExtension.class)
@TestPropertySource(
    properties = {
      "app.percentage.hostname=http://localhost:8090/",
    })
class PercentageClientImplTest {

  @Autowired private PercentageClientImpl percentageClient;

  @MockBean private RestTemplate restTemplate;

  @Test
  void getPercentageWhenFailService() {
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
        .when(restTemplate)
        .getForEntity(Mockito.anyString(), Mockito.any());
    Assertions.assertThrows(
        RemoteServiceNotAvailableException.class,
        () -> {
          percentageClient.getPercentage();
        });
  }

  @Test
  void getPercentageWhenFailForOtherException() throws RemoteServiceNotAvailableException {
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
            .when(restTemplate)
            .getForEntity(Mockito.anyString(), Mockito.any());
    var result = percentageClient.getPercentage();
    Assertions.assertNull(result);
  }

  @Test
  void getPercentageWhenOkService() throws RemoteServiceNotAvailableException {
    var dto = PercentageDTO.builder().percentage(32).build();
    ResponseEntity<Object> entity = new ResponseEntity(dto, HttpStatus.ACCEPTED);
    Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(entity);
    PercentageDTO resul = percentageClient.getPercentage();
    Assertions.assertNotNull(resul);
  }
}
