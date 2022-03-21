package com.chess_challenge.java_1.statistics;

import com.chess_challenge.java_1.converters.StatisticsConverter;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.response.DetectorStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final StatisticsConverter statisticsConverter;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, StatisticsConverter statisticsConverter) {
        this.statisticsService = statisticsService;
        this.statisticsConverter = statisticsConverter;
    }

    @RequestMapping(path = "/checkmate/stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<DetectorStatisticsResponse>> stats() {
        CompletableFuture<DetectorStatistics> futureStats = this.statisticsService.getStatistics();

        return futureStats.orTimeout(2, TimeUnit.SECONDS)
                .thenApply(this.statisticsConverter::convert)
                .thenApply(ResponseEntity::ok)
                .exceptionally((t) -> ResponseEntity.internalServerError().build());
    }
}
