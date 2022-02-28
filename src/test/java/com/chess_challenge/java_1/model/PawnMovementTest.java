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

public class PawnMovementTest {
    @ParameterizedTest
    @MethodSource("columns")
    @Timeout(value = 5)
    void white_pawn_in_row_2_has_2_moves(int col) throws IllegalSquareException {
        // Given a white Pawn in column `col` and row 2.
        char column = (char) col;
        Square position = new Square(column, 2);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves(Board.emptyBoard());

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

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves(Board.emptyBoard());

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

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves(Board.emptyBoard());

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

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves(Board.emptyBoard());

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

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves(Board.emptyBoard());

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

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his moves
        List<Square> moves = pawn.moves(Board.emptyBoard());

        // there are no moves
        assertTrue(moves.isEmpty());
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_square_d2_has_2_attacks() {
        // Given a white Pawn in square d2.
        Square position = new Square('d', 2);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        List<Square> expectedAttacks = Lists.newArrayList(
                new Square('c', 3),
                new Square('e', 3)
        );

        // all the expected attacks where found
        assertAll(
                expectedAttacks.stream().map((square) -> () -> assertTrue(attacks.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected attack found
        assertAll(
                attacks.stream().map((square) -> () -> assertTrue(expectedAttacks.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_square_a2_has_1_attack() {
        // Given a white Pawn in square a2.
        Square position = new Square('a', 2);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        List<Square> expectedAttacks = Lists.newArrayList(
                new Square('b', 3)
        );

        // all the expected attacks where found
        assertAll(
                expectedAttacks.stream().map((square) -> () -> assertTrue(attacks.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected attack found
        assertAll(
                attacks.stream().map((square) -> () -> assertTrue(expectedAttacks.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_square_h2_has_1_attack() {
        // Given a white Pawn in square h2.
        Square position = new Square('h', 2);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        List<Square> expectedAttacks = Lists.newArrayList(
                new Square('g', 3)
        );

        // all the expected attacks where found
        assertAll(
                expectedAttacks.stream().map((square) -> () -> assertTrue(attacks.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected attack found
        assertAll(
                attacks.stream().map((square) -> () -> assertTrue(expectedAttacks.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @ParameterizedTest
    @MethodSource("columns")
    @Timeout(value = 5)
    void white_pawn_in_row_8_has_no_attack(int col) throws IllegalSquareException {
        // Given a white Pawn in column `col` and row 8.
        char column = (char) col;
        Square position = new Square(column, 8);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        // there are no attacks
        assertTrue(attacks.isEmpty());
    }

    @Test
    @Timeout(value = 5)
    void black_pawn_in_square_d2_has_2_attacks() {
        // Given a black Pawn in square d2.
        Square position = new Square('d', 2);

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        List<Square> expectedAttacks = Lists.newArrayList(
                new Square('c', 1),
                new Square('e', 1)
        );

        // all the expected attacks where found
        assertAll(
                expectedAttacks.stream().map((square) -> () -> assertTrue(attacks.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected attack found
        assertAll(
                attacks.stream().map((square) -> () -> assertTrue(expectedAttacks.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void black_pawn_in_square_a2_has_1_attack() {
        // Given a black Pawn in square a2.
        Square position = new Square('a', 2);

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        List<Square> expectedAttacks = Lists.newArrayList(
                new Square('b', 1)
        );

        // all the expected attacks where found
        assertAll(
                expectedAttacks.stream().map((square) -> () -> assertTrue(attacks.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected attack found
        assertAll(
                attacks.stream().map((square) -> () -> assertTrue(expectedAttacks.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @Test
    @Timeout(value = 5)
    void black_pawn_in_square_h2_has_1_attack() {
        // Given a black Pawn in square h2.
        Square position = new Square('h', 2);

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        List<Square> expectedAttacks = Lists.newArrayList(
                new Square('g', 1)
        );

        // all the expected attacks where found
        assertAll(
                expectedAttacks.stream().map((square) -> () -> assertTrue(attacks.contains(square), () -> String.format("Move: %s was not found", square)))
        );

        // no additional expected attack found
        assertAll(
                attacks.stream().map((square) -> () -> assertTrue(expectedAttacks.contains(square), () -> String.format("Move: %s is not expected", square)))
        );
    }

    @ParameterizedTest
    @MethodSource("columns")
    @Timeout(value = 5)
    void black_pawn_in_row_1_has_no_attack(int col) throws IllegalSquareException {
        // Given a black Pawn in column `col` and row 1.
        char column = (char) col;
        Square position = new Square(column, 1);

        Piece pawn = new Piece(new Pawn(), Color.BLACK, position);

        // When the pawn is asked for his attacks
        List<Square> attacks = pawn.attacks(Board.emptyBoard());

        // there are no attacks
        assertTrue(attacks.isEmpty());
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_d4_can_move_to_d5() throws IllegalSquareException {
        // Given a white pawn in square D4.
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d4);

        // When it's asked to move to D5
        Square d5 = new Square('d', 5);

        // Then a new Pawn is created in the D5 square.
        Piece movedPawn = assertDoesNotThrow(() -> pawn.moveTo(Board.emptyBoard(), d5));

        assertEquals(d5, movedPawn.position());
    }

    @Test
    @Timeout(value = 5)
    void black_pawn_in_d4_can_move_to_d3() throws IllegalSquareException {
        // Given a black pawn in square D4.
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.BLACK, d4);

        // When it's asked to move to D3
        Square d3 = new Square('d', 3);

        // Then a new Pawn is created in the D3 square.
        Piece movedPawn = assertDoesNotThrow(() -> pawn.moveTo(Board.emptyBoard(), d3));

        assertEquals(d3, movedPawn.position());
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_d4_cannot_move_to_d3() throws IllegalSquareException {
        // Given a white pawn in square D4.
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d4);

        // When it's asked to move to D3
        Square d3 = new Square('d', 3);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> pawn.moveTo(Board.emptyBoard(), d3));
    }

    @Test
    @Timeout(value = 5)
    void black_pawn_in_d4_cannot_move_to_d5() throws IllegalSquareException {
        // Given a white pawn in square D4.
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.BLACK, d4);

        // When it's asked to move to D5
        Square d5 = new Square('d', 5);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> pawn.moveTo(Board.emptyBoard(), d5));
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_d2_cannot_move_to_d4_if_d4_is_occupied() {
        // Given a white pawn in square D2 and a Board with a white pawn in D4.
        Square d2 = new Square('d', 2);
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d2);

        Board board = new Board(new Piece(new Pawn(), Color.WHITE, d4));

        // When it's asked to move to D4
        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> pawn.moveTo(board, d4));
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_d2_cannot_move_to_d3_if_d3_is_occupied() {
        // Given a white pawn in square D2 and a Board with a white pawn in D3.
        Square d2 = new Square('d', 2);
        Square d3 = new Square('d', 3);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d2);

        Board board = new Board(new Piece(new Pawn(), Color.WHITE, d3));

        // When it's asked to move to D3
        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> pawn.moveTo(board, d3));
    }

    @Test
    @Timeout(value = 5)
    void white_pawn_in_d2_cannot_move_to_d4_if_d3_is_occupied() {
        // Given a white pawn in square D2 and a Board with a white pawn in D3.
        Square d2 = new Square('d', 2);
        Square d3 = new Square('d', 3);
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d2);

        Board board = new Board(new Piece(new Pawn(), Color.WHITE, d3));

        // When it's asked to move to D4
        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> pawn.moveTo(board, d4));
    }

    @Test
    @Timeout(value = 5)
    void pawn_path_to_valid_move_is_always_origin_square_and_destination_square() {
        // Given a white pawn in square D4.
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d4);

        // When it's asked for the path to D5
        Square d5 = new Square('d', 5);

        List<Square> path = assertDoesNotThrow(() -> pawn.pathTo(Board.emptyBoard(), d5));
        List<Square> expectedMoves = Lists.newArrayList(
                d4, // current square
                d5 // destination square
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
    void pawn_path_to_invalid_move_throws_exception() {
        // Given a white pawn in square D4.
        Square d4 = new Square('d', 4);

        Piece pawn = new Piece(new Pawn(), Color.WHITE, d4);

        // When it's asked for the path to D1
        Square d1 = new Square('d', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> pawn.pathTo(Board.emptyBoard(), d1));
    }

    private static IntStream columns() {
        return IntStream.rangeClosed('a', 'h');
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
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
