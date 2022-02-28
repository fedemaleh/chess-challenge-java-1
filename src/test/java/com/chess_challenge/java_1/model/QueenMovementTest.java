package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QueenMovementTest {
    @ParameterizedTest
    @MethodSource("squares")
    @Timeout(value = 5)
    void queen_moves_are_equal_to_rook_and_bishop_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a Queen, a Bishop and  aRook
        Square square = new Square(col, row);

        Piece queen = new Piece(new Queen(), Color.WHITE, square);
        Piece bishop = new Piece(new Bishop(), Color.WHITE, square);
        Piece rook = new Piece(new Rook(), Color.WHITE, square);
        Board board = Board.emptyBoard();

        // The Queen moves are the sum of the moves of the Bishop and the Rook.
        List<Square> queenMoves = queen.moves(board);
        List<Square> bishopMoves = bishop.moves(board);
        List<Square> rookMoves = rook.moves(board);

        List<Square> expectedQueenMoves = new ArrayList<>(bishopMoves);
        expectedQueenMoves.addAll(rookMoves);

        assertEquals(expectedQueenMoves, queenMoves);
    }

    @ParameterizedTest
    @MethodSource("squares")
    @Timeout(value = 5)
    void queen_moves_are_equal_to_attacks_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a Queen
        Square square = new Square(col, row);

        Piece queen = new Piece(new Queen(), Color.WHITE, square);
        Board board = Board.emptyBoard();

        // It's moves should be the same as it's attacks
        List<Square> moves = queen.moves(board);

        List<Square> attacks = queen.attacks(board);

        assertEquals(moves, attacks);
    }

    @Test
    @Timeout(value = 5)
    void queen_in_d4_can_move_to_e4() throws IllegalSquareException {
        // Given a white Queen in square D4.
        Square d4 = new Square('d', 4);

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);

        // When it's asked to move to E4
        Square e4 = new Square('e', 4);

        // Then a new Queen is created in the E4 square.
        Piece movedQueen = assertDoesNotThrow(() -> queen.moveTo(Board.emptyBoard(), e4));

        assertEquals(e4, movedQueen.position());
    }

    @Test
    @Timeout(value = 5)
    void queen_in_d4_cannot_move_to_e6() throws IllegalSquareException {
        // Given a white queen in square D4.
        Square d4 = new Square('d', 4);

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);

        // When it's asked to move to E6
        Square e6 = new Square('e', 6);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> queen.moveTo(Board.emptyBoard(), e6));
    }

    @Test
    @Timeout(value = 5)
    void white_queen_can_move_up_to_the_first_white_piece_in_each_direction_not_included() {
        // Given a white queen in square D4 and a Board with white pieces in D2, D6, B4, F4, B2, B6, F2, F6
        Square d4 = new Square('d', 4);
        Square d2 = new Square('d', 2);
        Square d6 = new Square('d', 6);
        Square b4 = new Square('b', 4);
        Square f4 = new Square('f', 4);
        Square b2 = new Square('b', 2);
        Square b6 = new Square('b', 6);
        Square f2 = new Square('f', 2);
        Square f6 = new Square('f', 6);

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);

        Piece p1 = new Piece(new Pawn(), Color.WHITE, d2);
        Piece p2 = new Piece(new Pawn(), Color.WHITE, d6);
        Piece p3 = new Piece(new Pawn(), Color.WHITE, b4);
        Piece p4 = new Piece(new Pawn(), Color.WHITE, f4);
        Piece p5 = new Piece(new Pawn(), Color.WHITE, b2);
        Piece p6 = new Piece(new Pawn(), Color.WHITE, b6);
        Piece p7 = new Piece(new Pawn(), Color.WHITE, f2);
        Piece p8 = new Piece(new Pawn(), Color.WHITE, f6);

        Board board = new Board(p1, p2, p3, p4, p5, p6, p7, p8);

        // When the queen is asked for his moves
        List<Square> moves = queen.moves(board);

        List<Square> expectedMoves = Lists.newArrayList(
                // Rook moves
                // vertical moves
                new Square('d', 3),
                new Square('d', 5),
                // horizontal moves
                new Square('c', 4),
                new Square('e', 4),
                // Bishop moves
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
    void white_queen_can_move_up_to_the_first_white_piece_included() {
        // Given a white queen in square D4 and a Board with white pieces in D2, D6, B4, F4, B2, B6, F2, F6
        Square d4 = new Square('d', 4);
        Square d2 = new Square('d', 2);
        Square d6 = new Square('d', 6);
        Square b4 = new Square('b', 4);
        Square f4 = new Square('f', 4);
        Square b2 = new Square('b', 2);
        Square b6 = new Square('b', 6);
        Square f2 = new Square('f', 2);
        Square f6 = new Square('f', 6);

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);

        Piece p1 = new Piece(new Pawn(), Color.BLACK, d2);
        Piece p2 = new Piece(new Pawn(), Color.BLACK, d6);
        Piece p3 = new Piece(new Pawn(), Color.BLACK, b4);
        Piece p4 = new Piece(new Pawn(), Color.BLACK, f4);
        Piece p5 = new Piece(new Pawn(), Color.BLACK, b2);
        Piece p6 = new Piece(new Pawn(), Color.BLACK, b6);
        Piece p7 = new Piece(new Pawn(), Color.BLACK, f2);
        Piece p8 = new Piece(new Pawn(), Color.BLACK, f6);

        Board board = new Board(p1, p2, p3, p4, p5, p6, p7, p8);

        // When the queen is asked for his moves
        List<Square> moves = queen.moves(board);

        List<Square> expectedMoves = Lists.newArrayList(
                // Rook Moves
                // vertical moves
                new Square('d', 2),
                new Square('d', 3),
                new Square('d', 5),
                new Square('d', 6),
                // horizontal moves
                new Square('b', 4),
                new Square('c', 4),
                new Square('e', 4),
                new Square('f', 4),
                // Bishop moves
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
    void queen_path_to_lineal_valid_move_is_the_same_as_rook_in_same_position() {
        // Given a white queen and a white rook in square D4 and an empty board.
        Square d4 = new Square('d', 4);
        Board board = Board.emptyBoard();

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);
        Piece rook = new Piece(new Rook(), Color.WHITE, d4);

        // When it's asked for the path to D8
        Square d8 = new Square('d', 8);

        List<Square> queenPath = assertDoesNotThrow(() -> queen.pathTo(board, d8));
        List<Square> rookPath = assertDoesNotThrow(() -> rook.pathTo(board, d8));

        assertEquals(rookPath, queenPath);
    }

    @Test
    @Timeout(value = 5)
    void queen_path_to_diagnoal_valid_move_is_the_same_as_rook_in_same_position() {
        // Given a white queen and a white bishop in square D4 and an empty board.
        Square d4 = new Square('d', 4);
        Board board = Board.emptyBoard();

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);
        Piece bishop = new Piece(new Bishop(), Color.WHITE, d4);

        // When it's asked for the path to H8
        Square h8 = new Square('h', 8);

        List<Square> queenPath = assertDoesNotThrow(() -> queen.pathTo(board, h8));
        List<Square> bishopPath = assertDoesNotThrow(() -> bishop.pathTo(board, h8));

        assertEquals(bishopPath, queenPath);
    }

    @Test
    @Timeout(value = 5)
    void queen_path_to_invalid_move_throws_exception() {
        // Given a white queen in square D4.
        Square d4 = new Square('d', 4);

        Piece queen = new Piece(new Queen(), Color.WHITE, d4);

        // When it's asked for the path to E1
        Square e1 = new Square('e', 1);

        // Then an IllegalMovementException is thrown
        assertThrows(IllegalMovementException.class, () -> queen.pathTo(Board.emptyBoard(), e1));
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
