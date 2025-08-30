package com.tacos.restclient;

import com.tacos.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class IngredientClient {
    private final RestTemplate restTemplate;

    @Autowired
    public IngredientClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Ingredient> getIngredients() {
        return restTemplate.exchange("htttp://localhost:8080/api/ingredients",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Ingredient>>() {})
                .getBody();
    }

    public Ingredient getIngredientById(String id) {
        return restTemplate.getForObject("http://localhost:8080/api/ingredients/{id}",
                Ingredient.class, id);
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/api/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/api/ingredients/{id}",
                ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/api/ingredients",
                ingredient, Ingredient.class);
    }
}
