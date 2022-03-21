package com.chess_challenge.java_1.statistics.repositories.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface HibernateWinnersRepo extends JpaRepository<HibernateWinner, Long> {
    default CompletableFuture<List<HibernateWinner>> winners() {
        return CompletableFuture.supplyAsync(this::findAll);
    }

    default void recordWinner(HibernateWinner winner) {
        CompletableFuture.runAsync(() -> this.save(winner));
    }
}
