package com.chess_challenge.java_1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PieceDTO {
    private final TypeDTO type;
    private final ColorDTO color;
    private final SquareDTO position;

    @JsonCreator
    public PieceDTO(@JsonProperty("type") TypeDTO type,
                    @JsonProperty("color") ColorDTO color,
                    @JsonProperty("position") SquareDTO position) {
        this.type = type;
        this.color = color;
        this.position = position;
    }

    public TypeDTO getType() {
        return type;
    }

    public ColorDTO getColor() {
        return color;
    }

    public SquareDTO getPosition() {
        return position;
    }
}
