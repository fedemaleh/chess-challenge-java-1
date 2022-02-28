package com.chess_challenge.java_1.utils;

import com.chess_challenge.java_1.model.Square;

import java.util.Optional;

@FunctionalInterface
public interface MoveDirection {
    Optional<Square> next(Square square);
}
