package kz.sparklab.service.impl;

import kz.sparklab.service.RestService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Log4j
public class RestServiceImpl implements RestService {
    @Override
    public HashMap<String, Integer> getFullnessFromAPI() {
        RestTemplate restTemplate = new RestTemplate();

        String endpoint = "http://localhost:8088/sensor";
        var response = restTemplate.getForObject(endpoint, HashMap.class);
        log.info(response);
        return response;
    }
}
