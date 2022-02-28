package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.converters.BoardConverter;
import com.chess_challenge.java_1.dto.BoardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CheckmateTest {

    /*     a   b   c   d   e   f   g   h
       8 |   |   |   |   |   |   |   |   |
       7 |   |   |   |   |   |   |   |   |
       6 |   |   |   |   |   |   |   |   |
       5 |   |   |   |   |   |   |   |   |
       4 |   |   |   |   |   |   |   |   |
       3 |   |   |   |   |   |   |   |   |
       2 |   |   |   |   |   |   |   |   |
       1 |   |   |   |   |   |   |   |   |
           a   b   c   d   e   f   g   h
    */
    @Test
    @Timeout(value = 5)
    void empty_board_has_no_checkmate() {
        Board board = Board.emptyBoard();

        // Then there is no checkmate
        assertFalse(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
       8 |   |   |   |   |   |   |   |   |
       7 |   |   |   |   |   |   |   |   |
       6 |   |   |   |   |   |   |   |   |
       5 |   |   |   |   |   |   |   |   |
       4 |   |   |   |   |   |   |   |   |
       3 |   |   |   |   |   |   |   |   |
       2 |   |   |   |   |   |   |   |   |
       1 |   |   |   |   |whK|   |   |   |
           a   b   c   d   e   f   g   h
    */
    @Test
    @Timeout(value = 5)
    void board_with_one_king_has_no_checkmate() {
        Board board = new Board(
                new King(Color.WHITE, new Square('e', 1))
        );

        // Then there is no checkmate
        assertFalse(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
       8 |   |   |   |   |   |   |   |   |
       7 |   |   |   |   |   |   |   |   |
       6 |   |   |   |   |   |   |   |   |
       5 |   |   |   |   |   |   |   |   |
       4 |   |   |   |   |   |   |   |   |
       3 |   |   |   |   |   |   |   |   |
       2 |whP|whP|   |   |   |   |   |   |
       1 |whK|   |   |   |   |   |   |blQ|
           a   b   c   d   e   f   g   h
    */
    @Test
    @Timeout(value = 5)
    void checkmate_with_queen_king_trapped_by_own_pawns() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Pawn(Color.WHITE, new Square('a', 2)),
                new Pawn(Color.WHITE, new Square('b', 2)),
                new Queen(Color.BLACK, new Square('h', 1))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
       8 |   |   |   |   |   |   |   |   |
       7 |   |   |   |   |   |   |   |   |
       6 |   |   |   |   |   |   |   |   |
       5 |   |   |   |   |   |   |   |   |
       4 |   |   |   |   |   |   |   |   |
       3 |   |   |   |   |   |   |   |   |
       2 |   |whP|   |   |   |   |   |   |
       1 |whK|   |   |   |   |   |   |blQ|
           a   b   c   d   e   f   g   h
    */
    @Test
    @Timeout(value = 5)
    void no_checkmate_if_king_can_take_piece() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Pawn(Color.WHITE, new Square('b', 2)),
                new Queen(Color.BLACK, new Square('h', 1))
        );

        // Then there is no checkmate
        assertFalse(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
      8 |   |   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |   |   |   |   |   |   |
      2 |whP|   |   |   |   |   |   |   |
      1 |whK|blR|   |   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void no_checkmate_if_attacking_piece_can_be_taken_by_king() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.BLACK, new Square('b', 1)),
                new Pawn(Color.WHITE, new Square('a', 2))
        );

        // Then there is no checkmate
        assertFalse(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
      8 |   |   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |   |   |   |   |   |   |
      2 |   |blR|   |   |   |   |   |   |
      1 |whK|blR|   |   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void checkmate_with_2_rooks_near_king() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.BLACK, new Square('b', 2)),
                new Rook(Color.BLACK, new Square('b', 1))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
     8 |   |   |   |   |   |   |   |   |
     7 |   |   |   |   |   |   |   |   |
     6 |   |   |   |   |   |   |   |   |
     5 |   |   |   |   |   |   |   |   |
     4 |   |   |   |   |   |   |   |   |
     3 |   |   |blK|   |   |   |   |   |
     2 |   |   |   |   |   |   |   |   |
     1 |whK|blQ|   |   |   |   |   |   |
         a   b   c   d   e   f   g   h
  */
    @Test
    @Timeout(value = 5)
    void checkmate_with_queen_and_knight() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Knight(Color.BLACK, new Square('c', 3)),
                new Queen(Color.BLACK, new Square('b', 1))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
      8 |   |   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |   |   |   |   |   |   |
      2 |   |blR|whB|   |   |   |   |   |
      1 |whK|blR|   |   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void no_checkmate_if_attacking_piece_can_be_taken() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.BLACK, new Square('b', 2)),
                new Rook(Color.BLACK, new Square('b', 1)),
                new Bishop(Color.WHITE, new Square('c', 2))
        );

        // Then there is no checkmate
        assertFalse(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
      8 |   |   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |blB|   |   |   |   |   |
      2 |whP|whB|   |   |   |   |   |   |
      1 |whK|   |blR|   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void checkmate_if_attacking_piece_cannot_be_taken_due_to_blocked_piece() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Pawn(Color.WHITE, new Square('a', 2)),
                new Bishop(Color.WHITE, new Square('b', 2)),
                new Rook(Color.BLACK, new Square('c', 1)),
                new Bishop(Color.BLACK, new Square('c', 3))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
      8 |   |   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |blK|   |   |   |   |   |   |
      2 |whR|   |blR|   |   |   |   |   |
      1 |whK|whR|blR|   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void checkmate_if_attacking_piece_cannot_be_taken_due_to_blocked_piece_with_knight() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.WHITE, new Square('a', 2)),
                new Rook(Color.WHITE, new Square('b', 1)),
                new Knight(Color.BLACK, new Square('b', 3)),
                new Rook(Color.BLACK, new Square('c', 1)),
                new Rook(Color.BLACK, new Square('c', 2))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    /*     a   b   c   d   e   f   g   h
      8 |   |   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |   |   |   |   |   |   |
      2 |   |blQ|whR|   |   |   |   |   |
      1 |whK|blR|whR|   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void attacking_piece_cannot_be_taken_if_2_pieces_are_checking_the_king() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.BLACK, new Square('b', 1)),
                new Queen(Color.BLACK, new Square('b', 2)),
                new Rook(Color.WHITE, new Square('c', 1)),
                new Rook(Color.WHITE, new Square('c', 2))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    /*    a   b   c   d   e   f   g   h
      8 |   |whR|   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |   |   |   |   |   |   |
      2 |   |   |blR|   |   |   |   |   |
      1 |whK|   |blR|   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void no_checkmate_if_attack_can_be_intercepted() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.WHITE, new Square('b', 8)),
                new Rook(Color.BLACK, new Square('c', 1)),
                new Rook(Color.BLACK, new Square('c', 2))
        );

        // Then there is no checkmate
        assertFalse(board.hasCheckmate());
    }

    /*    a   b   c   d   e   f   g   h
      8 |   |whR|   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |blB|   |   |   |   |   |
      2 |   |   |blR|   |   |   |   |   |
      1 |whK|   |blR|   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void attacking_piece_cannot_be_intercepted_if_2_pieces_are_checking_the_king() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Rook(Color.WHITE, new Square('b', 8)),
                new Rook(Color.BLACK, new Square('c', 1)),
                new Rook(Color.BLACK, new Square('c', 2)),
                new Bishop(Color.BLACK, new Square('c', 3))
        );

        // Then there is no checkmate
        assertTrue(board.hasCheckmate());
    }

    /*    a   b   c   d   e   f   g   h
      8 |blQ|   |   |   |   |   |   |   |
      7 |   |   |   |   |   |   |   |   |
      6 |   |   |   |   |   |   |   |   |
      5 |   |   |   |   |   |   |   |   |
      4 |   |   |   |   |   |   |   |   |
      3 |   |   |   |   |   |   |   |   |
      2 |whB|   |blR|   |   |   |   |   |
      1 |whK|   |blR|   |   |   |   |   |
          a   b   c   d   e   f   g   h
   */
    @Test
    @Timeout(value = 5)
    void checkmate_if_attacking_piece_cannot_be_intercepted_due_to_blocked_piece() {
        // Given a board as the comment above
        Board board = new Board(
                new King(Color.WHITE, new Square('a', 1)),
                new Bishop(Color.WHITE, new Square('a', 2)),
                new Queen(Color.BLACK, new Square('a', 8)),
                new Rook(Color.BLACK, new Square('c', 1)),
                new Rook(Color.BLACK, new Square('c', 2))
        );

        // Then there is a checkmate
        assertTrue(board.hasCheckmate());
    }

    @ParameterizedTest
    @MethodSource("examples")
    @Timeout(value = 5)
    void check_cases_are_not_considered_checkmates(Integer exampleNumber) throws IOException {
        File example = new File(String.format("resources/check-examples/check_example_%d.json", exampleNumber));

        BoardDTO boardDTO = new ObjectMapper().readValue(example, BoardDTO.class);

        BoardConverter converter = new BoardConverter();

        Board board = converter.convertBoard(boardDTO);

        assertFalse(board.hasCheckmate());
    }

    @ParameterizedTest
    @MethodSource("examples")
    @Timeout(value = 20)
    void checkmate_cases_are_considered_checkmates(Integer exampleNumber) throws IOException {
        File example = new File(String.format("resources/checkmate-examples/checkmate_example_%d.json", exampleNumber));

        BoardDTO boardDTO = new ObjectMapper().readValue(example, BoardDTO.class);

        BoardConverter converter = new BoardConverter();

        Board board = converter.convertBoard(boardDTO);

        assertTrue(board.hasCheckmate());
    }

    private static Stream<Arguments> examples() {
        return IntStream.rangeClosed(1, 30).boxed().map(Arguments::of);
    }
}
