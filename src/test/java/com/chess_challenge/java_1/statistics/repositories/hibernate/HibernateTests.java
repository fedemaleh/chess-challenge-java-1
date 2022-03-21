package com.chess_challenge.java_1.statistics.repositories.hibernate;

import com.chess_challenge.java_1.converters.StatisticsConverter;
import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.response.DetectorStatisticsResponse;
import com.chess_challenge.java_1.statistics.StatisticsController;
import com.chess_challenge.java_1.statistics.StatisticsService;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HibernateTests {
    private StatisticsController statisticsController;
    private StatisticsRepository statisticsRepository;
    private HibernateWinnersRepo winnersRepo;
    private HibernatePiecesRepo piecesRepo;

    @BeforeEach
    public void setup() {
        this.winnersRepo = mock(HibernateWinnersRepo.class);
        this.piecesRepo = mock(HibernatePiecesRepo.class);

        this.statisticsRepository = new HibernateRepository(winnersRepo, piecesRepo);
        StatisticsConverter statisticsConverter = new StatisticsConverter();

        StatisticsService statisticsService = new StatisticsService(statisticsRepository);

        this.statisticsController = new StatisticsController(statisticsService, statisticsConverter);
    }

    @Test
    @Timeout(value = 5)
    void when_hibernate_queries_succeed_result_is_returned() throws ExecutionException, InterruptedException {
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
    }

    @Test
    @Timeout(value = 5)
    void record_winner_success_should_return_nothing(){
        // Given a successful winners repo
        this.mockWinnersSuccess();

        // When recording a winner it should not throw
        assertDoesNotThrow(() -> statisticsRepository.recordWinner(Color.WHITE));

        verify(winnersRepo, times(1)).recordWinner(any());
    }

    @Test
    @Timeout(value = 5)
    void record_pieces_success_should_return_nothing(){
        // Given a successful pieces repo
        this.mockPiecesSuccess();

        // When recording a piece it should not throw
        assertDoesNotThrow(() -> statisticsRepository.recordCheckmatePiece(Type.QUEEN));

        verify(piecesRepo, times(1)).recordPiece(any());
    }

    private void mockWinnersSuccess() {
        when(winnersRepo.winners())
                .thenReturn(
                        CompletableFuture.completedFuture(
                                Lists.newArrayList(
                                        new HibernateWinner("white"),
                                        new HibernateWinner("black"),
                                        new HibernateWinner("white"),
                                        new HibernateWinner("none")
                                )
                        )
                );

        doNothing().when(winnersRepo).recordWinner(any());
    }

    private void mockPiecesSuccess() {
        when(piecesRepo.pieces())
                .thenReturn(
                        CompletableFuture.completedFuture(
                                Lists.newArrayList(
                                        new HibernatePiece("queen"),
                                        new HibernatePiece("knight"),
                                        new HibernatePiece("rook"),
                                        new HibernatePiece("queen")
                                )
                        )
                );

        doNothing().when(piecesRepo).recordPiece(any());
    }

    private void mockWinnersFailure() {
        when(winnersRepo.winners()).thenReturn(CompletableFuture.failedFuture(new Exception()));
    }

    private void mockPiecesFailure() {
        when(piecesRepo.pieces()).thenReturn(CompletableFuture.failedFuture(new Exception()));
    }
}