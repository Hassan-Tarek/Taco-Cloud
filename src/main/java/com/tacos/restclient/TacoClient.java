package com.tacos.restclient;

import com.tacos.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class TacoClient {
    private final RestTemplate restTemplate;

    @Autowired
    public TacoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Taco getTacoById(Long id) {
        Map<String, Long> urlVariables = new HashMap<>();
        urlVariables.put("id", id);
        URI url = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/tacos/{id}")
                .build(urlVariables);
        return restTemplate.getForObject(url, Taco.class);
    }

    public void updateTaco(Taco taco) {
        restTemplate.put("http://localhost:8080/api/tacos/{id}",
                taco, taco.getId());
    }

    public void deleteTaco(Taco taco) {
        restTemplate.delete("http://localhost:8080/api/tacos/{id}",
                taco.getId());
    }

    public Taco createTaco(Taco taco) {
        return restTemplate.postForObject("http://localhost:8080/api/tacos",
                taco, Taco.class);
    }
}
