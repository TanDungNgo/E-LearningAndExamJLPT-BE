package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.entity.Exam;
import com.example.ElearningAndExamJNPT.repository.IExamRepository;
import com.example.ElearningAndExamJNPT.service.IExamService;
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
