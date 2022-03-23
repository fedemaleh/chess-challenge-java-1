/*
 * This file is generated by jOOQ.
 */
package com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.records;


import com.chess_challenge.java_1.statistics.repositories.jooq.db.tables.PiecesQueryBuilder;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PiecesQueryBuilderRecord extends UpdatableRecordImpl<PiecesQueryBuilderRecord> implements Record2<String, Integer> {

    private static final long serialVersionUID = 328276979;

    /**
     * Setter for <code>public.pieces_query_builder.piece</code>.
     */
    public PiecesQueryBuilderRecord setPiece(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.pieces_query_builder.piece</code>.
     */
    public String getPiece() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.pieces_query_builder.matches</code>.
     */
    public PiecesQueryBuilderRecord setMatches(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.pieces_query_builder.matches</code>.
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
        return PiecesQueryBuilder.PIECES_QUERY_BUILDER.PIECE;
    }

    @Override
    public Field<Integer> field2() {
        return PiecesQueryBuilder.PIECES_QUERY_BUILDER.MATCHES;
    }

    @Override
    public String component1() {
        return getPiece();
    }

    @Override
    public Integer component2() {
        return getMatches();
    }

    @Override
    public String value1() {
        return getPiece();
    }

    @Override
    public Integer value2() {
        return getMatches();
    }

    @Override
    public PiecesQueryBuilderRecord value1(String value) {
        setPiece(value);
        return this;
    }

    @Override
    public PiecesQueryBuilderRecord value2(Integer value) {
        setMatches(value);
        return this;
    }

    @Override
    public PiecesQueryBuilderRecord values(String value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PiecesQueryBuilderRecord
     */
    public PiecesQueryBuilderRecord() {
        super(PiecesQueryBuilder.PIECES_QUERY_BUILDER);
    }

    /**
     * Create a detached, initialised PiecesQueryBuilderRecord
     */
    public PiecesQueryBuilderRecord(String piece, Integer matches) {
        super(PiecesQueryBuilder.PIECES_QUERY_BUILDER);

        set(0, piece);
        set(1, matches);
    }
}