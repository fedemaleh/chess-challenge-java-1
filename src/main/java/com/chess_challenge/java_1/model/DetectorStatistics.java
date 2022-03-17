package com.chess_challenge.java_1.model;

import java.util.Map;

public class DetectorStatistics {
    private final Map<Color, Integer> winners;
    private final Map<Type, Integer> checkmatePieces;

    public DetectorStatistics(Map<Color, Integer> winners, Map<Type, Integer> checkmatePieces) {
        this.winners = winners;
        this.checkmatePieces = checkmatePieces;
    }

    public Map<Color, Integer> getWinners() {
        return winners;
    }

    public Map<Type, Integer> getCheckmatePieces() {
        return checkmatePieces;
    }
}
