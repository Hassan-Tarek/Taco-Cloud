package com.tacos.restclient;

import com.tacos.domain.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {
    private final RestTemplate restTemplate;

    @Autowired
    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TacoOrder getOrderById(Long id) {
        ResponseEntity<TacoOrder> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/api/orders/{id}",
                        TacoOrder.class, id);
        return responseEntity.getBody();
    }

    public void updateOrder(TacoOrder order) {
        restTemplate.put("http://localhost:8080/api/orders/{id}",
                order, order.getId());
    }

    public void deleteOrder(TacoOrder order) {
        restTemplate.delete("http://localhost:8080/api/orders/{id}",
                order.getId());
    }

    public TacoOrder createOrder(TacoOrder order) {
        return restTemplate.postForObject("http://localhost:8080/api/orders",
                order, TacoOrder.class);
    }}
