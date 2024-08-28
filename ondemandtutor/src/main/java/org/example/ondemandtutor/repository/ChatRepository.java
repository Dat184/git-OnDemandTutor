package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Chat;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findBySenderAndRecipient(User sender, User recipient);
}
