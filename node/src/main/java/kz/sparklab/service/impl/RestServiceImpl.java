package kz.sparklab.service.impl;

import kz.sparklab.service.RestService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Log4j
public class RestServiceImpl implements RestService {

    @Value("${rest-service.url}")
    private String url;

    @Override
    public HashMap<String, Integer> getFullnessFromAPI() {
        RestTemplate restTemplate = new RestTemplate();

        log.debug("REQUEST URL  " + url);
        var response = restTemplate.getForObject(url, HashMap.class);
        log.info(response);
        return response;
    }
}
