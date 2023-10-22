package ru.mixaron.spring.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends SecurityConfigurerAdapter {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails manager =  User.withUsername("user").password("password").roles("USER").build();
        return new InMemoryUserDetailsManager(manager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/auth","/registration", "/error", "/css/**").permitAll()
                        .anyRequest().authenticated())
                .logout((logout) -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/auth"))
                .formLogin(form -> form
                        .loginPage("/auth").permitAll()
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/task", true)
                        .failureUrl("/auth?error")
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
