/*
 * This file is generated by jOOQ.
 */
package com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.records;


import com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.WinnersQueryBuilder;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WinnersQueryBuilderRecord extends UpdatableRecordImpl<WinnersQueryBuilderRecord> implements Record2<String, Integer> {

    private static final long serialVersionUID = 337380747;

    /**
     * Setter for <code>public.winners_query_builder.winner</code>.
     */
    public WinnersQueryBuilderRecord setWinner(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.winners_query_builder.winner</code>.
     */
    public String getWinner() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.winners_query_builder.matches</code>.
     */
    public WinnersQueryBuilderRecord setMatches(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.winners_query_builder.matches</code>.
     */
    public Integer getMatches() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return WinnersQueryBuilder.WINNERS_QUERY_BUILDER.WINNER;
    }

    @Override
    public Field<Integer> field2() {
        return WinnersQueryBuilder.WINNERS_QUERY_BUILDER.MATCHES;
    }

    @Override
    public String component1() {
        return getWinner();
    }

    @Override
    public Integer component2() {
        return getMatches();
    }

    @Override
    public String value1() {
        return getWinner();
    }

    @Override
    public Integer value2() {
        return getMatches();
    }

    @Override
    public WinnersQueryBuilderRecord value1(String value) {
        setWinner(value);
        return this;
    }

    @Override
    public WinnersQueryBuilderRecord value2(Integer value) {
        setMatches(value);
        return this;
    }

    @Override
    public WinnersQueryBuilderRecord values(String value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WinnersQueryBuilderRecord
     */
    public WinnersQueryBuilderRecord() {
        super(WinnersQueryBuilder.WINNERS_QUERY_BUILDER);
    }

    /**
     * Create a detached, initialised WinnersQueryBuilderRecord
     */
    public WinnersQueryBuilderRecord(String winner, Integer matches) {
        super(WinnersQueryBuilder.WINNERS_QUERY_BUILDER);

        set(0, winner);
        set(1, matches);
    }
}
