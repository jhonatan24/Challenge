package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.GeneratorDTO;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(GeneratorClientImpl.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"generator.hostname=http://localhost:8090/"})
class GeneratorClientImplTest {

  @Autowired
  GeneratorClient generatorClient;

  @MockBean
  private RestTemplate restTemplate;
  
  @Test
  void getPercentageWhenFailService() {
    Mockito.doThrow(new RuntimeException())
        .when(restTemplate)
        .getForEntity(Mockito.anyString(), Mockito.any());
    Optional<GeneratorDTO> resul = generatorClient.getPercentage();
    Assertions.assertTrue(resul.isEmpty());
  }

  @Test
  void getPercentageWhenOkService() {
    var dto = GeneratorDTO.builder().percentage(32).build();
    ResponseEntity<Object> entity = new ResponseEntity(dto, HttpStatus.ACCEPTED);
    Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(entity);
    Optional<GeneratorDTO> resul = generatorClient.getPercentage();
    Assertions.assertTrue(resul.isPresent());
  }
}
