package com.chess_challenge.java_1.model;

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

    private List<Square> generateMovesUntilCondition(Board board,
                                                     int initialValue,
                                                     Predicate<Integer> loopCondition,
                                                     Function<Integer, Integer> updateIndex,
                                                     Function<Integer, Square> moveGenerator) {
        List<Square> moves = new ArrayList<>();

        for (int index = initialValue; loopCondition.test(index); index = updateIndex.apply(index)) {
            Square possibleMove = moveGenerator.apply(index);

            Optional<Piece> piece = board.pieceAt(possibleMove);

            if (piece.isPresent()) {
                if (piece.filter(p -> p.color() != this.color()).isPresent()) {
                    moves.add(possibleMove);
                }

                break;
            } else {
                moves.add(possibleMove);
            }
        }

        return moves;
    }

    private List<Square> horizontalMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // Moves to left
        moves.addAll(
                generateMovesUntilCondition(
                        board,
                        this.position().getColumn() - 1,
                        (col) -> col >= 'a',
                        (col) -> col - 1,
                        (col) -> new Square((char) (int) col, this.position().getRow())
                )
        );

        // Moves to right
        moves.addAll(
                generateMovesUntilCondition(
                        board,
                        this.position().getColumn() + 1,
                        (col) -> col <= 'h',
                        (col) -> col + 1,
                        (col) -> new Square((char) (int) col, this.position().getRow())
                )
        );

        return moves;
    }

    private List<Square> verticalMoves(Board board) {
        List<Square> moves = new ArrayList<>();

        // Moves to back
        moves.addAll(
                generateMovesUntilCondition(
                        board,
                        this.position().getRow() - 1,
                        (row) -> row >= 1,
                        (row) -> row - 1,
                        (row) -> new Square(this.position().getColumn(), row)
                )
        );

        // Moves to front
        moves.addAll(
                generateMovesUntilCondition(
                        board,
                        this.position().getRow() + 1,
                        (row) -> row <= 8,
                        (row) -> row + 1,
                        (row) -> new Square(this.position().getColumn(), row)
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
    public Piece moveTo(Board board, Square square) throws IllegalMovementException {
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
