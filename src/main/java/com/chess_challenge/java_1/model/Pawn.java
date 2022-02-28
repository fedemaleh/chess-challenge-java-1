package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pawn implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> squares = new ArrayList<>();

        char column = piece.position().getColumn();
        int row = piece.position().getRow();

        if (row != piece.color().finalPawnRow()) {
            Square move = new Square(column, this.nextRow(piece, 1));

            if (board.pieceAt(move).isPresent()) {
                return Collections.emptyList();
            }

            squares.add(move);
        }

        if (row == piece.color().initialPawnRow()) {
            Square move = new Square(column, this.nextRow(piece, 2));

            if (!board.pieceAt(move).isPresent()) {
                squares.add(move);
            }
        }

        return squares;
    }

    @Override
    public List<Square> attacks(Board board, Piece piece) {
        if (piece.position().getRow() == piece.color().finalPawnRow()) {
            return Collections.emptyList();
        }

        List<Square> attacks = new ArrayList<>();

        if (piece.position().getColumn() != 'a') {
            attacks.add(new Square((char) (piece.position().getColumn() - 1), this.nextRow(piece, 1)));
        }

        if (piece.position().getColumn() != 'h') {
            attacks.add(new Square((char) (piece.position().getColumn() + 1), this.nextRow(piece, 1)));
        }

        return attacks;
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        if (!this.moves(board, piece).contains(square) && !this.attacks(board, piece).contains(square)) {
            throw new IllegalMovementException(piece, square);
        }

        return Lists.newArrayList(piece.position(), square);
    }

    private int nextRow(Piece piece, int squares) {
        if (piece.color() == Color.WHITE) {
            return piece.position().getRow() + squares;
        }

        return piece.position().getRow() - squares;
    }
}
