package com.simsek.food.delivery.microservice.payment.config;

import com.simsek.food.delivery.microservice.payment.dto.PaymentResponse;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<PaymentResponse> serializer = new Jackson2JsonRedisSerializer<>(PaymentResponse.class);
        template.setDefaultSerializer(serializer);

        return template;
    }
}
