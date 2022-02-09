package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RookMovementTest {
    @Test
    @Timeout(value = 5)
    void rook_in_d4_can_move_in_4_possible_moves() throws IllegalSquareException {
        // Given a white Rook in square D4.
        Square d4 = new Square('d', 4);

        Rook rook = new Rook(Color.WHITE, d4);

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(Board.emptyBoard());

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
        // Given a white Rook in square A1.
        Square a1 = new Square('a', 1);

        Rook rook = new Rook(Color.WHITE, a1);

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(Board.emptyBoard());

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
        // Given a white Rook in square H1.
        Square h1 = new Square('h', 1);

        Rook rook = new Rook(Color.WHITE, h1);

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(Board.emptyBoard());

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
        // Given a white Rook in square A8.
        Square a8 = new Square('a', 8);

        Rook rook = new Rook(Color.WHITE, a8);

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(Board.emptyBoard());

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
        // Given a white Rook in square H8.
        Square d8 = new Square('h', 8);

        Rook rook = new Rook(Color.WHITE, d8);

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(Board.emptyBoard());

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

    @ParameterizedTest
    @MethodSource("squares")
    @Timeout(value = 5)
    void rook_moves_are_equal_to_attacks_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a Rook
        Square square = new Square(col, row);

        Rook rook = new Rook(Color.WHITE, square);
        Board board = Board.emptyBoard();

        // It's moves should be the same as it's attacks
        List<Square> moves = rook.moves(board);

        List<Square> attacks = rook.attacks(board);

        assertEquals(moves, attacks);
    }

    @Test
    @Timeout(value = 5)
    void rook_in_d4_can_move_to_e4() throws IllegalSquareException {
        // Given a white rook in square D4.
        Square d4 = new Square('d', 4);

        Rook rook = new Rook(Color.WHITE, d4);

        // When it's asked to move to E4
        Square e4 = new Square('e', 4);

        // Then a new Rook is created in the E4 square.
        Rook movedRook = assertDoesNotThrow(() -> rook.moveTo(Board.emptyBoard(), e4));

        assertEquals(e4, movedRook.position());
    }

    @Test
    @Timeout(value = 5)
    void rook_in_d4_cannot_move_to_e1() throws IllegalSquareException {
        // Given a white rook in square D4.
        Square d4 = new Square('d', 4);

        Rook rook = new Rook(Color.WHITE, d4);

        // When it's asked to move to E1
        Square e1 = new Square('e', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> rook.moveTo(Board.emptyBoard(), e1));
    }

    @Test
    @Timeout(value = 5)
    void white_rook_can_move_up_to_the_first_white_piece_in_each_direction_not_included() {
        // Given a white rook in square D4 and a Board with white pieces in D2, D6, B4, F4
        Square d4 = new Square('d', 4);
        Square d2 = new Square('d', 2);
        Square d6 = new Square('d', 6);
        Square b4 = new Square('b', 4);
        Square f4 = new Square('f', 4);

        Rook rook = new Rook(Color.WHITE, d4);

        Piece p1 = new Pawn(Color.WHITE, d2);
        Piece p2 = new Pawn(Color.WHITE, d6);
        Piece p3 = new Pawn(Color.WHITE, b4);
        Piece p4 = new Pawn(Color.WHITE, f4);

        Board board = new Board(Lists.newArrayList(p1, p2, p3, p4));

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(board);

        List<Square> expectedMoves = Lists.newArrayList(
                // vertical moves
                new Square('d', 3),
                new Square('d', 5),
                // horizontal moves
                new Square('c', 4),
                new Square('e', 4)
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
    void white_rook_can_move_up_to_the_first_white_piece_included() {
        // Given a white rook in square D4 and a Board with white pieces in D2, D6, B4, F4
        Square d4 = new Square('d', 4);
        Square d2 = new Square('d', 2);
        Square d6 = new Square('d', 6);
        Square b4 = new Square('b', 4);
        Square f4 = new Square('f', 4);

        Rook rook = new Rook(Color.WHITE, d4);

        Piece p1 = new Pawn(Color.BLACK, d2);
        Piece p2 = new Pawn(Color.BLACK, d6);
        Piece p3 = new Pawn(Color.BLACK, b4);
        Piece p4 = new Pawn(Color.BLACK, f4);

        Board board = new Board(Lists.newArrayList(p1, p2, p3, p4));

        // When the rook is asked for his moves
        List<Square> moves = rook.moves(board);

        List<Square> expectedMoves = Lists.newArrayList(
                // vertical moves
                new Square('d', 2),
                new Square('d', 3),
                new Square('d', 5),
                new Square('d', 6),
                // horizontal moves
                new Square('b', 4),
                new Square('c', 4),
                new Square('e', 4),
                new Square('f', 4)
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

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
