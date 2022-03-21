package com.chess_challenge.java_1.statistics.repositories.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "WINNERS")
public class HibernateWinner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "winner")
    private String winner;

    public HibernateWinner(String winner) {
        this.id = null;
        this.winner = winner;
    }

    public HibernateWinner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
