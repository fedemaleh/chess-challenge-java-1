package com.chess_challenge.java_1.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BoardStatusResponse {
    CHECKMATE, NO_CHECKMATE;

    @JsonValue
    public String jsonValue() {
        return this.name().toLowerCase();
    }
}
