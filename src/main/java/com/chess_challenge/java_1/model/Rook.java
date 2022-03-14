package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MoveDirection;
import com.chess_challenge.java_1.utils.MovementUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rook implements MovementStrategy {
    private final MoveDirection left = Square::leftSquare;
    private final MoveDirection right = Square::rightSquare;
    private final MoveDirection back = Square::backwardSquare;
    private final MoveDirection front = Square::forwardSquare;

    @Override
    public List<Square> moves(Board board, Piece piece) {
        return Stream.of(left, right, back, front)
                .map(moveDirection -> MovementUtils.generateMovesUntilCondition(board, piece, moveDirection))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
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

    @Override
    public Type type() {
        return Type.ROOK;
    }
}
