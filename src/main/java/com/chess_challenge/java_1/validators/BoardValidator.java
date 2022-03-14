package com.chess_challenge.java_1.validators;

import com.chess_challenge.java_1.dto.BoardDTO;
import com.chess_challenge.java_1.dto.ColorDTO;
import com.chess_challenge.java_1.dto.PieceDTO;
import com.chess_challenge.java_1.dto.TypeDTO;
import com.chess_challenge.java_1.model.*;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BoardValidator {
    public Either<BoardDTO, List<IllegalBoardException>> validate(BoardDTO boardDTO) {
        List<IllegalBoardException> boardErrors = this.validatePieces(boardDTO.getPieces());

        boardErrors.addAll(this.validateKings(boardDTO.getPieces()));

        if (boardErrors.isEmpty()) {
            return Either.left(boardDTO);
        } else {
            return Either.right(boardErrors);
        }
    }

    private List<IllegalBoardException> validatePieces(List<PieceDTO> pieces) {
        return pieces.stream()
                .filter(piece -> !Square.validSquare(piece.getPosition().getColumn(), piece.getPosition().getRow()))
                .map(piece -> new IllegalSquareException(piece.getPosition().getColumn(), piece.getPosition().getRow()))
                .collect(Collectors.toList());
    }

    private List<IllegalBoardException> validateKings(List<PieceDTO> pieces) {
        Optional<IllegalBoardException> whiteKingError = this.validateKings(pieces, ColorDTO.WHITE);
        Optional<IllegalBoardException> blackKingError = this.validateKings(pieces, ColorDTO.BLACK);

        return Stream.of(whiteKingError, blackKingError)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<IllegalBoardException> validateKings(List<PieceDTO> pieces, ColorDTO color) {
        long kings = this.countKings(pieces, color);

        if (kings == 0) {
            return Optional.of(new MissingKingException(color));
        } else if (kings > 1) {
            return Optional.of(new MultipleKingsException(color));
        }

        return Optional.empty();
    }

    private long countKings(List<PieceDTO> pieces, ColorDTO color) {
        return pieces.stream()
                .filter(piece -> piece.getType() == TypeDTO.KING)
                .filter(piece -> piece.getColor() == color)
                .count();
    }
}
