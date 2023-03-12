package com.uwan.servicegetway.component;


import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UriKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        final String path = exchange.getRequest().getURI().getPath();
        System.out.println(path);
        return Mono.just(path);
    }
}
