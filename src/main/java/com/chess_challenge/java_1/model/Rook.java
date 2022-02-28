package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rook implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.horizontalMoves(board, piece));
        moves.addAll(this.verticalMoves(board, piece));

        return moves;
    }

    @Override
    public List<Square> attacks(Board board, Piece piece) {
        return this.moves(board, piece);
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        List<Square> path = this.moves(board, piece);

        if (!path.contains(square)) {
            throw new IllegalMovementException(piece, square);
        }

        path.add(0, piece.position());

        return path.stream()
                .filter(move -> move.isAligned(square) && move.distanceTo(square) <= piece.position().distanceTo(square))
                .collect(Collectors.toList());
    }

    private List<Square> horizontalMoves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        // Moves to left
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getColumn() >= Square.MIN_COL,
                        Square::leftSquare
                )
        );

        // Moves to right
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getColumn() <= Square.MAX_COL,
                        Square::rightSquare
                )
        );

        return moves;
    }

    private List<Square> verticalMoves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        // Moves to back
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getRow() >= Square.MIN_ROW,
                        Square::backwardSquare
                )
        );

        // Moves to front
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getRow() <= Square.MAX_ROW,
                        Square::forwardSquare
                )
        );

        return moves;
    }
}
