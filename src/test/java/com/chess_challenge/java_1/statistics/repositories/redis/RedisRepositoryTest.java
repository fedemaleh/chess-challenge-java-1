package com.chess_challenge.java_1.statistics.repositories.redis;

import com.chess_challenge.java_1.converters.StatisticsConverter;
import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.response.DetectorStatisticsResponse;
import com.chess_challenge.java_1.statistics.StatisticsController;
import com.chess_challenge.java_1.statistics.StatisticsService;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisException;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisRepositoryTest {
    private StatisticsController statisticsController;
    private StatisticsRepository statisticsRepository;
    private RedisAsyncCommands<String, String> commands;

    @BeforeEach
    public void setup() {
        this.commands = mock(RedisAsyncCommands.class);

        StatefulRedisConnection<String, String> connection = mock(StatefulRedisConnection.class);

        when(connection.async()).thenReturn(commands);

        this.statisticsRepository = new RedisRepository(connection);

        StatisticsService statisticsService = new StatisticsService(statisticsRepository);
        StatisticsConverter statisticsConverter = new StatisticsConverter();

        this.statisticsController = new StatisticsController(statisticsService, statisticsConverter);
    }

    @Test
    @Timeout(value = 5)
    void when_redis_queries_succeed_result_is_returned() throws ExecutionException, InterruptedException {
        // Given successful winners and pieces
        this.mockWinnersSuccess();
        this.mockPiecesSuccess();

        // When getting the statistics
        ResponseEntity<DetectorStatisticsResponse> response = this.statisticsController.stats().get();

        // Then request is successful with summarized stats
        assertEquals(HttpStatus.OK, response.getStatusCode());

        DetectorStatisticsResponse stats = response.getBody();

        Map<String, Integer> expectedWinners = Map.of(
                "white", 2,
                "black", 1,
                "none", 1
        );

        assertEquals(expectedWinners, stats.getWinners());

        Map<String, Integer> expectedPiece = Map.of(
                "queen", 2,
                "knight", 1,
                "rook", 1,
                "pawn", 0,
                "king", 0,
                "bishop", 0
        );

        assertEquals(expectedPiece, stats.getCheckmatePieces());

        verify(commands, times(1)).mget("winners:black", "winners:white", "winners:none");
        verify(commands, times(1)).mget("pieces:knight", "pieces:queen", "pieces:king", "pieces:bishop", "pieces:pawn", "pieces:rook");
    }

    @Test
    @Timeout(value = 5)
    void when_winners_query_fails_propagate_an_error() throws ExecutionException, InterruptedException {
        // Given a failing winners repo
        this.mockWinnersFailure();
        this.mockPiecesSuccess();

        // When getting the statistics
        ResponseEntity<DetectorStatisticsResponse> response = this.statisticsController.stats().get();

        // Then request fails with empty body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        verify(commands, times(1)).mget("winners:black", "winners:white", "winners:none");
        verify(commands, times(1)).mget("pieces:knight", "pieces:queen", "pieces:king", "pieces:bishop", "pieces:pawn", "pieces:rook");
    }

    @Test
    @Timeout(value = 5)
    void when_pieces_query_fails_propagate_an_error() throws ExecutionException, InterruptedException {
        // Given a failing winners repo
        this.mockWinnersSuccess();
        this.mockPiecesFailure();

        // When getting the statistics
        ResponseEntity<DetectorStatisticsResponse> response = this.statisticsController.stats().get();

        // Then request fails with empty body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        verify(commands, times(1)).mget("winners:black", "winners:white", "winners:none");
        verify(commands, times(1)).mget("pieces:knight", "pieces:queen", "pieces:king", "pieces:bishop", "pieces:pawn", "pieces:rook");
    }

    @Test
    @Timeout(value = 5)
    void record_winner_success_should_return_nothing() {
        // Given a successful winners repo
        this.mockWinnersSuccess();

        // When recording a winner it should not throw
        assertDoesNotThrow(() -> statisticsRepository.recordWinner(Color.WHITE));

        verify(commands, times(1)).incr("winners:white");
    }

    @Test
    @Timeout(value = 5)
    void record_pieces_success_should_return_nothing() {
        // Given a successful pieces repo
        this.mockPiecesSuccess();

        // When recording a piece it should not throw
        assertDoesNotThrow(() -> statisticsRepository.recordCheckmatePiece(Type.QUEEN));

        verify(commands, times(1)).incr("pieces:queen");
    }

    private void mockWinnersSuccess() {
        RedisFuture<List<KeyValue<String, String>>> winners = MockRedisFuture.success(
                Lists.newArrayList(
                        KeyValue.just("winners:white", "2"),
                        KeyValue.just("winners:black", "1"),
                        KeyValue.just("winners:none", "1")
                )
        );

        when(commands.mget("winners:black", "winners:white", "winners:none"))
                .thenReturn(winners);
    }

    private void mockPiecesSuccess() {
        RedisFuture<List<KeyValue<String, String>>> pieces = MockRedisFuture.success(
                Lists.newArrayList(
                        KeyValue.just("pieces:queen", "2"),
                        KeyValue.just("pieces:knight", "1"),
                        KeyValue.just("pieces:rook", "1"),
                        KeyValue.fromNullable("pieces:king", null),
                        KeyValue.fromNullable("pieces:bishop", null),
                        KeyValue.just("pieces:pawn", "0")
                )
        );

        when(commands.mget("pieces:knight", "pieces:queen", "pieces:king", "pieces:bishop", "pieces:pawn", "pieces:rook"))
                .thenReturn(pieces);
    }

    private void mockWinnersFailure() {
        RedisFuture<List<KeyValue<String, String>>> winners = MockRedisFuture.failed(new RedisException("error"));

        when(commands.mget("winners:black", "winners:white", "winners:none"))
                .thenReturn(winners);

    }

    private void mockPiecesFailure() {
        RedisFuture<List<KeyValue<String, String>>> pieces = MockRedisFuture.failed(new RedisException("error"));

        when(commands.mget("pieces:knight", "pieces:queen", "pieces:king", "pieces:bishop", "pieces:pawn", "pieces:rook"))
                .thenReturn(pieces);
    }
}