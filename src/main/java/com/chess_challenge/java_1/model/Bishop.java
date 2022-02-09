package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
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

    @Override
    public List<Square> moves(Board board) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.backwardMoves(board));
        moves.addAll(this.forwardMoves(board));

        return moves;
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

    private List<Square> backwardMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // back and left moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        new Square((char) (this.position().getColumn() - 1), this.position().getRow() - 1),
                        (square) -> square.getColumn() >= 'a' && square.getRow() >= 1,
                        (square) -> new Square((char) (square.getColumn() - 1), square.getRow() - 1)
                )
        );

        // back and right moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        new Square((char) (this.position().getColumn() + 1), this.position().getRow() - 1),
                        (square) -> square.getColumn() <= 'h' && square.getRow() >= 1,
                        (square) -> new Square((char) (square.getColumn() + 1), square.getRow() - 1)
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
                        new Square((char) (this.position().getColumn() - 1), this.position().getRow() + 1),
                        (square) -> square.getColumn() >= 'a' && square.getRow() <= 8,
                        (square) -> new Square((char) (square.getColumn() - 1), square.getRow() + 1)
                )
        );

        // forward and right moves
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        new Square((char) (this.position().getColumn() + 1), this.position().getRow() + 1),
                        (square) -> square.getColumn() <= 'h' && square.getRow() <= 8,
                        (square) -> new Square((char) (square.getColumn() + 1), square.getRow() + 1)
                )
        );

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
        if (!this.moves().contains(square)){
            throw new IllegalMovementException(this, square);
        }

        return new Bishop(this.color(), square);
    }

    @Override
    public Bishop moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)){
            throw new IllegalMovementException(this, square);
        }

        return new Bishop(this.color(), square);
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
