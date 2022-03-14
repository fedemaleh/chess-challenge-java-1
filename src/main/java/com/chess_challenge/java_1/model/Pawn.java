package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;

import java.util.*;

public class Pawn implements MovementStrategy {
    @Override
    public List<Square> moves(Board board, Piece piece) {
        List<Square> moves = new ArrayList<>();

        Optional<Square> firstPossibleMove = this.nextSquare(piece.color(), piece.position())
                .filter(board::emptySquare);

        Optional<Square> secondPossibleMove = firstPossibleMove
                .filter(move -> this.isInitialPosition(piece))
                .flatMap(move -> this.nextSquare(piece.color(), move))
                .filter(board::emptySquare);

        firstPossibleMove.ifPresent(moves::add);
        secondPossibleMove.ifPresent(moves::add);

        return moves;
    }

    @Override
    public List<Square> attacks(Board board, Piece piece) {
        List<Square> attacks = new ArrayList<>();

        Optional<Square> nextRow = this.nextSquare(piece.color(), piece.position());

        Optional<Square> leftAttack = nextRow.flatMap(Square::leftSquare);
        Optional<Square> rightAttack = nextRow.flatMap(Square::rightSquare);

        leftAttack.ifPresent(attacks::add);
        rightAttack.ifPresent(attacks::add);

        return attacks;
    }

    @Override
    public List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException {
        if (!this.moves(board, piece).contains(square) && !this.attacks(board, piece).contains(square)) {
            throw new IllegalMovementException(piece, square);
        }

        return Lists.newArrayList(piece.position(), square);
    }

    @Override
    public Type type() {
        return Type.PAWN;
    }

    private Optional<Square> nextSquare(Color color, Square position) {
        if (color == Color.WHITE) {
            return position.forwardSquare();
        }

        return position.backwardSquare();
    }

    private boolean isInitialPosition(Piece piece) {
        if (piece.color() == Color.WHITE) {
            return piece.position().getRow() == Square.MIN_ROW + 1;
        }

        return piece.position().getRow() == Square.MAX_ROW - 1;
    }
}
