package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.dto.ColorDTO;

public class MissingKingException extends IllegalBoardException {
    public MissingKingException(ColorDTO color) {
        super(String.format("Missing %s King", color.name().toLowerCase()));
    }
}
