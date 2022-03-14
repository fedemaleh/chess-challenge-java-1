package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.dto.TypeDTO;

public class IllegalPieceException extends IllegalBoardException {
    public IllegalPieceException(TypeDTO type) {
        super(String.format("No piece called %s exists.", type.name().toLowerCase()));
    }
}
