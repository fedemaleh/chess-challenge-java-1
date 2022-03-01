package com.chess_challenge.java_1.utils;


import com.chess_challenge.java_1.model.Board;
import com.chess_challenge.java_1.model.Piece;
import com.chess_challenge.java_1.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MovementUtils {
    public static List<Square> generateMovesUntilCondition(Board board,
                                                           Piece currentPiece,
                                                           MoveDirection moveDirection) {
        List<Square> moves = new ArrayList<>();

        Optional<Square> possibleSquare = moveDirection.next(currentPiece.position());

        while (possibleSquare.isPresent()) {
            Square currentSquare = possibleSquare.get();

            Optional<Piece> piece = board.pieceAt(currentSquare);

            if (piece.isPresent()) {
                if (piece.filter(p -> p.color() != currentPiece.color()).isPresent()) {
                    moves.add(currentSquare);
                }

                break;
            } else {
                moves.add(currentSquare);
            }

            possibleSquare = moveDirection.next(currentSquare);
        }

        return moves;
    }

    public static Stream<Square> adjacentSquares(Square square) {
        Optional<Square> back = square.backwardSquare();
        Optional<Square> forward = square.forwardSquare();

        return validSquares(
                square.leftSquare(),
                square.rightSquare(),
                back,
                back.flatMap(Square::leftSquare),
                back.flatMap(Square::rightSquare),
                forward,
                forward.flatMap(Square::leftSquare),
                forward.flatMap(Square::rightSquare)
        );
    }

    @SafeVarargs
    public static Stream<Square> validSquares(Optional<Square>... squares) {
        return Stream.of(squares)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
