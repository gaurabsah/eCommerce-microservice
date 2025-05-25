package com.api.security;

import java.security.Key;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationPreFilter extends AbstractGatewayFilterFactory<AuthenticationPreFilter.Config> {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private List<String> excludedUrls;
    
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public AuthenticationPreFilter() {
        super(Config.class);
    }
    public static class Config{
        private List<String> excludedPatterns;

        public List<String> getExcludedPatterns() {
            return excludedPatterns;
        }



        public void setExcludedPatterns(List<String> excludedPatterns) {
            this.excludedPatterns = excludedPatterns;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            HttpHeaders httpHeaders = request.getHeaders();
            String token = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
            if (isExcluded(path)){
                return chain.filter(exchange);
            }

            if (token == null || !token.startsWith("Bearer ")){
                return handleAuthError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            token = token.substring(7);

            try {
            	Key signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));


                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
//                String role = claims.get("role").toString();
                String role = (claims.get("role") != null) ? claims.get("role").toString() : "USER";

                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("username", username)
                        .header("role", role)
                        .build();

                exchange.getRequest().mutate()
                        .header("username", username)
                        .header("role", role)
                        .build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (Exception e) {
                return handleAuthError(exchange, "Invalid Token", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private boolean isExcluded(String path){
        Predicate<String> isExcluded = pattern -> path.matches(pattern.replace("**", ".*"));
        return excludedUrls.stream().anyMatch(isExcluded);
    }

    private Mono<Void> handleAuthError(ServerWebExchange exchange, String message, HttpStatus status){
        ServerHttpResponse response =  exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        responseBody.put("message", message);
        responseBody.put("status", status.value());
        responseBody.put("errorCode", status.value());

        try{
            byte [] bytes = objectMapper.writeValueAsBytes(responseBody);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}