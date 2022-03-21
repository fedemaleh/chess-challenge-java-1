package com.chess_challenge.java_1.statistics.repositories.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface HibernatePiecesRepo extends JpaRepository<HibernatePiece, Long> {
    default CompletableFuture<List<HibernatePiece>> pieces() {
        return CompletableFuture.supplyAsync(this::findAll);
    }

    default void recordPiece(HibernatePiece piece) {
        CompletableFuture.runAsync(() -> this.save(piece));
    }
}
