package com.chess_challenge.java_1.statistics.repositories.jooq;

import com.chess_challenge.java_1.model.Color;
import com.chess_challenge.java_1.model.DetectorStatistics;
import com.chess_challenge.java_1.model.Type;
import com.chess_challenge.java_1.statistics.repositories.StatisticsRepository;
import com.chess_challenge.java_1.statistics.repositories.jooq.db.Tables;
import com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.PiecesQueryBuilder;
import com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.WinnersQueryBuilder;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class JooqRepository implements StatisticsRepository {
    private final DSLContext db;

    public JooqRepository(DSLContext db) {
        this.db = db;
    }

    @Override
    public CompletableFuture<DetectorStatistics> get() {
        CompletableFuture<Map<Color, Integer>> futureWinners = this.fetchWinners();
        CompletableFuture<Map<Type, Integer>> futurePieces = this.fetchPieces();

        return futureWinners.thenCombine(futurePieces, DetectorStatistics::new);
    }

    private CompletableFuture<Map<Color, Integer>> fetchWinners() {
        return db.select(
                WinnersQueryBuilder.WINNERS_QUERY_BUILDER.WINNER,
                DSL.sum(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES).as(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES)
        )
                .from(Tables.WINNERS_QUERY_BUILDER)
                .groupBy(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.WINNER)
                .fetchAsync()
                .thenApply(results ->
                        results.stream().collect(
                                Collectors.toMap(
                                        row -> Color.valueOf(row.value1().toUpperCase()),
                                        row -> row.value2().intValue()
                                )
                        )
                )
                .toCompletableFuture();
    }

    private CompletableFuture<Map<Type, Integer>> fetchPieces() {
        return db.select(
                PiecesQueryBuilder.PIECES_QUERY_BUILDER.PIECE,
                DSL.sum(PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES).as(PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES)
        )
                .from(Tables.PIECES_QUERY_BUILDER)
                .groupBy(PiecesQueryBuilder.PIECES_QUERY_BUILDER.PIECE)
                .fetchAsync()
                .thenApply(results ->
                        results.stream().collect(
                                Collectors.toMap(
                                        row -> Type.valueOf(row.value1().toUpperCase()),
                                        row -> row.value2().intValue()
                                )
                        )
                )
                .toCompletableFuture();
    }

    @Override
    public void recordWinner(Color color) {
        db.update(Tables.WINNERS_QUERY_BUILDER)
                .set(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES, WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES.plus(1))
                .where(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.WINNER.eq(color.name().toLowerCase()))
                .executeAsync();
    }

    @Override
    public void recordCheckmatePiece(Type type) {
        db.update(Tables.PIECES_QUERY_BUILDER)
                .set(PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES, PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES.plus(1))
                .where(PiecesQueryBuilder.PIECES_QUERY_BUILDER.PIECE.eq(type.name().toLowerCase()))
                .executeAsync();
    }
}
