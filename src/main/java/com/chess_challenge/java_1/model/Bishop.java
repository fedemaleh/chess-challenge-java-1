package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bishop implements Piece {
    private final Color color;
    private final Square square;

    public Bishop(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves() {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.backwardMoves());
        moves.addAll(this.forwardMoves());

        return moves.stream().filter(move -> !move.equals(this.position())).collect(Collectors.toList());
    }

    private List<Square> backwardMoves() {
        int leftCol = this.position().getColumn() - 1;
        int rightCol = this.position().getColumn() + 1;
        int currentRow = this.position().getRow() - 1;

        List<Square> moves = new ArrayList<>();

        while (currentRow >= 1) {
            if(leftCol >= 'a') {
                moves.add(new Square((char)leftCol, currentRow));
            }

            if(rightCol <= 'h') {
                moves.add(new Square((char)rightCol, currentRow));
            }

            leftCol--;
            rightCol++;
            currentRow--;
        }

        return moves;
    }

    private List<Square> forwardMoves() {
        int leftCol = this.position().getColumn() - 1;
        int rightCol = this.position().getColumn() + 1;
        int currentRow = this.position().getRow() + 1;

        List<Square> moves = new ArrayList<>();

        while (currentRow <= 8) {
            if(leftCol >= 'a') {
                moves.add(new Square((char)leftCol, currentRow));
            }

            if(rightCol <= 'h') {
                moves.add(new Square((char)rightCol, currentRow));
            }

            leftCol--;
            rightCol++;
            currentRow++;
        }

        return moves;
    }

    @Override
    public List<Square> attacks() {
        return this.moves();
    }

    @Override
    public Square position() {
        return this.square;
    }

    @Override
    public Bishop moveTo(Square square) throws IllegalMovementException {
        return null;
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public Type type() {
        return Type.BISHOP;
    }
}
