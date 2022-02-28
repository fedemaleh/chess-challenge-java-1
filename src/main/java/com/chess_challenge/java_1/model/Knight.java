package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Knight implements MovementStrategy {

    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.verticalMoves(piece));

        moves.addAll(this.horizontalMoves(piece));

        return moves
                .stream()
                .filter(square -> !board.pieceAt(square)
                        .filter(p -> p.color() == piece.color())
                        .isPresent()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        if (!this.moves(board, piece).contains(square)) {
            throw new IllegalMovementException(piece, square);
        }

        return Lists.newArrayList(piece.position(), square);
    }

    private List<Square> verticalMoves(Piece piece) {
        return IntStream.of(piece.position().getRow() - 2, piece.position().getRow() + 2)
                .filter(row -> row >= 1 && row <= 8)
                .boxed()
                .flatMap(row ->
                        IntStream.of(piece.position().getColumn() - 1, piece.position().getColumn() + 1)
                                .filter(col -> col >= 'a' && col <= 'h')
                                .mapToObj(col -> new Square((char) col, row))
                )
                .collect(Collectors.toList());
    }

    private List<Square> horizontalMoves(Piece piece) {
        return IntStream.of(piece.position().getColumn() - 2, piece.position().getColumn() + 2)
                .filter(col -> col >= 'a' && col <= 'h')
                .boxed()
                .flatMap(col ->
                        IntStream.of(piece.position().getRow() - 1, piece.position().getRow() + 1)
                                .filter(row -> row >= 1 && row <= 8)
                                .mapToObj(row -> new Square((char) (int) col, row))
                )
                .collect(Collectors.toList());
    }
}
