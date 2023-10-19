package ru.mixaron.spring.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.mixaron.spring.taskmanager.security.Auth;
import ru.mixaron.spring.taskmanager.service.PersonDetailService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends SecurityConfigurerAdapter {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails manager =  User.withUsername("user").password(passwordEncoder()
                .encode("password")).roles("USER").build();
        return new InMemoryUserDetailsManager(manager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/task/auth","/task/registration", "/error", "/css/**").permitAll()
                        .anyRequest().authenticated())
                .logout((logout) -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/auth/login"))
                .formLogin(form -> form
                        .loginPage("/task/auth").permitAll()
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/task", true)
                        .failureUrl("/task/auth?error")
                        .permitAll()
                );
        return http.build();
    }

    @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }



}
