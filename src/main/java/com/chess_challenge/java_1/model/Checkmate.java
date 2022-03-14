package com.chess_challenge.java_1.model;

import java.util.List;
import java.util.Optional;

public class Checkmate implements BoardStatus {
    private final List<Piece> checkmatePieces;

    public Checkmate(List<Piece> checkmatePieces) {
        this.checkmatePieces = checkmatePieces;
    }

    @Override
    public Optional<Color> winner() {
        return this.checkmatePieces.stream()
                .findFirst()
                .map(Piece::color);
    }

    @Override
    public List<Piece> checkmatePieces() {
        return this.checkmatePieces;
    }
}
