package com.example.ElearningAndExamJNPT.service.impl;


import com.example.ElearningAndExamJNPT.entity.User.User;
import com.example.ElearningAndExamJNPT.repository.IUserRepository;
import com.example.ElearningAndExamJNPT.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new UsernameNotFoundException("Username by " + id + " can not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
