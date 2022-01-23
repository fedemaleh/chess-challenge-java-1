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
    public List<Square> moves() {
        List<Square> bishopMoves = new Bishop(this.color(), this.position()).moves();
        List<Square> rookMoves = new Rook(this.color(), this.position()).moves();

        List<Square> moves = new ArrayList<>(bishopMoves);
        moves.addAll(rookMoves);
        return moves;
    }

    @Override
    public List<Square> attacks() {
        return null;
    }

    @Override
    public Square position() {
        return this.square;
    }

    @Override
    public Piece moveTo(Square square) throws IllegalMovementException {
        return null;
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
