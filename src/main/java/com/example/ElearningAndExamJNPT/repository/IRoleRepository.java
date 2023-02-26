package com.example.ElearningAndExamJNPT.repository;


import com.example.ElearningAndExamJNPT.entity.User.Role;
import com.example.ElearningAndExamJNPT.entity.User.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
