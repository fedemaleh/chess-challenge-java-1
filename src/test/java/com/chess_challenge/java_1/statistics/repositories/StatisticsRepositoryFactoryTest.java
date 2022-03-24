package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.statistics.InvalidRepositoryException;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateRepository;
import com.chess_challenge.java_1.statistics.repositories.inmemory.InMemoryStatisticsRepository;
import com.chess_challenge.java_1.statistics.repositories.jooq.JooqRepository;
import com.chess_challenge.java_1.statistics.repositories.redis.RedisHashRepository;
import com.chess_challenge.java_1.statistics.repositories.redis.RedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.env.MockEnvironment;

import static com.chess_challenge.java_1.statistics.repositories.StatisticsRepositoryFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StatisticsRepositoryFactoryTest {
    private StatisticsRepositoryFactory factory;
    private MockEnvironment env;
    private ConfigurableApplicationContext context;

    @BeforeEach
    public void setup() {
        this.factory = new StatisticsRepositoryFactory();
        this.env = new MockEnvironment();
        this.context = mock(ConfigurableApplicationContext.class);
    }

    @Test
    @Timeout(value = 5)
    void inmemory_repository_if_config_is_in_memory() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_IN_MEMORY);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env, context);

        // then it should wire an in-memory repo
        assertInstanceOf(InMemoryStatisticsRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void hibernate_repository_if_config_is_hibernate() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_HIBERNATE);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env, context);

        // then it should wire an in-memory repo
        assertInstanceOf(HibernateRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void jooq_repository_if_config_is_jooq() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_JOOQ);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env, context);

        // then it should wire an in-memory repo
        assertInstanceOf(JooqRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void redis_repository_if_config_is_redis() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_REDIS);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env, context);

        // then it should wire an in-memory repo
        assertInstanceOf(RedisRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void redis_hash_repository_if_config_is_redis_hash() throws InvalidRepositoryException {
        // given an environment with an inmemory repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, REPOSITORY_REDIS_HASH);

        // when wiring the repo
        StatisticsRepository repo = factory.statisticsRepository(env, context);

        // then it should wire an in-memory repo
        assertInstanceOf(RedisHashRepository.class, repo);
    }

    @Test
    @Timeout(value = 5)
    void throw_exception_if_no_config() {
        // given an environment with no repo configured

        // when wiring the repo
        // then it should throw an exception
        assertThrows(InvalidRepositoryException.class, () -> factory.statisticsRepository(env, context));
    }

    @Test
    @Timeout(value = 5)
    void inmemory_repository_if_config_is_unknown() {
        // given an environment with an unknown repo configured
        env.setProperty(REPOSITORY_PROPERTY_KEY, "unknown");

        // when wiring the repo
        // then it should throw an exception
        assertThrows(InvalidRepositoryException.class, () -> factory.statisticsRepository(env, context));
    }
}