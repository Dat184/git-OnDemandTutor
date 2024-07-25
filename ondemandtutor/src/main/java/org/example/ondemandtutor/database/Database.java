package org.example.ondemandtutor.database;


import org.example.ondemandtutor.persistence.entity.Subject;
import org.example.ondemandtutor.persistence.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(SubjectRepository subjectRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Subject subjectA = new Subject("Java", "Hoc phan lap trinh Java");
                Subject subjectB = new Subject("Web", "Hoc phan lap trinh Web");
                logger.info("insert data: {}", subjectRepository.save(subjectA));
                logger.info("insert data: {}", subjectRepository.save(subjectB));
            }
        };
    }
}
