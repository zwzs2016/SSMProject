//package com.uwan.servicegetway.config;
//
//import com.uwan.servicegetway.component.GatewayRateLimitFilterByIP;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
//@Configuration
//public class RouteLocatorConfig {
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes().route(r -> r.path("/recommender/**")
//                .filters(f -> {
//                    f.filter(new GatewayRateLimitFilterByIP(1, 1, Duration.ofSeconds(1)));
////                    f.stripPrefix(1);
////                    f.hystrix(config -> config.setFallbackUri("forward:/userFallBack"));
//                    return f;
//                })
//                .uri("lb://service-recommender")
//        )
//                .build();
//    }
//}
