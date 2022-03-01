package com.chess_challenge.java_1.converters;

import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.dto.PieceDTO;
import com.chess_challenge.java_1.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        return switch (piece.getType()) {
            case KING -> new King();
            case QUEEN -> new Queen();
            case ROOK -> new Rook();
            case KNIGHT -> new Knight();
            case BISHOP -> new Bishop();
            case PAWN -> new Pawn();
        };
    }
}
