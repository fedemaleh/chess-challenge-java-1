package com.chess_challenge.java_1.statistics.repositories.jooq;

import com.chess_challenge.java_1.converters.StatisticsConverter;
import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.response.DetectorStatisticsResponse;
import com.chess_challenge.java_1.statistics.StatisticsController;
import com.chess_challenge.java_1.statistics.StatisticsService;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.chess_challenge.java_1.statistics.repositories.jooq.RecordMocks.newPieceResult;
import static com.chess_challenge.java_1.statistics.repositories.jooq.RecordMocks.newWinnerResult;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JooqRepositoryTest {
    private DSLContext db;

    public StatisticsController createStatisticsController(MockDataProvider dataProvider) {
        MockConnection connection = new MockConnection(dataProvider);

        db = DSL.using(connection, SQLDialect.POSTGRES);

        StatisticsRepository statisticsRepository = new JooqRepository(db);

        StatisticsService statisticsService = new StatisticsService(statisticsRepository);
        StatisticsConverter statisticsConverter = new StatisticsConverter();

        return new StatisticsController(statisticsService, statisticsConverter);
    }

    @Test
    @Timeout(value = 5)
    void when_jooq_queries_succeed_result_is_returned() throws ExecutionException, InterruptedException {
        // Given a successful DB connection and a StatisticsController
        StatisticsController statisticsController = this.createStatisticsController(this::successfulProvider);

        // When getting the statistics
        ResponseEntity<DetectorStatisticsResponse> response = statisticsController.stats().get();

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
    }

    @Test
    @Timeout(value = 5)
    void when_winners_query_fails_propagate_an_error() throws ExecutionException, InterruptedException {
        // Given a failing winners DB connection and a StatisticsController
        StatisticsController statisticsController = this.createStatisticsController(this::failingWinner);

        // When getting the statistics
        ResponseEntity<DetectorStatisticsResponse> response = statisticsController.stats().get();

        // Then request fails with empty body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Timeout(value = 5)
    void when_pieces_query_fails_propagate_an_error() throws ExecutionException, InterruptedException {
        // Given a failing pieces DB connection and a StatisticsController
        StatisticsController statisticsController = this.createStatisticsController(this::failingPieces);

        // When getting the statistics
        ResponseEntity<DetectorStatisticsResponse> response = statisticsController.stats().get();

        // Then request fails with empty body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Timeout(value = 5)
    void record_winner_success_should_return_nothing() {
        // Given a successful winners repo
        MockConnection connection = new MockConnection(this::successfulProvider);

        db = DSL.using(connection, SQLDialect.POSTGRES);

        StatisticsRepository statisticsRepository = new JooqRepository(db);

        // When recording a winner it should not throw
        assertDoesNotThrow(() -> statisticsRepository.recordWinner(Color.WHITE));
    }

    @Test
    @Timeout(value = 5)
    void record_pieces_success_should_return_nothing() {
        // Given a successful pieces repo
        MockConnection connection = new MockConnection(this::successfulProvider);

        db = DSL.using(connection, SQLDialect.POSTGRES);

        StatisticsRepository statisticsRepository = new JooqRepository(db);

        // When recording a piece it should not throw
        assertDoesNotThrow(() -> statisticsRepository.recordCheckmatePiece(Type.QUEEN));
    }

    private MockResult[] successfulProvider(MockExecuteContext ctx) {
        if (ctx.sql().contains("pieces_query_builder")) {
            return new MockResult[]{
                    newPieceResult(
                            db,
                            Pair.of("queen", 2),
                            Pair.of("knight", 1),
                            Pair.of("rook", 1),
                            Pair.of("pawn", 0),
                            Pair.of("king", 0),
                            Pair.of("bishop", 0)
                    )
            };
        }

        if (ctx.sql().contains("winners_query_builder")) {
            return new MockResult[]{
                    newWinnerResult(
                            db,
                            Pair.of("white", 2),
                            Pair.of("black", 1),
                            Pair.of("none", 1)
                    )
            };
        }

        return new MockResult[]{
                new MockResult(new SQLException())
        };
    }

    private MockResult[] failingPieces(MockExecuteContext ctx) {
        if (ctx.sql().contains("pieces_query_builder")) {
            return new MockResult[]{
                    new MockResult(new SQLException())
            };
        }

        if (ctx.sql().contains("winners_query_builder")) {
            return new MockResult[]{
                    newWinnerResult(
                            db,
                            Pair.of("white", 2),
                            Pair.of("black", 1),
                            Pair.of("none", 1)
                    )
            };
        }

        return new MockResult[]{
                new MockResult(new SQLException())
        };
    }

    private MockResult[] failingWinner(MockExecuteContext ctx) {
        if (ctx.sql().contains("pieces_query_builder")) {
            return new MockResult[]{
                    newPieceResult(
                            db,
                            Pair.of("queen", 2),
                            Pair.of("knight", 1),
                            Pair.of("rook", 1),
                            Pair.of("pawn", 0),
                            Pair.of("king", 0),
                            Pair.of("bishop", 0)
                    )
            };
        }

        if (ctx.sql().contains("winners_query_builder")) {
            return new MockResult[]{
                    new MockResult(new SQLException())
            };
        }

        return new MockResult[]{
                new MockResult(new SQLException())
        };
    }
}