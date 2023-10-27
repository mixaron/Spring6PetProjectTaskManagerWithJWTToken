package ru.mixaron.spring.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mixaron.spring.taskmanager.controller.AuthenticationRequest;
import ru.mixaron.spring.taskmanager.dto.PersonDTO;
import ru.mixaron.spring.taskmanager.dto.Token;
import ru.mixaron.spring.taskmanager.models.Person;
import ru.mixaron.spring.taskmanager.repository.PersonRepo;
import ru.mixaron.spring.taskmanager.util.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PersonRepo personRepo;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper model;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public String register(Person person) {
        var user = Person.builder()
                .name(person.getName())
                .password(passwordEncoder.encode(person.getPassword()))
                .role(Role.USER)
                .build();
        personRepo.save(user);
        return "ok";
    }

    public Token auth(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        var user = personRepo.findByName(request.getName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return Token.builder().token(jwtToken).build();
    }
    private Person convertToPerson(PersonDTO personDTO) {
        return model.map(personDTO, Person.class);
    }
}
