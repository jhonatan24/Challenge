package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.PercentageDTO;
import com.evaluation.tenpo.exception.RemoteServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PercentageClientImpl implements PercentageClient {

  private final String PATH_GENERATOR = "/generate";

  @Value("${app.percentage.hostname}")
  private String hostname;


  private RestTemplate restTemplate;

  public PercentageClientImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public PercentageDTO getPercentage() throws RemoteServiceNotAvailableException {
    String url =hostname.concat(PATH_GENERATOR);
    log.info("call service getPercentage {}",url);
    try {
      var resul = restTemplate.getForEntity(url, PercentageDTO.class);
      log.info("call response getPercentage {}",resul.getBody());
      return resul.getBody();
    } catch (HttpClientErrorException exception) {
      log.error("fail call percentage",exception);
      if (exception.getStatusCode().is5xxServerError()) {
        throw new RemoteServiceNotAvailableException(exception.getMessage());
      }
    }
    return null;
  }
}
