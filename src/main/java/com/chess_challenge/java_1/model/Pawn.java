package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;

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
    public List<Square> moves() {
        List<Square> squares = new ArrayList<>();

        char column = this.position().getColumn();
        int row = this.position().getRow();

        if (row != this.color().finalPawnRow()) {
            squares.add(new Square(column, this.nextRow(1)));
        }

        if (row == this.color().initialPawnRow()) {
            squares.add(new Square(column, this.nextRow(2)));
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
    public List<Square> attacks() {
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
    public Pawn moveTo(Square square) throws IllegalMovementException {
        return null;
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
