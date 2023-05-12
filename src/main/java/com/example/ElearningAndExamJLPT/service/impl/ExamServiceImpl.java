package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.repository.IExamRepository;
import com.example.ElearningAndExamJLPT.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements IExamService {
    @Autowired
    private IExamRepository examRepository;
    @Override
    public List<Exam> getAll() {
        return null;
    }

    @Override
    public Optional<Exam> getById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Exam save(Exam entity) {
        return null;
    }

    @Override
    public Exam update(Exam entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
