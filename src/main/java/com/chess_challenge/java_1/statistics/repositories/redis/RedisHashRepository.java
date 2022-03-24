package com.chess_challenge.java_1.statistics.repositories.redis;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class RedisHashRepository implements StatisticsRepository {
    private static final String WINNERS = "winners";
    private static final String PIECES = "pieces";

    private final StatefulRedisConnection<String, String> redisConnection;

    public RedisHashRepository(StatefulRedisConnection<String, String> redisConnection) {
        this.redisConnection = redisConnection;
    }

    @Override
    public CompletableFuture<DetectorStatistics> get() {
        CompletionStage<Map<Color, Integer>> futureWinners = executeHGetAll(WINNERS, Color.values());
        CompletionStage<Map<Type, Integer>> futurePieces = executeHGetAll(PIECES, Type.values());

        return futureWinners.thenCombine(futurePieces, DetectorStatistics::new)
                .toCompletableFuture();
    }

    private <T> CompletionStage<Map<T, Integer>> executeHGetAll(String key, T... collectionKeys) {
        return redisConnection.async().hgetall(key)
                .thenApply((values) -> Arrays.stream(collectionKeys)
                                .collect(Collectors.toMap(
                                        value -> value,
                                        value -> this.parseValue(values.get(value.toString().toLowerCase()))
                                ))
                );
    }

    private Integer parseValue(String value) {
        if (NumberUtils.isDigits(value)) {
            return NumberUtils.createInteger(value);
        }

        return 0;
    }

    @Override
    public void recordWinner(Color color) {
        redisConnection.async().hincrby(WINNERS, color.name().toLowerCase(), 1);
    }

    @Override
    public void recordCheckmatePiece(Type type) {
        redisConnection.async().hincrby(PIECES, type.name().toLowerCase(), 1);
    }
}
