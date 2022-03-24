package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.statistics.InvalidRepositoryException;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernatePiecesRepo;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateWinnersRepo;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateRepository;
import com.chess_challenge.java_1.statistics.repositories.inmemory.InMemoryStatisticsRepository;
import com.chess_challenge.java_1.statistics.repositories.jooq.JooqRepository;
import com.chess_challenge.java_1.statistics.repositories.redis.RedisRepository;
import io.lettuce.core.api.StatefulRedisConnection;
import org.jooq.DSLContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class StatisticsRepositoryFactory {
    static final String REPOSITORY_PROPERTY_KEY = "stats.repository";
    static final String REPOSITORY_IN_MEMORY = "in-memory";
    static final String REPOSITORY_HIBERNATE = "hibernate";
    static final String REPOSITORY_JOOQ = "jooq";
    static final String REPOSITORY_REDIS = "redis";

    @Bean
    @Primary
    public StatisticsRepository statisticsRepository(Environment env,
                                                     ConfigurableApplicationContext context) throws InvalidRepositoryException {
        String repo = env.getProperty(REPOSITORY_PROPERTY_KEY);

        if (REPOSITORY_IN_MEMORY.equals(repo)) {
            return new InMemoryStatisticsRepository();
        }

        if (REPOSITORY_HIBERNATE.equals(repo)) {
            HibernateWinnersRepo winnersRepo = context.getBean(HibernateWinnersRepo.class);
            HibernatePiecesRepo piecesRepo = context.getBean(HibernatePiecesRepo.class);

            return new HibernateRepository(winnersRepo, piecesRepo);
        }

        if (REPOSITORY_JOOQ.equals(repo)) {
            DSLContext db = context.getBean(DSLContext.class);
            return new JooqRepository(db);
        }

        if (REPOSITORY_REDIS.equals(repo)) {
            StatefulRedisConnection<String, String> redisConnection = context.getBean(StatefulRedisConnection.class);

            return new RedisRepository(redisConnection);
        }

        throw new InvalidRepositoryException();
    }
}
