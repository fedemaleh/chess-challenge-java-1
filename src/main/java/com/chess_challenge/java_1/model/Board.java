package com.chess_challenge.java_1.model;

import java.util.List;
import java.util.Optional;

public class Board {
    private List<Piece> pieces;

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Optional<Piece> pieceAt(Square square) {
        return pieces.stream().filter(piece -> piece.position().equals(square)).findFirst();
    }

//    public boolean isCheckmate() {
//
//    }
}