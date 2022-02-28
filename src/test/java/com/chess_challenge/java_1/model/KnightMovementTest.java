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

        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves(Board.emptyBoard());

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

        Piece knight = new Piece(new Knight(), Color.WHITE, a1);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves(Board.emptyBoard());

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

        Piece knight = new Piece(new Knight(), Color.WHITE, h1);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves(Board.emptyBoard());

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

        Piece knight = new Piece(new Knight(), Color.WHITE, a8);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves(Board.emptyBoard());

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
        Square h8 = new Square('h', 8);

        Piece knight = new Piece(new Knight(), Color.WHITE, h8);

        // When the knight is asked for his moves
        List<Square> moves = knight.moves(Board.emptyBoard());

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

        Piece knight = new Piece(new Knight(), Color.WHITE, square);
        Board board = Board.emptyBoard();

        // It's moves should be the same as it's attacks
        List<Square> moves = knight.moves(board);

        List<Square> attacks = knight.attacks(board);

        assertEquals(moves, attacks);
    }

    @Test
    @Timeout(value = 5)
    void knight_in_d4_can_move_to_e6() throws IllegalSquareException {
        // Given a white knight in square D4.
        Square d4 = new Square('d', 4);

        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        // When it's asked to move to E6
        Square e6 = new Square('e', 6);

        // Then a new Knight is created in the E6 square.
        Piece movedKnight = assertDoesNotThrow(() -> knight.moveTo(Board.emptyBoard(), e6));

        assertEquals(e6, movedKnight.position());
    }

    @Test
    @Timeout(value = 5)
    void knight_in_d4_cannot_move_to_e4() throws IllegalSquareException {
        // Given a white knight in square D4.
        Square d4 = new Square('d', 4);

        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        // When it's asked to move to E4
        Square e4 = new Square('e', 4);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> knight.moveTo(Board.emptyBoard(), e4));
    }

    @Test
    @Timeout(value = 5)
    void white_knight_in_d4_can_move_to_e6_if_square_is_empty() {
        // Given a white knight in square D4 and an empty Board.
        Square d4 = new Square('d', 4);
        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        Board board = Board.emptyBoard();

        // When it's asked to move to E6
        Square e6 = new Square('e', 6);

        // Then a new Knight is created in the E6 square.
        Piece movedKnight = assertDoesNotThrow(() -> knight.moveTo(board, e6));

        assertEquals(e6, movedKnight.position());
    }

    @Test
    @Timeout(value = 5)
    void white_knight_in_d4_can_move_to_e6_if_square_has_black_piece() {
        // Given a white knight in square D4 and a Board with a black Pawn in D4.
        Square d4 = new Square('d', 4);
        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        Square e6 = new Square('e', 6);
        Piece pawn = new Piece(new Pawn(), Color.BLACK, e6);

        Board board = new Board(pawn);

        // When it's asked to move to E6
        // Then a new Knight is created in the E6 square.
        Piece movedKnight = assertDoesNotThrow(() -> knight.moveTo(board, e6));

        assertEquals(e6, movedKnight.position());
    }

    @Test
    @Timeout(value = 5)
    void white_knight_in_d4_cannot_move_to_e6_if_square_has_white_piece() {
        // Given a white knight in square D4 and a Board with a white Pawn in D4.
        Square d4 = new Square('d', 4);
        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        Square e6 = new Square('e', 6);
        Piece pawn = new Piece(new Pawn(), Color.WHITE, e6);

        Board board = new Board(pawn);

        // When it's asked to move to E6
        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> knight.moveTo(board, e6));
    }

    @Test
    @Timeout(value = 5)
    void knight_path_to_valid_move_is_always_origin_square_and_destination_square() {
        // Given a white knight in square D4.
        Square d4 = new Square('d', 4);

        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        // When it's asked for the path to E6
        Square e6 = new Square('e', 6);

        List<Square> path = assertDoesNotThrow(() -> knight.pathTo(Board.emptyBoard(), e6));
        List<Square> expectedMoves = Lists.newArrayList(
                d4, // current square
                e6 // destination square
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
    void knight_path_to_invalid_move_throws_exception() {
        // Given a white knight in square D4.
        Square d4 = new Square('d', 4);

        Piece knight = new Piece(new Knight(), Color.WHITE, d4);

        // When it's asked for the path to D1
        Square d1 = new Square('d', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> knight.pathTo(Board.emptyBoard(), d1));
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
