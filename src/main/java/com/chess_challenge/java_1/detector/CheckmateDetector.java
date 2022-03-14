package com.chess_challenge.java_1.detector;

import com.chess_challenge.java_1.converters.BoardConverter;
import com.chess_challenge.java_1.converters.BoardStatusConverter;
import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.model.Board;
import com.chess_challenge.java_1.model.BoardStatus;
import com.chess_challenge.java_1.model.IllegalBoardException;
import com.chess_challenge.java_1.response.CheckmateDetectorResultResponse;
import com.chess_challenge.java_1.response.ErrorResponse;
import com.chess_challenge.java_1.statistics.StatisticsService;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CheckmateDetector {
    private final BoardConverter boardConverter;
    private final BoardStatusConverter boardStatusConverter;
    private final StatisticsService statisticsService;

    @Autowired
    public CheckmateDetector(BoardConverter boardConverter, BoardStatusConverter boardStatusConverter, StatisticsService statisticsService) {
        this.boardConverter = boardConverter;
        this.boardStatusConverter = boardStatusConverter;
        this.statisticsService = statisticsService;
    }

    @RequestMapping(path = "/checkmate/detector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detectCheckmate(@RequestBody BoardDTO inputBoard) {
        Either<Board, List<IllegalBoardException>> eitherBoard = boardConverter.convertBoard(inputBoard);

        if (eitherBoard.isRight()) {
            List<String> errors = eitherBoard.get().stream().map(Exception::getMessage).collect(Collectors.toList());

            return ResponseEntity.badRequest().body(new ErrorResponse(errors));
        }

        Board board = eitherBoard.getLeft();

        BoardStatus status = board.analyse();

        this.statisticsService.recordResults(status);

        CheckmateDetectorResultResponse response = boardStatusConverter.convertBoardStatus(status);

        return ResponseEntity.ok(response);
    }
}
