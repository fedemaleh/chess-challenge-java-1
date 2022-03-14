package com.chess_challenge.java_1.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class DetectorStatistics {
    private final Map<Color, Integer> winners;
    private final Map<Type, Integer> checkmatePieces;

    public DetectorStatistics(Map<Color, Integer> winners, Map<Type, Integer> checkmatePieces) {
        this.winners = winners;
        this.checkmatePieces = checkmatePieces;
    }

    public static DetectorStatistics initialStatistics() {
        Map<Color, Integer> winners = Arrays.stream(Color.values()).collect(Collectors.toMap((color) -> color, (color) -> 0));
        Map<Type, Integer> checkmatePieces = Arrays.stream(Type.values()).collect(Collectors.toMap((type) -> type, (type) -> 0));

        return new DetectorStatistics(winners, checkmatePieces);
    }

    public void recordWinner(Color color) {
        this.winners.computeIfPresent(color, (c, winners) -> winners + 1);
    }

    public void recordCheckmatePiece(Type type) {
        this.checkmatePieces.computeIfPresent(type, (t, winners) -> winners + 1);
    }

    public Map<Color, Integer> getWinners() {
        return winners;
    }

    public Map<Type, Integer> getCheckmatePieces() {
        return checkmatePieces;
    }
}
