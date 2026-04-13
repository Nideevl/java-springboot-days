package com.Deep.library_api;

import com.Deep.library_api.model.User;
import com.Deep.library_api.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user);
    }
}
