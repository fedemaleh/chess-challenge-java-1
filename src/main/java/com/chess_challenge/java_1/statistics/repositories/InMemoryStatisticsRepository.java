package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStatisticsRepository implements StatisticsRepository {
    private final DetectorStatistics stats;

    public InMemoryStatisticsRepository() {
        this.stats = DetectorStatistics.initialStatistics();
    }

    public DetectorStatistics get() {
        return stats;
    }

    public void recordWinner(Color color) {
        this.stats.recordWinner(color);
    }

    public void recordCheckmatePiece(Type type) {
        this.stats.recordCheckmatePiece(type);
    }
}
