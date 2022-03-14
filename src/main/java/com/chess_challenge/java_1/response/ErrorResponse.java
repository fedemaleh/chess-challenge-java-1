package com.chess_challenge.java_1.response;

import java.util.List;

public class ErrorResponse {
    private final List<String> errors;

    public ErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
