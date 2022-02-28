package com.chess_challenge.java_1.model;

import java.util.List;

/**
 * Represents a chess piece. It contains all the methods needed to model the movement of the pieces.
 */
public class Piece {
    private final MovementStrategy type;
    private final Color color;
    private final Square position;

    public Piece(MovementStrategy type, Color color, Square position) {
        this.type = type;
        this.color = color;
        this.position = position;
    }

    /**
     * Gets the movements a piece can make.
     *
     * @param board where the piece will be moved.
     * @return a list of squares where the piece can move.
     */
    public List<Square> moves(Board board) {
        return this.type().moves(board, this);
    }

    /**
     * Gets the attack targets a piece has.
     *
     * @param board where the piece will be moved.
     * @return a list of squares that are threatened by the piece.
     */
    public List<Square> attacks(Board board) {
        return this.type().attacks(board, this);
    }

    /**
     * Gets the attack targets a piece has.
     *
     * @param board  where the piece will be moved.
     * @param square to attack
     * @return if the piece can attack the square.
     */
    public boolean attacks(Board board, Square square) {
        return this.attacks(board).contains(square);
    }

    /**
     * Gets the current position of the piece.
     *
     * @return the square of the piece.
     */
    public Square position() {
        return this.position;
    }

    /**
     * Moves a piece to a different square.
     *
     * @param board  where the piece will be moved.
     * @param square the target square for the piece.
     * @return the piece in the new square.
     * @throws IllegalMovementException when the piece can't move to the desired square.
     */
    public Piece moveTo(Board board, Square square) throws IllegalMovementException {
        if (!this.moves(board).contains(square)) {
            throw new IllegalMovementException(this, square);
        }

        return new Piece(this.type(), this.color(), square);
    }

    /**
     * Gives the squares that need to be visited to get from the current position to the desired one.
     *
     * @param board  where the piece will be moved.
     * @param square the target square for the piece.
     * @return a list with all the squares visited to get from the current position to the desired one.
     * @throws IllegalMovementException when the piece can't move to the desired square.
     */
    public List<Square> pathTo(Board board, Square square) throws IllegalMovementException {
        return this.type().pathTo(board, this, square);
    }

    /**
     * Gets the color of the piece.
     *
     * @return the color of the piece.
     */
    public Color color() {
        return this.color;
    }

    /**
     * Gets the type of the piece.
     *
     * @return the type of the piece.
     */
    public MovementStrategy type() {
        return this.type;
    }
}
