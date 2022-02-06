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

public class PawnMovementTest {
    @ParameterizedTest
    @MethodSource("columns")
    @Timeout(value = 5)
    void white_pawn_in_row_2_has_2_moves(int col) throws IllegalSquareException {
        // Given a white Pawn in column `col` and row 2.
        char column = (char) col;
        Square position = new Square(column, 2);

        Pawn pawn = new Pawn(Color.WHITE, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square(column, 3),
                new Square(column, 4)
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
    @MethodSource("singleMoveWhiteSquares")
    @Timeout(value = 5)
    void white_pawn_in_row_3_to_7_has_1_move(char col, int row) throws IllegalSquareException {
        // Given a white Pawn in column `col` and row `row`.
        Square position = new Square(col, row);

        Pawn pawn = new Pawn(Color.WHITE, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square(col, row + 1)
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
    @MethodSource("columns")
    @Timeout(value = 5)
    void white_pawn_in_row_8_has_no_moves(int col) throws IllegalSquareException {
        // Given a white Pawn in column `col` and row 8.
        char column = (char) col;
        Square position = new Square(column, 8);

        Pawn pawn = new Pawn(Color.WHITE, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves();

        // there are no moves
        assertTrue(moves.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("columns")
    @Timeout(value = 5)
    void black_pawn_in_row_7_has_2_moves(int col) throws IllegalSquareException {
        // Given a black Pawn in column `col` and row 2.
        char column = (char) col;
        Square position = new Square(column, 7);

        Pawn pawn = new Pawn(Color.BLACK, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square(column, 6),
                new Square(column, 5)
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
    @MethodSource("singleMoveBlackSquares")
    @Timeout(value = 5)
    void black_pawn_in_row_2_to_6_has_1_move(char col, int row) throws IllegalSquareException {
        // Given a black Pawn in column `col` and row `row`.
        Square position = new Square(col, row);

        Pawn pawn = new Pawn(Color.BLACK, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves();

        List<Square> expectedMoves = Lists.newArrayList(
                new Square(col, row - 1)
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
    @MethodSource("columns")
    @Timeout(value = 5)
    void black_pawn_in_row_1_has_no_moves(int col) throws IllegalSquareException {
        // Given a black Pawn in column `col` and row 1.
        char column = (char) col;
        Square position = new Square(column, 1);

        Pawn pawn = new Pawn(Color.BLACK, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves();

        // there are no moves
        assertTrue(moves.isEmpty());
    }

    private static IntStream columns() {
        return IntStream.rangeClosed('a', 'h');
    }

    private static Stream<Arguments> singleMoveWhiteSquares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(3, 7)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }

    private static Stream<Arguments> singleMoveBlackSquares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(2, 6)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
