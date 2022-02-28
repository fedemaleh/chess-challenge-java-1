package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class King implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        // Adds moves in all direction 1 square
        moves.addAll(this.backwardMoves(piece));
        moves.addAll(this.horizontalMoves(piece));
        moves.addAll(this.forwardMoves(piece));

        // Removes current position as it's not a movement
        moves.remove(piece.position());

        return moves.stream()
                .filter(move -> !this.isSquareOccupiedByOwnPiece(board, piece, move))
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

    private List<Square> backwardMoves(Piece piece) {
        return movesInRow(piece, piece.position().getRow() - 1);
    }

    private List<Square> horizontalMoves(Piece piece) {
        return movesInRow(piece, piece.position().getRow());
    }

    private List<Square> forwardMoves(Piece piece) {
        return movesInRow(piece, piece.position().getRow() + 1);
    }

    private List<Square> movesInRow(Piece piece, int row) {
        if (row < 1 || row > 8) {
            return Collections.emptyList();
        }

        char currentColumn = piece.position().getColumn();
        char leftColumn = (char) (piece.position().getColumn() - 1);
        char rightColumn = (char) (piece.position().getColumn() + 1);

        List<Square> moves = new ArrayList<>();

        if (leftColumn >= 'a') {
            moves.add(new Square(leftColumn, row));
        }

        moves.add(new Square(currentColumn, row));

        if (rightColumn <= 'h') {
            moves.add(new Square(rightColumn, row));
        }

        return moves;
    }

    private boolean isSquareOccupiedByOwnPiece(Board board, Piece piece, Square move) {
        return board.pieceAt(move).filter(p -> p.color() == piece.color()).isPresent();
    }

    private boolean isSquareThreatenedByRivalPiece(Board board, Piece piece, Square move) {
        return board.isThreatened(move, piece);
    }

    private boolean isAdjacentToOtherKing(Board board, Piece piece, Square move) {
        return !board.validKingMove(piece, move);
    }
}
