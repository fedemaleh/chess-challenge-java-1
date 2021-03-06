package com.chess_challenge.java_1.model;

import java.util.Objects;
import java.util.Optional;

public class Square {
    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 8;

    public static final char MIN_COL = 'a';
    public static final char MAX_COL = 'h';

    private final char column;
    private final int row;

    public Square(char column, int row) throws IllegalSquareException {
        if (!validSquare(column, row)) {
            throw new IllegalSquareException(column, row);
        }

        this.column = column;
        this.row = row;
    }

    public Square(int column, int row) throws IllegalSquareException {
        this((char) column, row);
    }

    public static Optional<Square> squareOrEmpty(char column, int row) {
        return Optional.of(validSquare(column, row))
                .filter(valid -> valid)
                .map(valid -> new Square(column, row));
    }

    public static Optional<Square> squareOrEmpty(int column, int row) {
        return Optional.of(validSquare((char) column, row))
                .filter(valid -> valid)
                .map(valid -> new Square(column, row));
    }

    public static boolean validSquare(char column, int row) {
        return column >= MIN_COL && column <= MAX_COL &&
                row >= MIN_ROW && row <= MAX_ROW;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public double distanceTo(Square square) {
        double deltaCol = this.getColumn() - square.getColumn();
        double deltaRow = this.getRow() - square.getRow();

        return Math.sqrt(deltaCol * deltaCol + deltaRow * deltaRow);
    }

    public boolean isAligned(Square square) {
        return this.getRow() == square.getRow() || this.getColumn() == square.getColumn();
    }

    public boolean sharesDiagonal(Square square) {
        return this.getRow() - square.getRow() == this.getColumn() - square.getColumn();
    }

    public Optional<Square> leftSquare() {
        return this.leftSquare(1);
    }

    public Optional<Square> rightSquare() {
        return this.rightSquare(1);
    }

    public Optional<Square> backwardSquare() {
        return this.backwardSquare(1);
    }

    public Optional<Square> forwardSquare() {
        return this.forwardSquare(1);
    }

    public Optional<Square> leftSquare(int squares) {
        return Square.squareOrEmpty(this.getColumn() - squares, this.getRow());
    }

    public Optional<Square> rightSquare(int squares) {
        return Square.squareOrEmpty(this.getColumn() + squares, this.getRow());
    }

    public Optional<Square> backwardSquare(int squares) {
        return Square.squareOrEmpty(this.getColumn(), this.getRow() - squares);
    }

    public Optional<Square> forwardSquare(int squares) {
        return Square.squareOrEmpty(this.getColumn(), this.getRow() + squares);
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
