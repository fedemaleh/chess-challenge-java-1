package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.dto.ColorDTO;

public class MultipleKingsException extends IllegalBoardException {
    public MultipleKingsException(ColorDTO color) {
        super(String.format("It can only be one %s King", color.name().toLowerCase()));
    }
}
