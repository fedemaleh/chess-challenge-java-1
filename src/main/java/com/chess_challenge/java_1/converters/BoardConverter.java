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

        return new Piece(this.convertMovementStrategy(piece), color, square);
    }

    private MovementStrategy convertMovementStrategy(PieceDTO piece) {
        switch (piece.getType()) {
            case KING:
                return new King();
            case QUEEN:
                return new Queen();
            case ROOK:
                return new Rook();
            case KNIGHT:
                return new Knight();
            case BISHOP:
                return new Bishop();
            case PAWN:
                return new Pawn();
            default:
                throw new IllegalPieceException(piece.getType());
        }
    }
}
