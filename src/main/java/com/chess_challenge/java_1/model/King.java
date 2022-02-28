package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class King implements Piece {
    private final Color color;
    private final Square square;

    public King(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves(Board board) {
        List<Square> moves = new ArrayList<>();

        // Adds moves in all direction 1 square
        moves.addAll(this.backwardMoves());
        moves.addAll(this.horizontalMoves());
        moves.addAll(this.forwardMoves());

        // Removes current position as it's not a movement
        moves.remove(this.position());

        return moves.stream()
                .filter(move -> !this.isSquareOccupiedByOwnPiece(board, move))
                .filter(move -> !isSquareThreatenedByRivalPiece(board, move))
                .filter(move -> !isAdjacentToOtherKing(board, move))
                .collect(Collectors.toList());
    }

    private boolean isSquareOccupiedByOwnPiece(Board board, Square move) {
        return board.pieceAt(move).filter(piece -> piece.color() == this.color()).isPresent();
    }

    private boolean isSquareThreatenedByRivalPiece(Board board, Square move) {
        return board.isThreatened(move, this);
    }

    private boolean isAdjacentToOtherKing(Board board, Square move) {
        return !board.validKingMove(this, move);
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
    public King moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new King(this.color(), square);
    }

    @Override
    public List<Square> pathTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return Lists.newArrayList(this.position(), square);
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
