package com.chess_challenge.java_1.statistics.repositories.hibernate;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;

import java.util.concurrent.CompletableFuture;

public class HibernateRepository implements StatisticsRepository {
    @Override
    public CompletableFuture<DetectorStatistics> get() {
        return null;
    }

    @Override
    public void recordWinner(Color color) {

    }

    @Override
    public void recordCheckmatePiece(Type type) {

    }
}
