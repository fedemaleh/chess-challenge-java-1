package com.chess_challenge.java_1.utils;


import com.chess_challenge.java_1.model.Board;
import com.chess_challenge.java_1.model.Piece;
import com.chess_challenge.java_1.model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static List<Square> adjacentSquares(Square square) {
        int minRow = Integer.max(1, square.getRow() - 1);
        int maxRow = Integer.min(8, square.getRow() + 1);

        char minCol = (char) Integer.max('a', square.getColumn() - 1);
        char maxCol = (char) Integer.min('h', square.getColumn() + 1);

        return IntStream.rangeClosed(minRow, maxRow)
                .mapToObj(row ->
                        IntStream.rangeClosed(minCol, maxCol)
                                .mapToObj(col -> new Square((char) col, row))
                )
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }
}
