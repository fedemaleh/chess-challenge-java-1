package com.chess_challenge.java_1.statistics.repositories.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class RedisClientFactory {
    @Bean
    @Lazy
    public StatefulRedisConnection<String, String> redisConnection(
            @Value("${stats.redis.host}") String host,
            @Value("${stats.redis.port}") int port,
            @Value("${stats.redis.password}") String password) {
        RedisURI uri = RedisURI.Builder.redis(host, port)
                .withPassword(password.toCharArray())
                .build();

        RedisClient client = RedisClient.create(uri);

        return client.connect();
    }
}
