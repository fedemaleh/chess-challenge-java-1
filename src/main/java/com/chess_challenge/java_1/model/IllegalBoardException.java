package com.chess_challenge.java_1.model;

/**
 * Base type for board-related exceptions.
 */
public abstract class IllegalBoardException extends RuntimeException {
    public IllegalBoardException(String message) {
        super(message);
    }
}
