package com.chess_challenge.java_1.model;

import java.util.List;

/**
 * This interface is used to represent each possible piece movements.
 */
public interface MovementStrategy {
    /**
     * Gets the movements a piece can make.
     *
     * @param board where the piece will be moved.
     * @param piece the piece that will be moved.
     * @return a list of squares where the piece can move.
     */
    List<Square> moves(Board board, Piece piece);

    /**
     * Gets the attack targets a piece has. It defaults to the moves as most chess pieces attack as they move.
     *
     * @param board where the piece will be moved.
     * @param piece the piece that attacks.
     * @return a list of squares that are threatened by the piece.
     */
    default List<Square> attacks(Board board, Piece piece) {
        return this.moves(board, piece);
    }

    /**
     * Gives the squares that need to be visited to get from the current position to the desired one.
     *
     * @param board  where the piece will be moved.
     * @param piece  the piece that attacks.
     * @param square the target square for the piece.
     * @return a list with all the squares visited to get from the current position to the desired one.
     * @throws IllegalMovementException when the piece can't move to the desired square.
     */
    List<Square> pathTo(Board board, Piece piece, Square square) throws IllegalMovementException;

    /**
     * Gets the name of the piece represented by the movement strategy.
     * @return the piece represented by the movement strategy.
     */
    String name();
}
