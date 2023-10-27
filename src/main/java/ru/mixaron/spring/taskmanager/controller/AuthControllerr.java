package ru.mixaron.spring.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mixaron.spring.taskmanager.dto.Token;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerr {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Person personDTO) {
        return ResponseEntity.ok(service.register(personDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Token> auth(@RequestBody AuthenticationRequest personDTO) {
        return ResponseEntity.ok(service.auth(personDTO));
    }
}
