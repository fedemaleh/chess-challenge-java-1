package com.chess_challenge.java_1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeDTO {
    KING,
    QUEEN,
    ROOK,
    KNIGHT,
    BISHOP,
    PAWN;

    @JsonCreator
    public static TypeDTO fromJson(String name) {
        return valueOf(name.toUpperCase());
    }
}
