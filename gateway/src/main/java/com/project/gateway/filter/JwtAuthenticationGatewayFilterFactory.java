package com.project.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private static final String ROLE_KEY = "role";
    private final Environment environment;


    public JwtAuthenticationGatewayFilterFactory(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(ROLE_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) return onError(response, "No authorization header", HttpStatus.UNAUTHORIZED);
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer", "");


            if (!isJwtValid(token)) {
                return onError(response, "invalid authorization header", HttpStatus.BAD_REQUEST);
            }

            String userRole = getRole(token);
            if (!hasRole(userRole, config.role)) {
                return onError(response, "invalid role", HttpStatus.FORBIDDEN);
            }

            ServerHttpRequest newRequest = request.mutate()
                    .header("user-id", getUserId(token))
                    .header("role", userRole)
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    private boolean hasRole(String tokenUser, String role) {
        return role.equals(tokenUser);
    }


    private Mono<Void> onError(ServerHttpResponse response, String message, HttpStatus status) {
        response.setStatusCode(status);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Setter
    public static class Config {

        private String role;

    }

    private boolean isJwtValid(String jwt) {
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return !Strings.isBlank(subject);
    }

    public String getUserId(String token) {
        return getClaimsFromJwtToken(token).getSubject();
    }

    public String getRole(String token) {
        return (String) getClaimsFromJwtToken(token).get("roles");
    }

    private Claims getClaimsFromJwtToken(String token) {
        try {
            return Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
