package com.uwan.servicegetway.component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by hgf
 * created time is 2019/12/16
 */
public class GatewayRateLimitFilterByIP implements GatewayFilter, Ordered {
    private final Logger log = LoggerFactory.getLogger(GatewayRateLimitFilterByIP.class);

    /**
     * 单机网关限流用一个ConcurrentHashMap来存储 bucket，
     * 如果是分布式集群限流的话，可以采用 Redis等分布式解决方案
     */
    private static final Map<String, Bucket> LOCAL_CACHE = new ConcurrentHashMap<>();

    /**
     * 桶的最大容量，即能装载 Token 的最大数量
     */
    int capacity;
    /**
     * 每次 Token 补充量
     */
    int refillTokens;
    /**
     * 补充 Token 的时间间隔
     */
    Duration refillDuration;

    public GatewayRateLimitFilterByIP() {
    }

    /**
     * @param capacity       即能装载 Token 的最大数量.
     * @param refillTokens
     * @param refillDuration
     */
    public GatewayRateLimitFilterByIP(int capacity, int refillTokens, Duration refillDuration) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillDuration = refillDuration;
    }

    private Bucket createNewBucket() {
        Refill refill = Refill.of(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        //若ip不存在则创建一个Bucket（令牌桶）
        Bucket bucket = LOCAL_CACHE.computeIfAbsent(ip, k -> createNewBucket());
        log.info("IP:{} ,令牌通可用的Token数量:{} ", ip, bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            //当可用的令牌书为0是，进行限流返回429状态码
            /*log.error("IP:{} ,限制访问:{} ", ip, bucket.getAvailableTokens());
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();*/
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory().wrap(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase().getBytes());
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
    }

    @Override
    public int getOrder() {
        return -1000;
    }

    public static Map<String, Bucket> getLocalCache() {
        return LOCAL_CACHE;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(int refillTokens) {
        this.refillTokens = refillTokens;
    }

    public Duration getRefillDuration() {
        return refillDuration;
    }

    public void setRefillDuration(Duration refillDuration) {
        this.refillDuration = refillDuration;
    }

}

