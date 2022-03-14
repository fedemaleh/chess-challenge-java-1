package com.chess_challenge.java_1.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DetectorStatisticsResponse {
    private final Map<String, Integer> winners;
    private final Map<String, Integer> checkmatePieces;

    public DetectorStatisticsResponse(Map<String, Integer> winners, Map<String, Integer> checkmatePieces) {
        this.winners = winners;
        this.checkmatePieces = checkmatePieces;
    }

    public Map<String, Integer> getWinners() {
        return winners;
    }

    public Map<String, Integer> getCheckmatePieces() {
        return checkmatePieces;
    }
}
