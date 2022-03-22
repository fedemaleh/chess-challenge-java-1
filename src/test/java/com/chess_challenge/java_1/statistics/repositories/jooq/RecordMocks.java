package com.chess_challenge.java_1.statistics.repositories.jooq;

import com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.PiecesQueryBuilder;
import com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.WinnersQueryBuilder;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.tools.jdbc.MockResult;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecordMocks {
    public static MockResult newPieceResult(DSLContext db, Pair<String, Integer>... values) {
        Result<Record2<String, BigDecimal>> result =
                db.newResult(
                        PiecesQueryBuilder.PIECES_QUERY_BUILDER.PIECE,
                        DSL.sum(PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES).as(PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES)
                );

        return newResult(db, result, values);
    }

    public static MockResult newWinnerResult(DSLContext db, Pair<String, Integer>... values) {
        Result<Record2<String, BigDecimal>> result =
                db.newResult(
                        WinnersQueryBuilder.WINNERS_QUERY_BUILDER.WINNER,
                        DSL.sum(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES).as(WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES)
                );

        return newResult(db, result, values);
    }

    private static MockResult newResult(DSLContext db, Result<Record2<String, BigDecimal>> result, Pair<String, Integer>[] values) {
        for (Pair<String, Integer> value : values) {
            Record2<String, BigDecimal> record =
                    db.newRecord((Field<String>) result.field(0), (Field<BigDecimal>) result.field(1))
                            .values(value.getFirst(), BigDecimal.valueOf(value.getSecond()));

            result.add(record);
        }

        return new MockResult(values.length, result);
    }
}
