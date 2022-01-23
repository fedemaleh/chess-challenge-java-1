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

public class BishopMovementTest {
    @Test
    @Timeout(value = 5)
    void bishop_in_d4_can_move_in_4_possible_moves() throws IllegalSquareException {
        // Given a white Bishop in square D4.
        Square d4 = new Square('d', 4);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // left and backward
                new Square('a', 1), new Square('b', 2), new Square('c', 3),
                // left and forward
                new Square('c', 5), new Square('b', 6), new Square('a', 7),
                // right and backward
                new Square('g', 1), new Square('f', 2), new Square('e', 3),
                // right and forward
                new Square('e', 5), new Square('f', 6), new Square('g', 7), new Square('h', 8)
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
    void bishop_in_a1_moves_in_whole_white_diagonal() throws IllegalSquareException {
        // Given a white Bishop in square A1.
        Square a1 = new Square('a', 1);

        Bishop bishop = new Bishop(Color.WHITE, a1);

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('b', 2), new Square('c', 3), new Square('d', 4),
                new Square('e', 5), new Square('f', 6), new Square('g', 7),
                new Square('h', 8)
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
    void bishop_in_h1_moves_in_whole_black_diagonal() throws IllegalSquareException {
        // Given a white Bishop in square H1.
        Square h1 = new Square('h', 1);

        Bishop bishop = new Bishop(Color.WHITE, h1);

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('g', 2), new Square('f', 3), new Square('e', 4),
                new Square('d', 5), new Square('c', 6), new Square('b', 7),
                new Square('a', 8)
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
    void bishop_in_a8_moves_in_whole_black_diagonal() throws IllegalSquareException {
        // Given a white Bishop in square A8.
        Square a8 = new Square('a', 8);

        Bishop bishop = new Bishop(Color.WHITE, a8);

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('h', 1), new Square('g', 2), new Square('f', 3),
                new Square('e', 4), new Square('d', 5), new Square('c', 6),
                new Square('b', 7)
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
    void bishop_in_h8_moves_in_whole_white_diagonal() throws IllegalSquareException {
        // Given a white Bishop in square H8.
        Square d8 = new Square('h', 8);

        Bishop bishop = new Bishop(Color.WHITE, d8);

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square('a', 1), new Square('b', 2), new Square('c', 3),
                new Square('d', 4), new Square('e', 5), new Square('f', 6),
                new Square('g', 7)
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
    void bishop_moves_are_equal_to_attacks_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a Bishop
        Square square = new Square(col, row);

        Bishop bishop = new Bishop(Color.WHITE, square);

        // It's moves should be the same as it's attacks
        List<Square> moves = bishop.moves();

        List<Square> attacks = bishop.attacks();

        assertEquals(moves, attacks);
    }

    @Test
    @Timeout(value = 5)
    void bishop_in_d4_can_move_to_e5() throws IllegalSquareException {
        // Given a white bishop in square D4.
        Square d4 = new Square('d', 4);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        // When it's asked to move to E5
        Square e5 = new Square('e', 5);

        // Then a new King is created in the E4 square.
        Bishop movedBishop = assertDoesNotThrow(() -> bishop.moveTo(e5));

        assertEquals(e5, movedBishop.position());
    }

    @Test
    @Timeout(value = 5)
    void bishop_in_d4_cannot_move_to_e4() throws IllegalSquareException {
        // Given a white bishop in square D4.
        Square d4 = new Square('d', 4);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        // When it's asked to move to E4
        Square e4 = new Square('e', 4);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> bishop.moveTo(e4));
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
