package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

    @Override
    public List<Square> moves(Board board) {
        List<Square> moves = new ArrayList<>();

        moves.addAll(this.horizontalMoves(board));
        moves.addAll(this.verticalMoves(board));

        return moves;
    }

    private List<Square> horizontalMoves() {
        return IntStream.rangeClosed('a', 'h')
                .filter(col -> (char) col != this.position().getColumn())
                .mapToObj(col -> new Square((char) col, this.position().getRow()))
                .collect(Collectors.toList());
    }

    private List<Square> verticalMoves() {
        return IntStream.rangeClosed(1, 8)
                .filter(row -> row != this.position().getRow())
                .mapToObj(row -> new Square(this.position().getColumn(), row))
                .collect(Collectors.toList());
    }

    private List<Square> horizontalMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // Moves to left
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        new Square((char) (this.position().getColumn() - 1), this.position().getRow()),
                        (square) -> square.getColumn() >= 'a',
                        (square) -> new Square((char) (square.getColumn() - 1), this.position().getRow())
                )
        );

        // Moves to right
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        new Square((char) (this.position().getColumn() + 1), this.position().getRow()),
                        (square) -> square.getColumn() <= 'h',
                        (square) -> new Square((char) (square.getColumn() + 1), this.position().getRow())
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
                        new Square(this.position().getColumn(), this.position().getRow() - 1),
                        (square) -> square.getRow() >= 1,
                        (square) -> new Square(square.getColumn(), square.getRow() - 1)
                )
        );

        // Moves to front
        moves.addAll(
                MovementUtils.generateMovesUntilCondition(
                        board,
                        this,
                        new Square(this.position().getColumn(), this.position().getRow() + 1),
                        (square) -> square.getRow() <= 8,
                        (square) -> new Square(square.getColumn(), square.getRow() + 1)
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
        return square;
    }

    @Override
    public Rook moveTo(Square square) throws IllegalMovementException {
        if (!this.moves().contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Rook(this.color(), square);
    }

    @Override
    public Rook moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Rook(this.color(), square);
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
