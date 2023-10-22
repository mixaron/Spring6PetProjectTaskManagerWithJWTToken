package ru.mixaron.spring.taskmanager.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mixaron.spring.taskmanager.service.PersonDetailServices;

import java.util.Collections;

@Component
public class Auth implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final PersonDetailServices personDetailService;

    public Auth(PasswordEncoder passwordEncoder, PersonDetailServices personDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.personDetailService = personDetailService;
    }

    // это чтобы сравнить зашифрованный пароль
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails personDetails = personDetailService.loadUserByUsername(username);
        String rawPass =authentication.getCredentials().toString();

        if (!passwordEncoder.matches(rawPass, personDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(personDetails, rawPass, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
