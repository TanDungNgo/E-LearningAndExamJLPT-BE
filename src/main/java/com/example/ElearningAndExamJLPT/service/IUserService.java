package com.example.ElearningAndExamJLPT.service;



import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.User;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IService<User, Long>{
    Optional<User> findByUsername(String name);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    List<User> findByTeacher();
    List<User> findByStudent();
}
