package com.chess_challenge.java_1.statistics;

import com.chess_challenge.java_1.model.BoardStatus;
import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Piece;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    private final StatisticsRepository repository;

    public StatisticsService(StatisticsRepository repository) {
        this.repository = repository;
    }

    public DetectorStatistics getStatistics() {
        return repository.get();
    }

    public void recordResults(BoardStatus status) {
        Color winner = status.winner().orElse(Color.NO_COLOR);

        this.repository.recordWinner(winner);

        List<Piece> checkmatePieces = status.checkmatePieces();

        checkmatePieces.forEach(piece -> this.repository.recordCheckmatePiece(piece.type().type()));
    }
}
