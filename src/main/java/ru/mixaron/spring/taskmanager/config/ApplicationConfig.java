package ru.mixaron.spring.taskmanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mixaron.spring.taskmanager.repository.PersonRepo;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    // нижнее просто копируй всегда

    private final PersonRepo personRepo;

    // ппоиск по name
    @Bean
    public UserDetailsService userDetailsService() {
        return username ->  personRepo.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    //чтобы обеспечить аутентификацию пользователей на основе информации,
    // полученной от UserDetailsService,
    // и использования PasswordEncoder для проверки пароля.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
