package com.chess_challenge.java_1.model;

import java.util.List;

/**
 * Represents a chess piece. It contains all the methods needed to model the movement of the pieces.
 */
public interface Piece {
    /**
     * Gets the movements a piece can make.
     *
     * @param board where the piece will be moved.
     * @return a list of squares where the piece can move.
     */
    List<Square> moves(Board board);

    /**
     * Gets the attack targets a piece has.
     *
     * @param board where the piece will be moved.
     * @return a list of squares that are threatened by the piece.
     */
    List<Square> attacks(Board board);

    /**
     * Gets the current position of the piece.
     *
     * @return the square of the piece.
     */
    Square position();

    /**
     * Moves a piece to a different square.
     *
     * @param board  where the piece will be moved.
     * @param square the target square for the piece.
     * @return the piece in the new square.
     * @throws IllegalMovementException when the piece can't move to the desired square.
     */
    Piece moveTo(Board board, Square square) throws IllegalMovementException;

    /**
     * Gets the color of the piece.
     *
     * @return the color of the piece.
     */
    Color color();

    /**
     * Gets the type of the piece.
     *
     * @return the type of the piece.
     */
    Type type();
}
