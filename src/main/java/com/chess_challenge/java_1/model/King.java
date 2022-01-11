package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class King implements Piece {
    private final Color color;
    private final Square square;

    public King(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves() {
        List<Square> moves = new ArrayList<>();

        // Adds moves in all direction 1 square
        moves.addAll(this.backwardMoves());
        moves.addAll(this.horizontalMoves());
        moves.addAll(this.forwardMoves());

        // Removes current position as it's not a movement
        moves.remove(this.position());

        return moves;
    }

    @Override
    public List<Square> attacks() {
        return moves();
    }

    @Override
    public Square position() {
        return this.square;
    }

    @Override
    public King moveTo(Square square) throws IllegalMovementException {
        return null;
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public Type type() {
        return Type.KING;
    }

    private List<Square> backwardMoves() {
        return movesInRow(this.position().getRow() - 1);
    }

    private List<Square> horizontalMoves() {
        return movesInRow(this.position().getRow());
    }

    private List<Square> forwardMoves() {
        return movesInRow(this.position().getRow() + 1);
    }

    private List<Square> movesInRow(int row) {
        if (row < 1 || row > 8) {
            return Collections.emptyList();
        }

        char currentColumn = this.square.getColumn();
        char leftColumn = (char) (this.square.getColumn() - 1);
        char rightColumn = (char) (this.square.getColumn() + 1);

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
}
