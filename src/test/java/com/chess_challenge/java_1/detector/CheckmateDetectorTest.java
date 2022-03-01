package com.chess_challenge.java_1.detector;

import com.chess_challenge.java_1.converters.BoardConverter;
import com.chess_challenge.java_1.converters.BoardStatusConverter;
import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.response.BoardStatusResponse;
import com.chess_challenge.java_1.response.CheckmateDetectorResultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CheckmateDetectorTest {
    private CheckmateDetector checkmateDetector;

    @BeforeEach
    public void setup() {
        BoardConverter boardConverter = new BoardConverter();
        BoardStatusConverter boardStatusConverter = new BoardStatusConverter();

        this.checkmateDetector = new CheckmateDetector(boardConverter, boardStatusConverter);
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
    }
}