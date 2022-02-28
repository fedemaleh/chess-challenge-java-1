package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MoveDirection;
import com.chess_challenge.java_1.utils.MovementUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bishop implements MovementStrategy {
    private final MoveDirection backAndLeft = (square) -> square.leftSquare().flatMap(Square::backwardSquare);
    private final MoveDirection backAndRight = (square) -> square.rightSquare().flatMap(Square::backwardSquare);
    private final MoveDirection frontAndLeft = (square) -> square.leftSquare().flatMap(Square::forwardSquare);
    private final MoveDirection frontAndRight = (square) -> square.rightSquare().flatMap(Square::forwardSquare);

    @Override
    public List<Square> moves(Board board, Piece piece) {
        return Stream.of(backAndLeft, backAndRight, frontAndLeft, frontAndRight)
                .map(moveDirection -> MovementUtils.generateMovesUntilCondition(board, piece, moveDirection))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
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
}
