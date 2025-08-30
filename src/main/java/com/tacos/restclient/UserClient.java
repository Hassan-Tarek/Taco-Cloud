package com.tacos.restclient;

import com.tacos.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class UserClient {
    private final Traverson traverson;
    private final RestTemplate restTemplate;

    @Autowired
    public UserClient(Traverson traverson, RestTemplate restTemplate) {
        this.traverson = traverson;
        this.restTemplate = restTemplate;
    }

    public User getUserByUsername(String username) {
        return traverson
                .follow("users")
                .follow(username)
                .toObject(User.class);
    }

    public void updateUser(User user) {
        URI userUri = UriComponentsBuilder
                .fromUri(traverson.follow("users").asLink().toUri())
                .path("/{username}")
                .buildAndExpand(user.getUsername())
                .toUri();

        restTemplate.put(userUri, user);
    }

    public void deleteUser(User user) {
        URI userUri = UriComponentsBuilder
                .fromUri(traverson.follow("users").asLink().toUri())
                .path("/{username}")
                .buildAndExpand(user.getUsername())
                .toUri();

        restTemplate.delete(userUri);
    }

    public User createUser(User user) {
        URI userUri = traverson
                .follow("users")
                .asLink()
                .toUri();

        return restTemplate.postForObject(userUri, user, User.class);
    }
}
