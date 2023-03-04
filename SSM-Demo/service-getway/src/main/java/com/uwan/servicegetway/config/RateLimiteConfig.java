package com.uwan.servicegetway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiteConfig {

    /**
     * 接口限流：根据请求路径限流
     * @return
     */
    /*
         如果不使用@Primary注解，会报如下错误，需要注意
    Description:
    Parameter 1 of method requestRateLimiterGatewayFilterFactory in org.springframework.cloud.gateway.config.GatewayAutoConfiguration required a single bean, but 3 were found:
            - pathKeyResolver: defined by method 'pathKeyResolver' in class path resource [com/mkevin/gateway/config/RateLimiteConfig.class]
            - ipKeyResolver: defined by method 'ipKeyResolver' in class path resource [com/mkevin/gateway/config/RateLimiteConfig.class]
            - userKeyResolver: defined by method 'userKeyResolver' in class path resource [com/mkevin/gateway/config/RateLimiteConfig.class]
    Action:
    Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier
    to identify the bean that should be consumed
    */
//    @Bean
//    @Primary
//    public KeyResolver pathKeyResolver() {
//        //写法1
//        return exchange -> Mono.just(
//                exchange.getRequest()
//                        .getPath()
//                        .toString()
//        );
//
//        /*
//        //写法2
//        return new KeyResolver() {
//            @Override
//            public Mono<String> resolve(ServerWebExchange exchange) {
//                return Mono.just(exchange.getRequest()
//                        .getPath()
//                        .toString());
//            }
//        };
//        */
//    }

    /**
     * 根据请求IP限流
     * @return
     */
//    @Bean
//    public KeyResolver ipKeyResolver() {
//        return exchange -> Mono.just(
//                exchange.getRequest()
//                        .getRemoteAddress()
//                        .getAddress()
//                        .getHostAddress()
////                        .getHostName()
//        );
//    }

    /**
     * 根据请求参数中的userId进行限流
     *
     * 请求地址写法：http://localhost:8801/rate/123?userId=lisi
     *
     * @return
     */
//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(
//                exchange.getRequest()
//                        .getQueryParams()
//                        .getFirst("userId")
//        );
//    }
}
