package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Knight implements Piece {
    private final Color color;
    private final Square square;

    public Knight(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves(Board board) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.verticalMoves());

        moves.addAll(this.horizontalMoves());

        return moves
                .stream()
                .filter(square -> !board.pieceAt(square)
                        .filter(piece -> piece.color() == this.color())
                        .isPresent()
                )
                .collect(Collectors.toList());
    }

    private List<Square> verticalMoves() {
        return IntStream.of(this.position().getRow() - 2, this.position().getRow() + 2)
                .filter(row -> row >= 1 && row <= 8)
                .boxed()
                .flatMap(row ->
                        IntStream.of(this.position().getColumn() - 1, this.position().getColumn() + 1)
                                .filter(col -> col >= 'a' && col <= 'h')
                                .mapToObj(col -> new Square((char) col, row))
                )
                .collect(Collectors.toList());
    }

    private List<Square> horizontalMoves() {
        return IntStream.of(this.position().getColumn() - 2, this.position().getColumn() + 2)
                .filter(col -> col >= 'a' && col <= 'h')
                .boxed()
                .flatMap(col ->
                        IntStream.of(this.position().getRow() - 1, this.position().getRow() + 1)
                                .filter(row -> row >= 1 && row <= 8)
                                .mapToObj(row -> new Square((char) (int) col, row))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Square> attacks(Board board) {
        return this.moves(board);
    }

    @Override
    public boolean attacks(Board board, Square square) {
        return this.attacks(board).contains(square);
    }

    @Override
    public Square position() {
        return this.square;
    }

    @Override
    public Knight moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Knight(this.color(), square);
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public Type type() {
        return Type.KNIGHT;
    }
}
