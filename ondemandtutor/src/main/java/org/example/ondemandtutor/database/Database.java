package org.example.ondemandtutor.database;


import org.example.ondemandtutor.pojo.Role;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                User user1 = new User("Dat184","123", "Dat184@gmail.com", Role.Student);
//                Student student1;
//                student1 = new Student("Dat","University");
//                student1.setUser(user1);
//                user1.setStudent(student1);
//                logger.info("insert data: {}", userRepository.save(user1));


            }
        };
    }


}
