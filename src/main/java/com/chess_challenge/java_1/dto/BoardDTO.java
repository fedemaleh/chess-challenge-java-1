package com.chess_challenge.java_1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BoardDTO {
    private final List<PieceDTO> pieces;

    @JsonCreator
    public BoardDTO(@JsonProperty("pieces") List<PieceDTO> pieces) {
        this.pieces = pieces;
    }

    public List<PieceDTO> getPieces() {
        return pieces;
    }
}
