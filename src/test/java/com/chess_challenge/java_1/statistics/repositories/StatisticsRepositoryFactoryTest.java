package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.statistics.InvalidRepositoryException;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateRepository;
import com.chess_challenge.java_1.statistics.repositories.inmemory.InMemoryStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.mock.env.MockEnvironment;

import static com.chess_challenge.java_1.statistics.repositories.StatisticsRepositoryFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class StatisticsRepositoryFactoryTest {
    private StatisticsRepositoryFactory factory;
    private MockEnvironment env;

    @BeforeEach
    public void setup() {
        this.factory = new StatisticsRepositoryFactory();
        this.env = new MockEnvironment();
    }

    @Test
    @Timeout(value = 5)
    void inmemory_repository_if_config_is_in_memory() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_IN_MEMORY);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env);

        // then it should wire an in-memory repo
        assertInstanceOf(InMemoryStatisticsRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void hibernate_repository_if_config_is_hibernate() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_HIBERNATE);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env);

        // then it should wire an in-memory repo
        assertInstanceOf(HibernateRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void throw_exception_if_no_config() {
        // given an environment with no repo configured

        // when wiring the repo
        // then it should throw an exception
        assertThrows(InvalidRepositoryException.class, () -> factory.statisticsRepository(env));
    }

    @Test
    @Timeout(value = 5)
    void inmemory_repository_if_config_is_unknown() {
        // given an environment with an unknown repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, "unknown");

        // when wiring the repo
        // then it should throw an exception
        assertThrows(InvalidRepositoryException.class, () -> factory.statisticsRepository(env));
    }
}