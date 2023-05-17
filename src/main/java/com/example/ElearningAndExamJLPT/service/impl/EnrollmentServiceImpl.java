package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.repository.IEnrollmentRepository;
import com.example.ElearningAndExamJLPT.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EnrollmentServiceImpl implements IEnrollmentService {
    @Autowired
    private IEnrollmentRepository enrollmentRepository;
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
