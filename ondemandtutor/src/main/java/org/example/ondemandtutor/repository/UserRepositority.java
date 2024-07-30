package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositority extends JpaRepository<User, Long> {
    List<User> findByUsername(String trim);
}
