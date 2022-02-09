package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pawn implements Piece {
    private final Color color;
    private final Square square;

    public Pawn(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves(Board board) {
        List<Square> squares = new ArrayList<>();

        char column = this.position().getColumn();
        int row = this.position().getRow();

        if (row != this.color().finalPawnRow()) {
            Square move = new Square(column, this.nextRow(1));

            if (board.pieceAt(move).isPresent()) {
                return Collections.emptyList();
            }

            squares.add(move);
        }

        if (row == this.color().initialPawnRow()) {
            Square move = new Square(column, this.nextRow(2));

            if (!board.pieceAt(move).isPresent()) {
                squares.add(move);
            }
        }

        return squares;
    }

    private int nextRow(int squares) {
        if (this.color() == Color.WHITE) {
            return this.position().getRow() + squares;
        }

        return this.position().getRow() - squares;
    }

    @Override
    public List<Square> attacks(Board board) {
        if (this.position().getRow() == this.color().finalPawnRow()) {
            return Collections.emptyList();
        }

        List<Square> attacks = new ArrayList<>();

        if (this.position().getColumn() != 'a') {
            attacks.add(new Square((char) (this.position().getColumn() - 1), this.nextRow(1)));
        }

        if (this.position().getColumn() != 'h') {
            attacks.add(new Square((char) (this.position().getColumn() + 1), this.nextRow(1)));
        }

        return attacks;
    }

    @Override
    public Square position() {
        return this.square;
    }

    @Override
    public Pawn moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Pawn(this.color(), square);
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public Type type() {
        return Type.PAWN;
    }
}
