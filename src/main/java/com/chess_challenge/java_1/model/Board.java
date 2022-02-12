package com.chess_challenge.java_1.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
    private List<Piece> pieces;

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Board(Piece... pieces) {
        this.pieces = Arrays.asList(pieces);
    }

    public static Board emptyBoard() {
        return new Board(new ArrayList<>());
    }

    public Optional<Piece> pieceAt(Square square) {
        return pieces.stream().filter(piece -> piece.position().equals(square)).findFirst();
    }

    public boolean isThreatened(Square square, Piece currentPiece) {
        // This temp board is necessary so the defending pieces can find the square with the friendly piece
        Board boardWithoutPieceAtSquare = new Board(
                this.pieces.stream()
                        .filter(piece -> !piece.position().equals(square))
                        .collect(Collectors.toList())
        );


        return pieces.stream().anyMatch(piece -> !piece.equals(currentPiece) &&
                piece.color() != currentPiece.color() &&
                piece.attacks(boardWithoutPieceAtSquare, square) && this.pieceCanBeMoved(piece)
        );
    }

    public boolean hasCheckmate() {
        return pieces.stream()
                .anyMatch(piece -> piece.type() == Type.KING && this.hasCheckmate(piece));
    }

    private boolean hasCheckmate(Piece king) {
        /* Checkmate rules:
            1. There is a piece attacking the king.
            2. The king can't move.
            3. No piece can take the attacking piece.
            4. No piece can block the attack.
         */

        List<Piece> piecesThatThreatenKing = pieces.stream().filter(piece -> piece.color() != king.color() &&
                piece.attacks(this, king.position())).collect(Collectors.toList());

        return piecesThatThreatenKing.stream().anyMatch(piece -> piecesThatThreatenKing.size() > 1 || !this.canBeTaken(piece)) &&
                king.moves(this).isEmpty();
    }

    private boolean canBeTaken(Piece piece) {
        return this.isThreatened(piece.position(), piece);
    }

    private boolean pieceCanBeMoved(Piece piece) {
        Optional<Piece> king = this.pieces.stream()
                .filter(currentPiece -> currentPiece.type() == Type.KING && piece.color() == currentPiece.color())
                .findFirst();

        if (!king.isPresent()) {
            return true;
        }

        // This temp board is necessary to verify if moving the piece will add any threat to the king
        Board boardWithoutPieceAtSquare = new Board(
                this.pieces.stream()
                        .filter(currentPiece -> !currentPiece.equals(piece))
                        .collect(Collectors.toList())
        );

        return this.pieces.stream()
                .filter(currentPiece -> currentPiece.attacks(this, king.get().position()))
                .count() ==
                boardWithoutPieceAtSquare.pieces.stream()
                        .filter(currentPiece -> currentPiece.attacks(boardWithoutPieceAtSquare, king.get().position()))
                        .count();
    }
}
