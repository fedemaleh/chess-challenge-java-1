package com.chess_challenge.java_1.model;

public enum Color {
    WHITE, BLACK;

    public int initialPawnRow() {
        return this == WHITE ? 2 : 7;
    }

    public int finalPawnRow() {
        return this == WHITE ? 8 : 1;
    }
}
