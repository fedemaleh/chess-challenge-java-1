package com.chess_challenge.java_1.model;

public class IllegalMovementException extends RuntimeException {
    public IllegalMovementException(Piece piece, Square square) {
        super(String.format("Cannot move a %s %s from %s to %s", piece.color(), piece.type(), piece.position().toString(), square.toString()));
    }
}
