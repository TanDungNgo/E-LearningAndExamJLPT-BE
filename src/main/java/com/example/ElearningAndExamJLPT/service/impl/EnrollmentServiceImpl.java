package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.repository.IEnrollmentRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {
    @Autowired
    private IEnrollmentRepository enrollmentRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Enrollment> getAll() {
        return null;
    }

    @Override
    public Optional<Enrollment> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Enrollment save(Enrollment entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setUserId(userRepository.findByUsername(authentication.getName()).get());
        entity.setRegistrationDate(now);
        return enrollmentRepository.save(entity);
    }

    @Override
    public Enrollment update(Enrollment entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
