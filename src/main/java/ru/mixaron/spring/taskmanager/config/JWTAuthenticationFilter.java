package ru.mixaron.spring.taskmanager.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mixaron.spring.taskmanager.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    // проверка есть ли юзер в бд (надо реализовать свой метод)
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userName = jwtService.extractName(jwt); // todo достать Мейл/Имя пользователя из JWT токена
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails  userDetails = this.userDetailsService.loadUserByUsername(userName);
            // если токен валидный создаем
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( // это аутентификации пользователя в системе
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // прдеставление деталей


                // Установка объекта аутентификации позволяет приложению сохранить информацию о пользователе,
                // его ролях и других атрибутах безопасности, которые могут быть использованы
                // для авторизации пользовательских запросов в дальнейшем.
                SecurityContextHolder.getContext().setAuthentication(authToken); // поставили контекст холдер
            }
        }
        filterChain.doFilter(request, response); // всегда вызывать, чтобы уепочка работала
    }
}
