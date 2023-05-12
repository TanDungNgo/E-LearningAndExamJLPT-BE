package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.RoleName;
import com.example.ElearningAndExamJLPT.repository.IRoleRepository;
import com.example.ElearningAndExamJLPT.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleResponsitory;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleResponsitory.findByName(name);
    }
}
