package com.chess_challenge.java_1.statistics.repositories.hibernate;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class HibernateRepository implements StatisticsRepository {
    private final HibernateWinnersRepo winnersRepo;
    private final HibernatePiecesRepo piecesRepo;

    public HibernateRepository(HibernateWinnersRepo winnersRepo, HibernatePiecesRepo piecesRepo) {
        this.winnersRepo = winnersRepo;
        this.piecesRepo = piecesRepo;
    }

    @Override
    public CompletableFuture<DetectorStatistics> get() {
        CompletableFuture<List<HibernateWinner>> futureWinners = winnersRepo.winners();
        CompletableFuture<List<HibernatePiece>> futurePieces = piecesRepo.pieces();

        return futureWinners.thenCombine(futurePieces, this::mapDbResults)
                .orTimeout(2, TimeUnit.SECONDS);
    }

    private DetectorStatistics mapDbResults(List<HibernateWinner> winnerRecords, List<HibernatePiece> pieceRecords) {
        Map<String, Integer> winnerValues = winnerRecords.stream()
                .collect(Collectors.toMap(
                        HibernateWinner::getWinner,
                        hs -> 1,
                        Integer::sum
                ));

        Map<String, Integer> piecesValues = pieceRecords.stream()
                .collect(Collectors.toMap(
                        HibernatePiece::getPiece,
                        hs -> 1,
                        Integer::sum
                ));

        Map<Color, Integer> winners = Arrays.stream(Color.values())
                .collect(Collectors.toMap(
                        c -> c,
                        c -> winnerValues.getOrDefault(c.name().toLowerCase(), 0)
                ));

        Map<Type, Integer> pieces = Arrays.stream(Type.values())
                .collect(Collectors.toMap(
                        t -> t,
                        t -> piecesValues.getOrDefault(t.name().toLowerCase(), 0)
                ));


        return new DetectorStatistics(winners, pieces);
    }

    @Override
    public void recordWinner(Color color) {
        this.winnersRepo.recordWinner(new HibernateWinner(color.name().toLowerCase()));
    }

    @Override
    public void recordCheckmatePiece(Type type) {
        this.piecesRepo.recordPiece(new HibernatePiece(type.name().toLowerCase()));
    }
}
