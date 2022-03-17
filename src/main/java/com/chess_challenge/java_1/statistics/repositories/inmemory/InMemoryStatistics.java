package com.chess_challenge.java_1.statistics.repositories.inmemory;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.Type;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryStatistics {
    private final Map<Color, Integer> winners;
    private final Map<Type, Integer> checkmatePieces;

    public InMemoryStatistics(Map<Color, Integer> winners, Map<Type, Integer> checkmatePieces) {
        this.winners = winners;
        this.checkmatePieces = checkmatePieces;
    }

    public static InMemoryStatistics initialStatistics() {
        Map<Color, Integer> winners = Arrays.stream(Color.values()).collect(Collectors.toMap((color) -> color, (color) -> 0));
        Map<Type, Integer> checkmatePieces = Arrays.stream(Type.values()).collect(Collectors.toMap((type) -> type, (type) -> 0));

        return new InMemoryStatistics(winners, checkmatePieces);
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
