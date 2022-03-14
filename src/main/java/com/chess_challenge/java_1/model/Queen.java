package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.List;

public class Queen implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> bishopMoves = new Bishop().moves(board, piece);
        List<Square> rookMoves = new Rook().moves(board, piece);

        List<Square> moves = new ArrayList<>(bishopMoves);
        moves.addAll(rookMoves);
        return moves;
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        Piece bishop = new Piece(new Bishop(), piece.color(), piece.position());

        if (bishop.attacks(board, square)) {
            return bishop.pathTo(board, square);
        }

        Piece rook = new Piece(new Rook(), piece.color(), piece.position());

        if (rook.attacks(board, square)) {
            return rook.pathTo(board, square);
        }

        throw new IllegalMovementException(piece, square);
    }

    @Override
    public Type type() {
        return Type.QUEEN;
    }
}
