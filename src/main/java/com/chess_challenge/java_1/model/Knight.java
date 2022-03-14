package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Knight implements MovementStrategy {

    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.verticalMoves(board, piece));

        moves.addAll(this.horizontalMoves(board, piece));

        return moves;
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
        return "knight";
    }

    private List<Square> verticalMoves(Board board, Piece piece) {
        return this.generateMoves(board, piece, 2, 1);
    }

    private List<Square> horizontalMoves(Board board, Piece piece) {
        return this.generateMoves(board, piece, 1, 2);
    }

    private List<Square> generateMoves(Board board, Piece piece, int verticalSquares, int horizontalSquares) {
        return MovementUtils.validSquares(piece.position().backwardSquare(verticalSquares), piece.position().forwardSquare(verticalSquares))
                .flatMap(square -> MovementUtils.validSquares(
                        square.leftSquare(horizontalSquares),
                        square.rightSquare(horizontalSquares))
                )
                .filter(square -> board.canOccupy(piece, square))
                .collect(Collectors.toList());
    }
}
