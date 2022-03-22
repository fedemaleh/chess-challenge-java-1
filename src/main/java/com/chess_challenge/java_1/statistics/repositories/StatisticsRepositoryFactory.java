package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.statistics.InvalidRepositoryException;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernatePiecesRepo;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateWinnersRepo;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateRepository;
import com.chess_challenge.java_1.statistics.repositories.inmemory.InMemoryStatisticsRepository;
import com.chess_challenge.java_1.statistics.repositories.jooq.JooqRepository;
import org.jooq.DSLContext;
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

    @Bean
    @Primary
    public StatisticsRepository statisticsRepository(Environment env,
                                                     HibernateWinnersRepo winnersRepo,
                                                     HibernatePiecesRepo piecesRepo,
                                                     DSLContext db) throws InvalidRepositoryException {
        String repo = env.getProperty(REPOSITORY_PROPERTY_KEY);

        if (REPOSITORY_IN_MEMORY.equals(repo)) {
            return new InMemoryStatisticsRepository();
        }

        if (REPOSITORY_HIBERNATE.equals(repo)) {
            return new HibernateRepository(winnersRepo, piecesRepo);
        }

        if (REPOSITORY_JOOQ.equals(repo)) {
            return new JooqRepository(db);
        }

        throw new InvalidRepositoryException();
    }
}
