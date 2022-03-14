package com.chess_challenge.java_1.converters;

import com.chess_challenge.java_1.model.BoardStatus;
import com.chess_challenge.java_1.model.MovementStrategy;
import com.chess_challenge.java_1.model.Piece;
import com.chess_challenge.java_1.response.BoardStatusResponse;
import com.chess_challenge.java_1.response.CheckmateDetectorResultResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class BoardStatusConverter {
    public CheckmateDetectorResultResponse convertBoardStatus(BoardStatus boardStatus) {
        return boardStatus.winner()
                .map(winner ->
                        new CheckmateDetectorResultResponse(
                                BoardStatusResponse.CHECKMATE,
                                winner.name().toLowerCase(),
                                boardStatus.checkmatePieces()
                                        .stream()
                                        .map(Piece::type)
                                        .map(movementStrategy -> movementStrategy.type().name().toLowerCase())
                                        .collect(Collectors.toList())
                        )
                )
                .orElse(new CheckmateDetectorResultResponse(BoardStatusResponse.NO_CHECKMATE, "no_winner", Collections.emptyList()));
    }

}
