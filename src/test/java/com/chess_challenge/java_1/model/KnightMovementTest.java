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

public class KnightMovementTest {
    @Test
    @Timeout(value = 5)
    void knight_in_d4_has_8_moves() throws IllegalSquareException {
        // Given a white Knight in square D4.
        Square d4 = new Square('d', 4);

        Knight knight = new Knight(Color.WHITE, d4);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // Forward moves
                new Square('c', 6), new Square('e', 6),
                // Backward moves
                new Square('c', 2), new Square('e', 2),
                // Leftward moves
                new Square('b', 3), new Square('b', 5),
                // Rightward moves
                new Square('f', 3), new Square('f', 5)
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
    void knight_in_a1_has_2_moves() throws IllegalSquareException {
        // Given a white Knight in square A1.
        Square a1 = new Square('a', 1);

        Knight knight = new Knight(Color.WHITE, a1);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // Forward moves
                new Square('b', 3),
                // Backward moves
                // Leftward moves
                // Rightward moves
                new Square('c', 2)
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
    void knight_in_h1_has_2_moves() throws IllegalSquareException {
        // Given a white Knight in square H1.
        Square h1 = new Square('h', 1);

        Knight knight = new Knight(Color.WHITE, h1);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // Forward moves
                new Square('g', 3),
                // Backward moves
                // Leftward moves
                new Square('f', 2)
                // Rightward moves
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
    void knight_in_a8_has_2_moves() throws IllegalSquareException {
        // Given a white Knight in square A8.
        Square a8 = new Square('a', 8);

        Knight knight = new Knight(Color.WHITE, a8);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // Forward moves
                // Backward moves
                new Square('b', 6),
                // Leftward moves
                // Rightward moves
                new Square('c', 7)
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
    void knight_in_h8_has_2_moves() throws IllegalSquareException {
        // Given a white Knight in square H8.
        Square d8 = new Square('h', 8);

        Knight knight = new Knight(Color.WHITE, d8);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                // Forward moves
                // Backward moves
                new Square('g', 6),
                // Leftward moves
                new Square('f', 7)
                // Rightward moves
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
    void knight_moves_are_equal_to_attacks_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a Knight
        Square square = new Square(col, row);

        Knight knight = new Knight(Color.WHITE, square);

        // It's moves should be the same as it's attacks
        List<Square> moves = knight.moves();

        List<Square> attacks = knight.attacks();

        assertEquals(moves, attacks);
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
