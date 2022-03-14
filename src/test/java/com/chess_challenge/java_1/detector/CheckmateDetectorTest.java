package com.chess_challenge.java_1.detector;

import com.chess_challenge.java_1.converters.BoardConverter;
import com.chess_challenge.java_1.converters.BoardStatusConverter;
import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.dto.ColorDTO;
import com.chess_challenge.java_1.model.*;
import com.chess_challenge.java_1.response.BoardStatusResponse;
import com.chess_challenge.java_1.response.CheckmateDetectorResultResponse;
import com.chess_challenge.java_1.response.ErrorResponse;
import com.chess_challenge.java_1.statistics.repositories.InMemoryStatisticsRepository;
import com.chess_challenge.java_1.statistics.StatisticsService;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import com.chess_challenge.java_1.validators.BoardValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckmateDetectorTest {
    private CheckmateDetector checkmateDetector;
    private StatisticsService statisticsService;

    @BeforeEach
    public void setup() {
        BoardValidator boardValidator = new BoardValidator();
        BoardConverter boardConverter = new BoardConverter(boardValidator);
        BoardStatusConverter boardStatusConverter = new BoardStatusConverter();

        StatisticsRepository statisticsRepository = new InMemoryStatisticsRepository();

        this.statisticsService = new StatisticsService(statisticsRepository);
        this.checkmateDetector = new CheckmateDetector(boardConverter, boardStatusConverter, statisticsService);
    }

    @Test
    @Timeout(value = 20)
    void checkmate_case_returns_a_checkmate_response() throws IOException {
        File example = new File(String.format("resources/checkmate-examples/checkmate_example_%d.json", 1));

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        ResponseEntity<?> response = checkmateDetector.detectCheckmate(board);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        CheckmateDetectorResultResponse result = (CheckmateDetectorResultResponse) response.getBody();

        assertEquals(BoardStatusResponse.CHECKMATE, result.getStatus());
        assertEquals(Color.WHITE.name().toLowerCase(), result.getWinner());
        assertEquals(Collections.singletonList("rook"), result.getPiece());
    }

    @Test
    @Timeout(value = 20)
    void check_case_returns_a_check_response() throws IOException {
        File example = new File(String.format("resources/check-examples/check_example_%d.json", 1));

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        ResponseEntity<?> response = checkmateDetector.detectCheckmate(board);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        CheckmateDetectorResultResponse result = (CheckmateDetectorResultResponse) response.getBody();

        assertEquals(BoardStatusResponse.NO_CHECKMATE, result.getStatus());
        assertEquals("no_winner", result.getWinner());
        assertEquals(Collections.emptyList(), result.getPiece());
    }

    @Test
    @Timeout(value = 20)
    void invalid_squares_case_returns_an_error_response_detailing_wrong_squares() throws IOException {
        File example = new File("resources/invalid_boards/invalid_squares.json");

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        ResponseEntity<?> response = checkmateDetector.detectCheckmate(board);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse errors = (ErrorResponse) response.getBody();

        List<String> expectedErrors = Stream.of(
                new IllegalSquareException('a', 18),
                new IllegalSquareException('l', 8)
        ).map(Exception::getMessage)
                .collect(Collectors.toList());

        assertEquals(expectedErrors, errors.getErrors());
    }

    @Test
    @Timeout(value = 20)
    void missing_white_king_case_returns_an_error_response_detailing_missing_king() throws IOException {
        File example = new File("resources/invalid_boards/missing_white_king.json");

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        ResponseEntity<?> response = checkmateDetector.detectCheckmate(board);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse errors = (ErrorResponse) response.getBody();

        List<String> expectedErrors = Stream.of(
                new MissingKingException(ColorDTO.WHITE)
        ).map(Exception::getMessage)
                .collect(Collectors.toList());

        assertEquals(expectedErrors, errors.getErrors());
    }

    @Test
    @Timeout(value = 20)
    void missing_black_king_case_returns_an_error_response_detailing_missing_king() throws IOException {
        File example = new File("resources/invalid_boards/missing_white_king.json");

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        ResponseEntity<?> response = checkmateDetector.detectCheckmate(board);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse errors = (ErrorResponse) response.getBody();

        List<String> expectedErrors = Stream.of(
                new MissingKingException(ColorDTO.WHITE)
        ).map(Exception::getMessage)
                .collect(Collectors.toList());

        assertEquals(expectedErrors, errors.getErrors());
    }

    @Test
    @Timeout(value = 20)
    void multiple_same_color_kings_case_returns_an_error_response_detailing_repeated_king() throws IOException {
        File example = new File("resources/invalid_boards/multiple_kings.json");

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        ResponseEntity<?> response = checkmateDetector.detectCheckmate(board);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse errors = (ErrorResponse) response.getBody();

        List<String> expectedErrors = Stream.of(
                new MultipleKingsException(ColorDTO.WHITE),
                new MultipleKingsException(ColorDTO.BLACK)
        ).map(Exception::getMessage)
                .collect(Collectors.toList());

        assertEquals(expectedErrors, errors.getErrors());
    }

    @Test
    @Timeout(value = 5)
    void no_checkmate_case_should_update_statistics() throws IOException {
        // Given an empty statistics service

        DetectorStatistics initialStats = statisticsService.getStatistics();

        // verify stats are empty
        Map<Color, Integer> initialWinners = initialStats.getWinners();

        initialWinners.forEach((color, checkmates) -> assertEquals(0, checkmates));

        Map<Type, Integer> initialCheckmatePieces = initialStats.getCheckmatePieces();

        initialCheckmatePieces.forEach((piece, checkmates) -> assertEquals(0, checkmates));

        // when the board is analysed
        File example = new File(String.format("resources/check-examples/check_example_%d.json", 1));

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        checkmateDetector.detectCheckmate(board);

        // then stats are updated
        DetectorStatistics currentStats = statisticsService.getStatistics();

        Map<Color, Integer> currentWinners = currentStats.getWinners();

        assertEquals(1, currentWinners.get(Color.NO_COLOR));

        initialWinners
                .entrySet()
                .stream()
                .filter(winner -> winner.getKey() != Color.NO_COLOR)
                .forEach((winner) -> assertEquals(0, winner.getValue()));

        Map<Type, Integer> currentCheckmatePieces = currentStats.getCheckmatePieces();

        currentCheckmatePieces.forEach((piece, checkmates) -> assertEquals(0, checkmates));
    }

    @Test
    @Timeout(value = 5)
    void checkmate_case_should_update_statistics() throws IOException {
        // Given an empty statistics service

        DetectorStatistics initialStats = statisticsService.getStatistics();

        // verify stats are empty
        Map<Color, Integer> initialWinners = initialStats.getWinners();

        initialWinners.forEach((color, checkmates) -> assertEquals(0, checkmates));

        Map<Type, Integer> initialCheckmatePieces = initialStats.getCheckmatePieces();

        initialCheckmatePieces.forEach((piece, checkmates) -> assertEquals(0, checkmates));

        // when the board is analysed
        File example = new File(String.format("resources/checkmate-examples/checkmate_example_%d.json", 1));

        BoardDTO board = new ObjectMapper().readValue(example, BoardDTO.class);

        checkmateDetector.detectCheckmate(board);

        // then stats are updated
        DetectorStatistics currentStats = statisticsService.getStatistics();

        Map<Color, Integer> currentWinners = currentStats.getWinners();

        assertEquals(1, currentWinners.get(Color.WHITE));

        initialWinners
                .entrySet()
                .stream()
                .filter(winner -> winner.getKey() != Color.WHITE)
                .forEach((winner) -> assertEquals(0, winner.getValue()));

        Map<Type, Integer> currentCheckmatePieces = currentStats.getCheckmatePieces();

        assertEquals(1, currentCheckmatePieces.get(Type.ROOK));

        currentCheckmatePieces.entrySet()
                .stream()
                .filter(winner -> !winner.getKey().equals(Type.ROOK))
                .forEach((winner) -> assertEquals(0, winner.getValue()));
    }
}