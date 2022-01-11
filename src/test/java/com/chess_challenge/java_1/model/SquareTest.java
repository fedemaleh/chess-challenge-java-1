package com.chess_challenge.java_1.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    @ParameterizedTest
    @MethodSource("rows")
    void squares_are_valid_if_row_is_between_1_and_8(int row) {
        if (row >= 1 && row <= 8) {
            assertDoesNotThrow(() -> new Square('a', row));
        } else{
            assertThrows(IllegalSquareException.class, () -> new Square('a', row));
        }
    }

    @ParameterizedTest
    @MethodSource("columns")
    void squares_are_valid_if_column_is_between_a_and_h(int col) {
        char column = (char) col;
        if (column >= 'a' && column <= 'h') {
            assertDoesNotThrow(() -> new Square(column, 1));
        } else{
            assertThrows(IllegalSquareException.class, () -> new Square(column, 1));
        }
    }

    static IntStream rows() {
        return IntStream.rangeClosed(10, 10);
    }

    static IntStream columns() {
        return IntStream.rangeClosed('a', 'z');
    }
}