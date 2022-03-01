package com.chess_challenge.java_1.converters;

import com.chess_challenge.java_1.model.BoardStatus;
import com.chess_challenge.java_1.response.BoardStatusResponse;
import org.springframework.stereotype.Service;

@Service
public class BoardStatusConverter {
    public BoardStatusResponse convertBoardStatus(BoardStatus boardStatus) {
        return switch (boardStatus) {
            case CHECKMATE -> BoardStatusResponse.CHECKMATE;
            case NO_CHECKMATE -> BoardStatusResponse.NO_CHECKMATE;
        };
    }

}
