package com.chess_challenge.java_1.utils;

import com.chess_challenge.java_1.model.Board;
import com.chess_challenge.java_1.model.Piece;
import com.chess_challenge.java_1.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class MovementUtils {
    public static List<Square> generateMovesUntilCondition(Board board,
                                                           Piece currentPiece,
                                                           Predicate<Square> loopCondition,
                                                           Function<Square, Optional<Square>> nextSquare) {
        List<Square> moves = new ArrayList<>();

        Optional<Square> possibleSquare = nextSquare.apply(currentPiece.position());

        if (!possibleSquare.isPresent()) {
            return moves;
        }

        Square currentSquare = possibleSquare.get();

        while (loopCondition.test(currentSquare)) {
            Optional<Piece> piece = board.pieceAt(currentSquare);

            if (piece.isPresent()) {
                if (piece.filter(p -> p.color() != currentPiece.color()).isPresent()) {
                    moves.add(currentSquare);
                }

                break;
            } else {
                moves.add(currentSquare);
            }

            possibleSquare = nextSquare.apply(currentSquare);

            if (!possibleSquare.isPresent()) {
                break;
            }

            currentSquare = possibleSquare.get();
        }

        return moves;
    }
}
