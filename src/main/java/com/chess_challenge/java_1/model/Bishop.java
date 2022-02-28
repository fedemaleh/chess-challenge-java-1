package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bishop implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.backwardMoves(board, piece));
        moves.addAll(this.forwardMoves(board, piece));

        return moves;
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        List<Square> path = this.moves(board, piece);

        if (!path.contains(square)) {
            throw new IllegalMovementException(piece, square);
        }

        path.add(0, piece.position());

        return path.stream()
                .filter(move -> move.sharesDiagonal(square) && move.distanceTo(square) <= piece.position().distanceTo(square))
                .collect(Collectors.toList());
    }

    private List<Square> backwardMoves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        // back and left moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getColumn() >= Square.MIN_COL && square.getRow() >= Square.MIN_ROW,
                        (square) -> square.leftSquare().flatMap(Square::backwardSquare)
                )
        );

        // back and right moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getColumn() <= Square.MAX_COL && square.getRow() >= Square.MIN_ROW,
                        (square) -> square.rightSquare().flatMap(Square::backwardSquare)
                )
        );

        return moves;
    }

    private List<Square> forwardMoves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        // forward and left moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getColumn() >= Square.MIN_COL && square.getRow() <= Square.MAX_ROW,
                        (square) -> square.leftSquare().flatMap(Square::forwardSquare)
                )
        );

        // forward and right moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        piece,
                        (square) -> square.getColumn() <= Square.MAX_COL && square.getRow() <= Square.MAX_ROW,
                        (square) -> square.rightSquare().flatMap(Square::forwardSquare)
                )
        );

        return moves;
    }
}
