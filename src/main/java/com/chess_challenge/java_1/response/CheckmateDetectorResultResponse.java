package com.chess_challenge.java_1.response;

import java.util.List;

public class CheckmateDetectorResultResponse {
    private final BoardStatusResponse status;
    private final String winner;
    private final List<String> piece;

    public CheckmateDetectorResultResponse(BoardStatusResponse status, String winner, List<String> piece) {
        this.status = status;
        this.winner = winner;
        this.piece = piece;
    }

    public BoardStatusResponse getStatus() {
        return status;
    }

    public List<String> getPiece() {
        return piece;
    }

    public String getWinner() {
        return winner;
    }
}
