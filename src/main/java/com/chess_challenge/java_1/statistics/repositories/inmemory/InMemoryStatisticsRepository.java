package com.chess_challenge.java_1.statistics.repositories.inmemory;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class InMemoryStatisticsRepository implements StatisticsRepository {
    private final InMemoryStatistics stats;

    public InMemoryStatisticsRepository() {
        this.stats = InMemoryStatistics.initialStatistics();
    }

    public CompletableFuture<DetectorStatistics> get() {
        DetectorStatistics currentStats = new DetectorStatistics(this.stats.getWinners(), this.stats.getCheckmatePieces());

        return CompletableFuture.completedFuture(currentStats);
    }

    public void recordWinner(Color color) {
        this.stats.recordWinner(color);
    }

    public void recordCheckmatePiece(Type type) {
        this.stats.recordCheckmatePiece(type);
    }
}
