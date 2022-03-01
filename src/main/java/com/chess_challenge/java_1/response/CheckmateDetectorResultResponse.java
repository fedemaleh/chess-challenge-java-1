package com.chess_challenge.java_1.response;

public class CheckmateDetectorResultResponse {
    private final BoardStatusResponse status;

    public CheckmateDetectorResultResponse(BoardStatusResponse status) {
        this.status = status;
    }

    public BoardStatusResponse getStatus() {
        return status;
    }
}
