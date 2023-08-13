package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.JwtAuthenticationException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Role;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


    public Authentication authenticate(String validJwtToken) {
        UserDetails user = userRepository.findByUsername(usernameFromToken(validJwtToken)).get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                "",
                user.getAuthorities());
        return authenticationToken;
    }


    public String createToken(String username, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secret)//
                .compact();
    }
    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.toString());
        });

        return result;
    }

    private String usernameFromToken(String validJwtToken) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(validJwtToken).getBody().getSubject();
    }


}
