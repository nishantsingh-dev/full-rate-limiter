
package com.ratelimiter.gateway;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
public class RateLimiterService {

    private final ReactiveStringRedisTemplate redis;
    private static final int LIMIT = 5;

    public RateLimiterService(ReactiveStringRedisTemplate redis){
        this.redis = redis;
    }

    public Mono<Boolean> isAllowed(String key){
        return redis.opsForValue().increment(key)
            .flatMap(count -> {
                if(count == 1){
                    return redis.expire(key, Duration.ofSeconds(60))
                            .thenReturn(true);
                }
                return Mono.just(count <= LIMIT);
            });
    }
}
