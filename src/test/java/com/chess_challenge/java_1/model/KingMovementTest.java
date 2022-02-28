package com.chess_challenge.java_1.model;


import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class KingMovementTest {
    /* Test cases:
        1. King in the middle has 8 moves
        2. King in each of the corners has 3 moves.
     */

    @Test
    @Timeout(value = 5)
    void king_in_d4_has_8_possible_moves() throws IllegalSquareException {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);

        King king = new King(Color.WHITE, d4);

        // When the king is asked for his moves
        List<Square> moves = king.moves(Board.emptyBoard());

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
        // Given a white king in square A1.
        Square a1 = new Square('a', 1);

        King king = new King(Color.WHITE, a1);

        // When the king is asked for his moves
        List<Square> moves = king.moves(Board.emptyBoard());

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
        // Given a white king in square H1.
        Square h1 = new Square('h', 1);

        King king = new King(Color.WHITE, h1);

        // When the king is asked for his moves
        List<Square> moves = king.moves(Board.emptyBoard());

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
        // Given a white king in square A8.
        Square a8 = new Square('a', 8);

        King king = new King(Color.WHITE, a8);

        // When the king is asked for his moves
        List<Square> moves = king.moves(Board.emptyBoard());

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
        // Given a white king in square H8.
        Square d8 = new Square('h', 8);

        King king = new King(Color.WHITE, d8);

        // When the king is asked for his moves
        List<Square> moves = king.moves(Board.emptyBoard());

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

    @ParameterizedTest
    @MethodSource("squares")
    @Timeout(value = 5)
    void king_moves_are_equal_to_attacks_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a King
        Square square = new Square(col, row);

        King king = new King(Color.WHITE, square);
        Board board = Board.emptyBoard();

        // It's moves should be the same as it's attacks
        List<Square> moves = king.moves(board);

        List<Square> attacks = king.attacks(board);

        assertEquals(moves, attacks);
    }

    @Test
    @Timeout(value = 5)
    void king_in_d4_can_move_to_e4() throws IllegalSquareException {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);

        King king = new King(Color.WHITE, d4);

        // When it's asked to move to E4
        Square e4 = new Square('e', 4);

        // Then a new King is created in the E4 square.
        King movedKing = assertDoesNotThrow(() -> king.moveTo(Board.emptyBoard(), e4));

        assertEquals(e4, movedKing.position());
    }

    @Test
    @Timeout(value = 5)
    void king_in_d4_cannot_move_to_d1() throws IllegalSquareException {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);

        King king = new King(Color.WHITE, d4);

        // When it's asked to move to D1
        Square d1 = new Square('d', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> king.moveTo(Board.emptyBoard(), d1));
    }

    @Test
    @Timeout(value = 5)
    void white_king_in_d4_cannot_move_to_d5_if_d5_is_occupied_by_white_piece() {
        // Given a white king in square D4 and a Board with a white piece in D5.
        Square d4 = new Square('d', 4);
        Square d5 = new Square('d', 5);

        King king = new King(Color.WHITE, d4);

        Board board = new Board(Collections.singletonList(new Pawn(Color.WHITE, d5)));

        // When it's asked to move to D5
        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> king.moveTo(board, d5));
    }

    @Test
    @Timeout(value = 5)
    void white_king_in_d4_can_move_to_d5_if_d5_is_occupied_by_black_piece() {
        // Given a white king in square D4 and a Board with a black piece in D5.
        Square d4 = new Square('d', 4);
        Square d5 = new Square('d', 5);

        King king = new King(Color.WHITE, d4);

        Board board = new Board(Collections.singletonList(new Pawn(Color.BLACK, d5)));

        // When it's asked to move to D5
        // Then an IllegalMovementException is thrown
        King movedKing = assertDoesNotThrow(() -> king.moveTo(board, d5));

        assertEquals(d5, movedKing.position());
    }

    @Test
    @Timeout(value = 5)
    void king_path_to_valid_move_is_always_origin_square_and_destination_square() {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);

        King king = new King(Color.WHITE, d4);

        // When it's asked for the path to E4
        Square e4 = new Square('e', 4);

        List<Square> path = assertDoesNotThrow(() -> king.pathTo(Board.emptyBoard(), e4));
        List<Square> expectedMoves = Lists.newArrayList(
                d4, // current square
                e4 // destination square
        );

        // all the expected moves where found
        assertAll(
                expectedMoves.stream().map((square) -> () -> assertTrue(path.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected move found
        assertAll(
                path.stream().map((square) -> () -> assertTrue(expectedMoves.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void king_path_to_invalid_move_throws_exception() {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);

        King king = new King(Color.WHITE, d4);

        // When it's asked for the path to D1
        Square d1 = new Square('d', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> king.pathTo(Board.emptyBoard(), d1));
    }

    @Test
    @Timeout(value = 5)
    void king_cannot_move_to_adjacent_other_king() {
        // Given a white king in square D4.
        Square d4 = new Square('d', 4);
        Square d6 = new Square('d', 6);

        King whiteKing = new King(Color.WHITE, d4);

        King blackKing = new King(Color.BLACK, d6);

        // When it's asked to move to D5
        Square d5 = new Square('d', 5);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> whiteKing.moveTo(new Board(whiteKing, blackKing), d5));
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}