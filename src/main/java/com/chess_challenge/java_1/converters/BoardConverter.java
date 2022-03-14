package com.chess_challenge.java_1.converters;

import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.dto.PieceDTO;
import com.chess_challenge.java_1.model.*;
import com.chess_challenge.java_1.validators.BoardValidator;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardConverter {
    private final BoardValidator boardValidator;

    @Autowired
    public BoardConverter(BoardValidator boardValidator) {
        this.boardValidator = boardValidator;
    }

    public Either<Board, List<IllegalBoardException>> convertBoard(BoardDTO dto) {
        return this.boardValidator.validate(dto)
                .mapLeft(BoardDTO::getPieces)
                .mapLeft(this::convertPieces)
                .mapLeft(Board::new);
    }

    private List<Piece> convertPieces(List<PieceDTO> pieces) {
        return pieces.stream()
                .map(this::convertPiece)
                .collect(Collectors.toList());
    }

    private Piece convertPiece(PieceDTO piece) {
        Color color = Color.valueOf(piece.getColor().name());
        MovementStrategy type = this.convertMovementStrategy(piece);
        Square position = new Square(piece.getPosition().getColumn(), piece.getPosition().getRow());

        return new Piece(type, color, position);
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
