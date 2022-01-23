package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rook implements Piece {
    private final Color color;
    private final Square square;

    public Rook(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves() {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.horizontalMoves());
        moves.addAll(this.verticalMoves());

        return moves;
    }

    private List<Square> horizontalMoves() {
        return IntStream.rangeClosed('a', 'h')
                .filter(col -> (char)col != this.position().getColumn())
                .mapToObj(col -> new Square((char) col, this.position().getRow()))
                .collect(Collectors.toList());
    }

    private List<Square> verticalMoves() {
        return IntStream.rangeClosed(1, 8)
                .filter(row -> row != this.position().getRow())
                .mapToObj(row -> new Square(this.position().getColumn(), row))
                .collect(Collectors.toList());
    }

    @Override
    public List<Square> attacks() {
        return this.moves();
    }

    @Override
    public Square position() {
        return square;
    }

    @Override
    public Rook moveTo(Square square) throws IllegalMovementException {
        return null;
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public Type type() {
        return Type.ROOK;
    }
}
