package com.chess_challenge.java_1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SquareDTO {
    private final char column;
    private final int row;

    @JsonCreator
    public SquareDTO(@JsonProperty("column") char column,
                     @JsonProperty("row") int row) {
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
