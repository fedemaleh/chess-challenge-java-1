package com.chess_challenge.java_1.detector;

import com.chess_challenge.java_1.converters.BoardConverter;
import com.chess_challenge.java_1.converters.BoardStatusConverter;
import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.model.Board;
import com.chess_challenge.java_1.model.BoardStatus;
import com.chess_challenge.java_1.response.BoardStatusResponse;
import com.chess_challenge.java_1.response.CheckmateDetectorResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckmateDetector {
    private final BoardConverter boardConverter;
    private final BoardStatusConverter boardStatusConverter;

    @Autowired
    public CheckmateDetector(BoardConverter boardConverter, BoardStatusConverter boardStatusConverter) {
        this.boardConverter = boardConverter;
        this.boardStatusConverter = boardStatusConverter;
    }

    @RequestMapping(path = "/checkmate/detector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detectCheckmate(@RequestBody BoardDTO inputBoard) {
        Board board = boardConverter.convertBoard(inputBoard);

        BoardStatus status = board.analyse();

        BoardStatusResponse boardStatusResponse = boardStatusConverter.convertBoardStatus(status);

        CheckmateDetectorResultResponse response = new CheckmateDetectorResultResponse(boardStatusResponse);

        return ResponseEntity.ok(response);
    }
}
