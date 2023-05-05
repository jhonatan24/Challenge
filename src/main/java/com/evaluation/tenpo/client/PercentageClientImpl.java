package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PercentageClientImpl implements PercentageClient {

  private final String PATH_GENERATOR = "generate";

  @Value("${app.percentage.hostname}")
  private String hostname;

  private RestTemplate restTemplate;

  public PercentageClientImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Cacheable(value = "percentage")
  public PercentageDTO getPercentage() throws RemoteServiceNotAvailableException {
    try {
      var resul = restTemplate.getForEntity(hostname.concat(PATH_GENERATOR), PercentageDTO.class);
      return resul.getBody();
    } catch (HttpClientErrorException exception) {
      if (exception.getStatusCode().is5xxServerError()) {
        throw new RemoteServiceNotAvailableException(exception.getMessage());
      }
    }
    return null;
  }
}
