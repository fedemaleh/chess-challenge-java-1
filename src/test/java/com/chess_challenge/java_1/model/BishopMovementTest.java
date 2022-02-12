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
        List<Square> moves = bishop.moves(Board.emptyBoard());

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
        List<Square> moves = bishop.moves(Board.emptyBoard());

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
        List<Square> moves = bishop.moves(Board.emptyBoard());

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
        List<Square> moves = bishop.moves(Board.emptyBoard());

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
        List<Square> moves = bishop.moves(Board.emptyBoard());

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
        Board board = Board.emptyBoard();

        // It's moves should be the same as it's attacks
        List<Square> moves = bishop.moves(board);

        List<Square> attacks = bishop.attacks(board);

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

        // Then a new Bishop is created in the E5 square.
        Bishop movedBishop = assertDoesNotThrow(() -> bishop.moveTo(Board.emptyBoard(), e5));

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
        assertThrows(IllegalMovementException.class, () -> bishop.moveTo(Board.emptyBoard(), e4));
    }

    @Test
    @Timeout(value = 5)
    void white_bishop_can_move_up_to_the_first_white_piece_in_each_direction_not_included() {
        // Given a white bishop in square D4 and a Board with white pieces in B2, B6, F2, F6
        Square d4 = new Square('d', 4);
        Square b2 = new Square('b', 2);
        Square b6 = new Square('b', 6);
        Square f2 = new Square('f', 2);
        Square f6 = new Square('f', 6);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        Piece p1 = new Pawn(Color.WHITE, b2);
        Piece p2 = new Pawn(Color.WHITE, b6);
        Piece p3 = new Pawn(Color.WHITE, f2);
        Piece p4 = new Pawn(Color.WHITE, f6);

        Board board = new Board(Lists.newArrayList(p1, p2, p3, p4));

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves(board);

        List<Square> expectedMoves = Lists.newArrayList(
                // vertical moves
                new Square('c', 3),
                new Square('c', 5),
                // horizontal moves
                new Square('e', 3),
                new Square('e', 5)
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
    void white_bishop_can_move_up_to_the_first_white_piece_included() {
        // Given a white bishop in square D4 and a Board with black pieces in B2, B6, F2, F6
        Square d4 = new Square('d', 4);
        Square b2 = new Square('b', 2);
        Square b6 = new Square('b', 6);
        Square f2 = new Square('f', 2);
        Square f6 = new Square('f', 6);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        Piece p1 = new Pawn(Color.BLACK, b2);
        Piece p2 = new Pawn(Color.BLACK, b6);
        Piece p3 = new Pawn(Color.BLACK, f2);
        Piece p4 = new Pawn(Color.BLACK, f6);

        Board board = new Board(Lists.newArrayList(p1, p2, p3, p4));

        // When the bishop is asked for his moves
        List<Square> moves = bishop.moves(board);

        List<Square> expectedMoves = Lists.newArrayList(
                // vertical moves
                new Square('b', 2),
                new Square('c', 3),
                new Square('c', 5),
                new Square('b', 6),
                // horizontal moves
                new Square('f', 2),
                new Square('e', 3),
                new Square('e', 5),
                new Square('f', 6)
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
    void bishop_path_to_forward_valid_move_is_always_origin_square_and_destination_square_and_middle_squares() {
        // Given a white bishop in square D4.
        Square d4 = new Square('d', 4);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        // When it's asked for the path to H8
        Square h8 = new Square('h', 8);

        List<Square> path = assertDoesNotThrow(() -> bishop.pathTo(Board.emptyBoard(), h8));

        Square e5 = new Square('e', 5);
        Square f6 = new Square('f', 6);
        Square g7 = new Square('g', 7);

        List<Square> expectedMoves = Lists.newArrayList(
                d4, // current square
                // intermediate squares
                e5, f6, g7,
                h8 // destination square
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
    void bishop_path_to_backward_valid_move_is_always_origin_square_and_destination_square_and_middle_squares() {
        // Given a white bishop in square D4.
        Square d4 = new Square('d', 4);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        // When it's asked for the path to A1
        Square a1 = new Square('a', 1);

        List<Square> path = assertDoesNotThrow(() -> bishop.pathTo(Board.emptyBoard(), a1));

        Square b2 = new Square('b', 2);
        Square c3 = new Square('c', 3);

        List<Square> expectedMoves = Lists.newArrayList(
                d4, // current square
                // intermediate squares
                c3, b2,
                a1 // destination square
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
    void bishop_path_to_invalid_move_throws_exception() {
        // Given a white bishop in square D4.
        Square d4 = new Square('d', 4);

        Bishop bishop = new Bishop(Color.WHITE, d4);

        // When it's asked for the path to E1
        Square e1 = new Square('e', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> bishop.pathTo(Board.emptyBoard(), e1));
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
