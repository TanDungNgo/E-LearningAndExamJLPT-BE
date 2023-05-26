package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    // Tim kiem user co ton tai trong db k
    Optional<User> findByUsername(String username);
    // Username da co trong db chua
    Boolean existsByUsername(String username);
    // Email da co trong db chua
    Boolean existsByEmail(String email);
    List<User> findByRoles(Role role);
}
