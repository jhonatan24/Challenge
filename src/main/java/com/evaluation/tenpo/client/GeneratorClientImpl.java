package com.evaluation.tenpo.client;

import com.evaluation.tenpo.dto.GeneratorDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GeneratorClientImpl implements GeneratorClient {

    @Value("${generator.hostname}")
    private String hostname;

    final private String PATH_GENERATOR ="generate";
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Optional<GeneratorDTO> getPercentage(){
        try{
            System.out.println(hostname);
            var resul = restTemplate.getForEntity(hostname.concat(PATH_GENERATOR),GeneratorDTO.class);
            return  Optional.of(resul.getBody());
        }catch (Exception ex){
            log.error("fails call api generate",ex);
            return Optional.empty();
        }
    }

}
