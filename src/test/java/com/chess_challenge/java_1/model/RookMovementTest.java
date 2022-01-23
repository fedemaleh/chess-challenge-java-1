package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RookMovementTest {
    @Test
    @Timeout(value = 5)
    void rook_in_d4_can_move_in_4_possible_moves() throws IllegalSquareException {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);

        Rook rook = new Rook(Color.WHITE, d4);

        // When the king is asked for his moves
        List<Square> moves = rook.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('a', 4), new Square('b', 4), new Square('c', 4), // moves to left
                new Square('e', 4), new Square('f', 4), new Square('g', 4), new Square('h', 4), // moves to right
                new Square('d', 1), new Square('d', 2), new Square('d', 3), // backward moves
                new Square('d', 5), new Square('d', 6), new Square('d', 7), new Square('d', 8) // forward moves
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
    void rook_in_a1_has_2_directions_moves() throws IllegalSquareException {
        // Given a white king in square A1.
        Square a1 = new Square('a', 1);

        Rook rook = new Rook(Color.WHITE, a1);

        // When the king is asked for his moves
        List<Square> moves = rook.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // no left moves
                new Square('b', 1), new Square('c', 1), new Square('d', 1), // moves to right
                new Square('e', 1), new Square('f', 1), new Square('g', 1), new Square('h', 1), // moves to right
                // no backward moves
                new Square('a', 2), new Square('a', 3), new Square('a', 4), // forward moves
                new Square('a', 5), new Square('a', 6), new Square('a', 7), new Square('a', 8) // forward moves
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
    void rook_in_h1_has_2_directions_moves() throws IllegalSquareException {
        // Given a white king in square H1.
        Square h1 = new Square('h', 1);

        Rook rook = new Rook(Color.WHITE, h1);

        // When the king is asked for his moves
        List<Square> moves = rook.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('a', 1), new Square('b', 1), new Square('c', 1), // moves to left
                new Square('d', 1), new Square('e', 1), new Square('f', 1), new Square('g', 1), // moves to left
                // no right moves
                // no backward moves
                new Square('h', 2), new Square('h', 3), new Square('h', 4), // backward moves
                new Square('h', 5), new Square('h', 6), new Square('h', 7), new Square('h', 8) // backward moves
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
    void rook_in_a8_has_2_directions_moves() throws IllegalSquareException {
        // Given a white king in square A8.
        Square a8 = new Square('a', 8);

        Rook rook = new Rook(Color.WHITE, a8);

        // When the king is asked for his moves
        List<Square> moves = rook.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // no left moves
                new Square('b', 8), new Square('c', 8), new Square('d', 8), // moves to right
                new Square('e', 8), new Square('f', 8), new Square('g', 8), new Square('h', 8), // moves to right
                // no forward moves
                new Square('a', 1), new Square('a', 2), new Square('a', 3), // backward moves
                new Square('a', 4), new Square('a', 5), new Square('a', 6), new Square('a', 7) // backward moves
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
    void rook_in_a1_has_8_directions_moves() throws IllegalSquareException {
        // Given a white king in square H8.
        Square d8 = new Square('h', 8);

        Rook rook = new Rook(Color.WHITE, d8);

        // When the king is asked for his moves
        List<Square> moves = rook.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('a', 8), new Square('b', 8), new Square('c', 8), // moves to left
                new Square('d', 8), new Square('e', 8), new Square('f', 8), new Square('g', 8), // moves to left
                // no right moves
                // no forward moves
                new Square('h', 1), new Square('h', 2), new Square('h', 3), // backward moves
                new Square('h', 4), new Square('h', 5), new Square('h', 6), new Square('h', 7) // backward moves
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
