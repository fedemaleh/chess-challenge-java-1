package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.List;

public class Queen implements Piece {
    private final Color color;
    private final Square square;

    public Queen(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves(Board board) {
        List<Square> bishopMoves = new Bishop(this.color(), this.position()).moves(board);
        List<Square> rookMoves = new Rook(this.color(), this.position()).moves(board);

        List<Square> moves = new ArrayList<>(bishopMoves);
        moves.addAll(rookMoves);
        return moves;
    }

    @Override
    public List<Square> attacks(Board board) {
        return this.moves(board);
    }

    @Override
    public boolean attacks(Board board, Square square) {
        return this.attacks(board).contains(square);
    }

    @Override
    public Square position() {
        return this.square;
    }

    @Override
    public Queen moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Queen(this.color(), square);
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public Type type() {
        return Type.QUEEN;
    }
}
