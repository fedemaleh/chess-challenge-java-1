package com.chess_challenge.java_1.converters;

import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.dto.PieceDTO;
import com.chess_challenge.java_1.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class BoardConverter {

    public Board convertBoard(BoardDTO dto) {
        List<Piece> pieces = dto.getPieces()
                .stream()
                .map(this::convertPiece)
                .collect(Collectors.toList());

        return new Board(pieces);
    }

    private Piece convertPiece(PieceDTO piece) {
        Color color = Color.valueOf(piece.getColor().name());
        Square square = new Square(piece.getSquare().getColumn(), piece.getSquare().getRow());

        switch (piece.getType()) {
            case KING:
                return new King(color, square);
            case QUEEN:
                return new Queen(color, square);
            case ROOK:
                return new Rook(color, square);
            case KNIGHT:
                return new Knight(color, square);
            case BISHOP:
                return new Bishop(color, square);
            case PAWN:
                return new Pawn(color, square);
            default:
                throw new IllegalPieceException(piece.getType());
        }
    }
}
