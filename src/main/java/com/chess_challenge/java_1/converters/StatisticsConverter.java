package com.chess_challenge.java_1.converters;

import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.response.DetectorStatisticsResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsConverter {
    public DetectorStatisticsResponse convert(DetectorStatistics stats) {
        Map<String, Integer> winners = this.convertMap(stats.getWinners());
        Map<String, Integer> checkmatePieces = this.convertMap(stats.getCheckmatePieces());

        return new DetectorStatisticsResponse(winners, checkmatePieces);
    }

    private Map<String, Integer> convertMap(Map<? extends Enum<?>, Integer> statsMap) {
        return statsMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name().toLowerCase(),
                        Map.Entry::getValue
                ));
    }
}
