package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Rook implements Piece {
    private final Color color;
    private final Square square;

    public Rook(Color color, Square square) {
        this.color = color;
        this.square = square;
    }

    @Override
    public List<Square> moves(Board board) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.horizontalMoves(board));
        moves.addAll(this.verticalMoves(board));

        return moves;
    }

    private List<Square> horizontalMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // Moves to left
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getColumn() >= 'a',
                        (square) -> Square.squareOrEmpty((char) (square.getColumn() - 1), this.position().getRow())
                )
        );

        // Moves to right
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getColumn() <= 'h',
                        (square) -> Square.squareOrEmpty((char) (square.getColumn() + 1), this.position().getRow())
                )
        );

        return moves;
    }

    private List<Square> verticalMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // Moves to back
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getRow() >= 1,
                        (square) -> Square.squareOrEmpty(square.getColumn(), square.getRow() - 1)
                )
        );

        // Moves to front
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getRow() <= 8,
                        (square) -> Square.squareOrEmpty(square.getColumn(), square.getRow() + 1)
                )
        );

        return moves;
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
        return square;
    }

    @Override
    public Rook moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Rook(this.color(), square);
    }

    @Override
    public List<Square> pathTo(Board board, Square square) throws IllegalMovementException {
        List<Square> path = this.moves(board);

        if (!path.contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        path.add(0, this.position());

        return path.stream()
                .filter(move -> move.isAligned(square) && move.distanceTo(square) <= this.position().distanceTo(square))
                .collect(Collectors.toList());
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
