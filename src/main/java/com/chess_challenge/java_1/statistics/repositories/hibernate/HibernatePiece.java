package com.chess_challenge.java_1.statistics.repositories.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "PIECES")
public class HibernatePiece {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "piece")
    private String piece;

    public HibernatePiece(String piece) {
        this.id = null;
        this.piece = piece;
    }

    public HibernatePiece() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }
}
