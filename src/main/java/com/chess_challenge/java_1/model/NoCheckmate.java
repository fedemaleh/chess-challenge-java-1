package com.chess_challenge.java_1.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NoCheckmate implements BoardStatus {
    @Override
    public Optional<Color> winner() {
        return Optional.empty();
    }

    @Override
    public List<Piece> checkmatePieces() {
        return Collections.emptyList();
    }
}
