package com.chess_challenge.java_1.model;


import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingMovementTest {
    /* Test cases:
        1. King in the middle has 8 moves
        2. King in each of the corners has 3 moves.
     */

    @Test
    @Timeout(value = 5)
    void king_in_d4_has_8_possible_moves() throws IllegalSquareException {
        Square d4 = new Square('d', 4);

        King king = new King(Color.WHITE, d4);

        List<Square> moves = king.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('c', 3), new Square('d', 3), new Square('e', 3), // backward moves
                new Square('c', 4), new Square('e', 4), // horizontal moves
                new Square('c', 5), new Square('d', 5), new Square('e', 5) // forward moves
        );

        // all the expected moves where found
        assertAll(
                expectedMoves.stream().map((square) -> () -> assertTrue(moves.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected move found
        assertAll(
                moves.stream().map((square) -> () -> assertTrue(expectedMoves.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void king_in_a1_has_3_possible_moves() throws IllegalSquareException {
        Square d4 = new Square('a', 1);

        King king = new King(Color.WHITE, d4);

        List<Square> moves = king.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // no backward moves
                new Square('b', 1), // horizontal moves
                new Square('a', 2), new Square('b', 2) // forward moves
        );

        // all the expected moves where found
        assertAll(
                expectedMoves.stream().map((square) -> () -> assertTrue(moves.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected move found
        assertAll(
                moves.stream().map((square) -> () -> assertTrue(expectedMoves.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void king_in_h1_has_3_possible_moves() throws IllegalSquareException {
        Square d4 = new Square('h', 1);

        King king = new King(Color.WHITE, d4);

        List<Square> moves = king.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // no backward moves
                new Square('g', 1), // horizontal moves
                new Square('g', 2), new Square('h', 2) // forward moves
        );

        // all the expected moves where found
        assertAll(
                expectedMoves.stream().map((square) -> () -> assertTrue(moves.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected move found
        assertAll(
                moves.stream().map((square) -> () -> assertTrue(expectedMoves.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void king_in_a8_has_3_possible_moves() throws IllegalSquareException {
        Square d4 = new Square('a', 8);

        King king = new King(Color.WHITE, d4);

        List<Square> moves = king.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('a', 7), new Square('b', 7), //  backward moves
                new Square('b', 8) // horizontal moves
                // no forward moves
        );

        // all the expected moves where found
        assertAll(
                expectedMoves.stream().map((square) -> () -> assertTrue(moves.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected move found
        assertAll(
                moves.stream().map((square) -> () -> assertTrue(expectedMoves.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void king_in_h8_has_3_possible_moves() throws IllegalSquareException {
        Square d4 = new Square('h', 8);

        King king = new King(Color.WHITE, d4);

        List<Square> moves = king.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('g', 7), new Square('h', 7), //  backward moves
                new Square('g', 8) // horizontal moves
                // no forward moves
        );

        // all the expected moves where found
        assertAll(
                expectedMoves.stream().map((square) -> () -> assertTrue(moves.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected move found
        assertAll(
                moves.stream().map((square) -> () -> assertTrue(expectedMoves.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }
}