package com.chess_challenge.java_1.model;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueenMovementTest {
    @ParameterizedTest
    @MethodSource("squares")
    @Timeout(value = 5)
    void queen_moves_are_equal_to_rook_and_bishop_for_all_squares(char col, int row) throws IllegalSquareException {
        // Given a Queen, a Bishop and  aRook
        Square square = new Square(col, row);

        Queen queen = new Queen(Color.WHITE, square);
        Bishop bishop = new Bishop(Color.WHITE, square);
        Rook rook = new Rook(Color.WHITE, square);

        // The Queen moves are the sum of the moves of the Bishop and the Rook.
        List<Square> queenMoves = queen.moves();
        List<Square> bishopMoves = bishop.moves();
        List<Square> rookMoves = rook.moves();

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

        Queen queen = new Queen(Color.WHITE, square);

        // It's moves should be the same as it's attacks
        List<Square> moves = queen.moves();

        List<Square> attacks = queen.attacks();

        assertEquals(moves, attacks);
    }

    private static Stream<Arguments> squares() {
        List<Character> columns = Lists.newArrayList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

        return IntStream.rangeClosed(1, 8)
                .mapToObj(row -> columns.stream().map(col -> Arguments.of(col, row)))
                .flatMap(Function.identity());
    }
}
