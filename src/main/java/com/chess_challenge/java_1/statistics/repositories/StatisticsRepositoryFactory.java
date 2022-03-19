package com.chess_challenge.java_1.statistics.repositories;

import com.chess_challenge.java_1.statistics.InvalidRepositoryException;
import com.chess_challenge.java_1.statistics.repositories.hibernate.HibernateRepository;
import com.chess_challenge.java_1.statistics.repositories.inmemory.InMemoryStatisticsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class StatisticsRepositoryFactory {
    static final String REPOSITORY_PROPERTY_KEY = "stats.repository";
    static final String REPOSITORY_IN_MEMORY = "in-memory";
    static final String REPOSITORY_HIBERNATE = "hibernate";

    @Bean
    @Primary
    public StatisticsRepository statisticsRepository(Environment env) throws InvalidRepositoryException {
        String repo = env.getProperty(REPOSITORY_PROPERTY_KEY);

        if (REPOSITORY_IN_MEMORY.equals(repo)) {
            return new InMemoryStatisticsRepository();
        }

        if (REPOSITORY_HIBERNATE.equals(repo)) {
            return new HibernateRepository();
        }

        throw new InvalidRepositoryException();
    }
}
