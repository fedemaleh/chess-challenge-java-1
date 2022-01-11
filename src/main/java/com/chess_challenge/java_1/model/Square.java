package com.chess_challenge.java_1.model;

import java.util.Objects;

public class Square {
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 8;

    private static final char MIN_COL = 'a';
    private static final char MAX_COL = 'h';

    private final char column;
    private final int row;

    public Square(char column, int row) throws IllegalSquareException {
        if (column < MIN_COL || column > MAX_COL) {
            throw new IllegalSquareException(column, row);
        }

        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalSquareException(column, row);
        }

        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return String.format("(%c, %d)", column, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return column == square.column &&
                row == square.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
