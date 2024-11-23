package com.shared_data.shared.components;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public interface JwtFilterI extends WebFilter{

    public Mono<Void> filter(ServerWebExchange exchange,WebFilterChain chain);

    public String obtenerJwtDePeticion(ServerHttpRequest request);
    
}
