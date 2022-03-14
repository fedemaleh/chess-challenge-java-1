package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class King implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {

        return MovementUtils.adjacentSquares(piece.position())
                .filter(move -> board.canOccupy(piece, move))
                .filter(move -> !this.isSquareThreatenedByRivalPiece(board, piece, move))
                .filter(move -> !this.isAdjacentToOtherKing(board, piece, move))
                .collect(Collectors.toList());
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        if (!this.moves(board, piece).contains(square)) {
            throw new IllegalMovementException(piece, square);
        }

        return Lists.newArrayList(piece.position(), square);
    }

    @Override
    public String name() {
        return "king";
    }

    private boolean isSquareThreatenedByRivalPiece(Board board, Piece piece, Square move) {
        return board.isThreatened(move, piece);
    }

    private boolean isAdjacentToOtherKing(Board board, Piece piece, Square move) {
        return !board.validKingMove(piece, move);
    }
}
