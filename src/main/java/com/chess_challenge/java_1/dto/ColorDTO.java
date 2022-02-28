package com.chess_challenge.java_1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ColorDTO {
    WHITE,
    BLACK;

    @JsonCreator
    public static ColorDTO fromJson(String name) {
        return valueOf(name.toUpperCase());
    }
}
