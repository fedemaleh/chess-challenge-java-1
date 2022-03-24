package com.chess_challenge.java_1.statistics.repositories.redis;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class RedisRepository implements StatisticsRepository {
    private final StatefulRedisConnection<String, String> redisConnection;

    public RedisRepository(StatefulRedisConnection<String, String> redisConnection) {
        this.redisConnection = redisConnection;
    }

    @Override
    public CompletableFuture<DetectorStatistics> get() {
        CompletionStage<Map<Color, Integer>> futureWinners = executeMGet((color) -> Color.valueOf(color.toUpperCase()),
                "winners:black", "winners:white", "winners:none");
        CompletionStage<Map<Type, Integer>> futurePieces = executeMGet((type) -> Type.valueOf(type.toUpperCase()),
                "pieces:knight", "pieces:queen", "pieces:king", "pieces:bishop", "pieces:pawn", "pieces:rook");

        return futureWinners.thenCombine(futurePieces, DetectorStatistics::new)
                .toCompletableFuture();
    }

    private <T> CompletionStage<Map<T, Integer>> executeMGet(Function<String, T> valueMapper, String... keys) {
        return redisConnection.async().mget(keys)
                .thenApply((values) -> values.stream()
                        .collect(Collectors.toMap(
                                entry -> valueMapper.apply(this.formatKey(entry.getKey())),
                                entry -> this.parseValue(entry.getValueOrElse("0"))
                        )));
    }

    private String formatKey(String key) {
        return key.substring(key.indexOf(":") + 1).toUpperCase();
    }

    private Integer parseValue(String value) {
        if (NumberUtils.isDigits(value)){
            return NumberUtils.createInteger(value);
        }

        return 0;
    }

    @Override
    public void recordWinner(Color color) {
        redisConnection.async().incr("winners:" + color.name().toLowerCase());
    }

    @Override
    public void recordCheckmatePiece(Type type) {
        redisConnection.async().incr("pieces:" + type.name().toLowerCase());
    }
}
