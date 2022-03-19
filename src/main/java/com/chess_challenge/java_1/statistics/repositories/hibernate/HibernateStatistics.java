package com.chess_challenge.java_1.statistics.repositories.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "RESULTS")
public class HibernateStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "winner")
    private final String winner;

    @Column(name = "piece")
    private final String piece;

    public HibernateStatistics(String winner, String piece) {
        this.winner = winner;
        this.piece = piece;
    }
}
