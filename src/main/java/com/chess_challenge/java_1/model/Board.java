package com.chess_challenge.java_1.model;

import com.chess_challenge.java_1.utils.MovementUtils;

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

    public boolean emptySquare(Square square) {
        return !this.pieceAt(square).isPresent();
    }

    public boolean isThreatened(Square square, Piece currentPiece) {
        // This temp board is necessary so the defending pieces can find the square with the friendly piece
        Board boardWithoutPieceAtSquare = new Board(
                this.pieces.stream()
                        .filter(piece -> !piece.position().equals(square))
                        .filter(piece -> !piece.equals(currentPiece))
                        .collect(Collectors.toList())
        );


        return pieces.stream().anyMatch(piece -> this.canTakePiece(boardWithoutPieceAtSquare, currentPiece, piece, square));
    }

    private boolean canTakePiece(Board board, Piece pieceToTake, Piece piece, Square square) {
        return !piece.equals(pieceToTake) &&
                piece.color() != pieceToTake.color() &&
                (!(piece.type() instanceof King) || !(pieceToTake.type() instanceof King)) && // Kings cannot threaten each other
                piece.attacks(board, square) && this.pieceCanBeMoved(piece);
    }

    public boolean hasCheckmate() {
        return pieces.stream()
                .anyMatch(piece -> piece.type() instanceof King && this.hasCheckmate(piece));
    }

    private boolean hasCheckmate(Piece king) {
        /* Checkmate rules:
            1. There is a piece attacking the king.
            2. The king can't move.
            3. No piece can take the attacking piece.
            4. No piece can block the attack.
         */

        List<Piece> piecesThatThreatenKing = pieces.stream()
                .filter(piece -> !(piece.type() instanceof King))
                .filter(piece -> piece.color() != king.color())
                .filter(piece -> piece.attacks(this, king.position()))
                .collect(Collectors.toList());

        return piecesThatThreatenKing.stream()
                .anyMatch(piece -> piecesThatThreatenKing.size() > 1 ||
                        (!this.canBeTaken(piece) && !this.canBeIntercepted(king, piece))
                ) &&
                king.moves(this).isEmpty();
    }

    private boolean canBeTaken(Piece piece) {
        return this.isThreatened(piece.position(), piece);
    }

    private boolean pieceCanBeMoved(Piece piece) {
        Optional<Piece> king = this.pieces.stream()
                .filter(currentPiece -> currentPiece.type() instanceof King && piece.color() == currentPiece.color())
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
                .filter(currentPiece -> !currentPiece.equals(piece))
                .filter(currentPiece -> !(currentPiece.type() instanceof King))
                .filter(currentPiece -> currentPiece.attacks(this, king.get().position()))
                .count() ==
                boardWithoutPieceAtSquare.pieces.stream()
                        .filter(currentPiece -> currentPiece.attacks(boardWithoutPieceAtSquare, king.get().position()))
                        .count();
    }

    private boolean canBeIntercepted(Piece king, Piece attacker) {
        List<Square> possibleInterceptionSquares = attacker.pathTo(this, king.position());

        possibleInterceptionSquares.remove(king.position()); // king cannot be displaced to be protected
        possibleInterceptionSquares.remove(attacker.position()); // taking the attacking piece is already handled

        return pieces.stream()
                .filter(piece -> piece.color() == king.color()) // piece is friendly to king
                .filter(this::pieceCanBeMoved) // is not blocked by an attacking piece
                .anyMatch(piece -> possibleInterceptionSquares.stream().anyMatch(square -> piece.moves(this).contains(square)));
    }

    public boolean validKingMove(Piece king, Square move) {
        List<Square> adjacentSquares = MovementUtils.adjacentSquares(move);

        return pieces.stream()
                .filter(piece -> piece.type() instanceof King)
                .filter(piece -> piece.color() != king.color())
                .noneMatch(piece -> adjacentSquares.contains(piece.position()));

    }
}
