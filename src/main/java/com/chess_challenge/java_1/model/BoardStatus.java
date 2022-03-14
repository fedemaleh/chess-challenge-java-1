package com.chess_challenge.java_1.model;

import java.util.List;
import java.util.Optional;

public interface BoardStatus {
    Optional<Color> winner();
    List<Piece> checkmatePieces();
}
