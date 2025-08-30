package com.tacos.api;

import com.tacos.data.jpa.UserRepository;
import com.tacos.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("apiUserController")
@RequestMapping(path = "api/users",
        produces = "application/json")
@CrossOrigin("http://tacocloud:8080")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value ->
                        new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createUser(@RequestBody User user) {
        User encodedUser = new User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getFullName(),
                user.getStreet(),
                user.getCity(),
                user.getState(),
                user.getZip(),
                user.getPhone()
        );
        userRepository.save(encodedUser);
    }
}
