//package ru.mixaron.spring.taskmanager.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.StringUtils;
//
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//
//        if (StringUtils.isEmpty(username)) {
//            throw new BadCredentialsException("Username is empty");
//        }
//
//        if (StringUtils.isEmpty(password)) {
//            throw new BadCredentialsException("Password is empty");
//        }
//
//        // Ваша логика проверки логина и пароля
//
//        return super.attemptAuthentication(request, response);
//    }
//}
