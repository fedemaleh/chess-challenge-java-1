package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;

public interface StatisticsRepository {
    /**
     * Gets the current statistics.
     * @return the current statistics.
     */
    DetectorStatistics get();

    /**
     * Records the winner of an analysed board.
     * @param color the color of the winner.
     */
    void recordWinner(Color color);

    /**
     * Records the piece that checkmated in an analysed board.
     * @param type the piece that checkmated.
     */
    void recordCheckmatePiece(Type type);
}
