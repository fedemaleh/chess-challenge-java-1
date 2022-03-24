package com.chess_challenge.java_1.statistics;

public class InvalidRepositoryException extends Exception {
    public InvalidRepositoryException() {
        super("Configuration Property `stats.repository` should be set to one of `in-memory`, `hibernate`, `jooq` " +
                "`redis`, `cassandra`, `mongo` or `neo`");
    }
}
