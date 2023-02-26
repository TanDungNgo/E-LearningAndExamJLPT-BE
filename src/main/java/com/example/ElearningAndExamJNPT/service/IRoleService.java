package com.example.ElearningAndExamJNPT.service;



import com.example.ElearningAndExamJNPT.entity.User.Role;
import com.example.ElearningAndExamJNPT.entity.User.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
