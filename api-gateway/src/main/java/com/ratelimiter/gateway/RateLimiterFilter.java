
package com.ratelimiter.gateway;

import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RateLimiterFilter implements GlobalFilter, Ordered {

    private final RateLimiterService service;
    private final KafkaTemplate<String,String> kafka;

    public RateLimiterFilter(RateLimiterService s, KafkaTemplate<String,String> k){
        this.service = s;
        this.kafka = k;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        String key = "rate:" + ip;

        return service.isAllowed(key).flatMap(allowed -> {
            if(!allowed){
                kafka.send("events","BLOCKED:"+ip);
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }
            kafka.send("events","ALLOWED:"+ip);
            return chain.filter(exchange);
        });
    }

    @Override
    public int getOrder(){ return -1; }
}
