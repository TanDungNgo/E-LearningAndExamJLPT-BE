package com.example.ElearningAndExamJLPT.service;



import com.example.ElearningAndExamJLPT.entity.User.User;

import java.util.Optional;

public interface IUserService extends IService<User, Long>{
    Optional<User> findByUsername(String name);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
}
