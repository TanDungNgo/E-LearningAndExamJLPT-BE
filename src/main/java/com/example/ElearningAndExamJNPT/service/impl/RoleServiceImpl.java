package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.entity.User.Role;
import com.example.ElearningAndExamJNPT.entity.User.RoleName;
import com.example.ElearningAndExamJNPT.repository.IRoleRepository;
import com.example.ElearningAndExamJNPT.service.IRoleService;
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
