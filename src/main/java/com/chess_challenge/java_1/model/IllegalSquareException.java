package com.chess_challenge.java_1.model;

public class IllegalSquareException extends Exception {
    public IllegalSquareException(char col, int row) {
        super(String.format("Square (%c, %d) is invalid. Valid columns are [a, b, c, d, e, f, g, h] and valid rows are [1, 2, 3, 4 ,5 , 6, 7, 8]",
                col, row));
    }
}
