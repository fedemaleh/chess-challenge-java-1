package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<Square> moves(Board board) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.backwardMoves(board));
        moves.addAll(this.forwardMoves(board));

        return moves;
    }

    private List<Square> backwardMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // back and left moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getColumn() >= 'a' && square.getRow() >= 1,
                        (square) -> Square.squareOrEmpty((char) (square.getColumn() - 1), square.getRow() - 1)
                )
        );

        // back and right moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getColumn() <= 'h' && square.getRow() >= 1,
                        (square) -> Square.squareOrEmpty((char) (square.getColumn() + 1), square.getRow() - 1)
                )
        );

        return moves;
    }

    private List<Square> forwardMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // forward and left moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getColumn() >= 'a' && square.getRow() <= 8,
                        (square) -> Square.squareOrEmpty((char) (square.getColumn() - 1), square.getRow() + 1)
                )
        );

        // forward and right moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        (square) -> square.getColumn() <= 'h' && square.getRow() <= 8,
                        (square) -> Square.squareOrEmpty((char) (square.getColumn() + 1), square.getRow() + 1)
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
        return this.square;
    }

    @Override
    public Bishop moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Bishop(this.color(), square);
    }

    @Override
    public List<Square> pathTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        List<Square> path = Lists.newArrayList(this.position());

        path.addAll(this.moves(board));

        return path.stream().filter(move ->
                move.getRow() - square.getRow() == move.getColumn() - square.getColumn() && // same diagonal
                move.distanceTo(square) <= this.position().distanceTo(square)
        ).collect(Collectors.toList());
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
