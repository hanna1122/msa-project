package com.project.trainer.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.trainer.Service.TrainerService;
import com.project.trainer.dto.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("UastIncorrectHttpHeaderInspection")
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final TrainerService trainerService;
    private final Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                TrainerService trainerService,
                                Environment environment) {
        super(authenticationManager);
        this.trainerService = trainerService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserId(),
                            loginRequest.getPassword(),
                            Collections.emptyList()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userId = ((User) authResult.getPrincipal()).getUsername();

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", "ROLE_TRAINER");

        String token = Jwts.builder()
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time")))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
    }
}
