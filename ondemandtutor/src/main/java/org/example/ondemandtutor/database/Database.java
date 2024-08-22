package org.example.ondemandtutor.database;


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
                //Student student1;
               // student1 = new Student("Dat","University");
//                student1.setUser(user1);
//                user1.setStudent(student1);
//                logger.info("insert data: {}", userRepository.save(user1));
//                User user2 = new User("Huy123", "123", "huy@gmail.com", Role.Tutor);
//                Tutor tutor1 = new Tutor("Huy", "University", "Math", "tung hoc toan", 4.5,user2);
//                user2.setTutor(tutor1);
//                logger.info("insert data: {}", userRepository.save(user2));
//                    User user3 = new User("admin", "admin", "admin@gamil.com", Role.Admin);
//                AdminLog adminLog = new AdminLog("create", "create user", null, user3);
//                logger.info("insert data: {}", AdminLogRepository.save(adminLog));

            }
        };
    }


}
