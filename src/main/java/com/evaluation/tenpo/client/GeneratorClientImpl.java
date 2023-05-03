package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.GeneratorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeneratorClient {

    @Value("${generator.hostname}")
    private String hostname;
    final private String PATH_GENERATOR ="/generate";

    @Autowired
    private RestTemplate restTemplate;

    public GeneratorDTO getPercentage(){
       var resul = restTemplate.getForEntity(PATH_GENERATOR,GeneratorDTO.class);
       return  resul.getBody();
    }

}
