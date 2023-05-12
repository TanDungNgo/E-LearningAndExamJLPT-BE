package com.example.ElearningAndExamJLPT.service;



import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
