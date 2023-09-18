package ru.mkotlov789.edu.pet.tasktrackerbackend.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.JwtService;

import java.io.IOException;
/**
 * JwtAuthFilter is a Spring Security filter responsible for authenticating requests
 * using JSON Web Tokens (JWT). It intercepts incoming HTTP requests and checks for
 * the presence of a valid JWT in the request headers.
 *
 * If a valid JWT is found, it is used to authenticate the user by setting the
 * user's security context, allowing access to secured API endpoints.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        String token = jwtService.getTokenFromRequest(request);
        if(token != null && jwtService.validateToken(token) ) {
            UsernamePasswordAuthenticationToken authentication = jwtService.authenticate(token);
            if (authentication !=null) {
//                authentication.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
                log.info("authentication using jwt is successful");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
