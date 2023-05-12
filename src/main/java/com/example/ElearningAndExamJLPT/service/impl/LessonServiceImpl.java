package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.ILessonRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public Lesson save(Lesson entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return lessonRepository.save(entity);
    }

    @Override
    public List<Lesson> searchLessons(String query) {
        return lessonRepository.searchLessons(query);
    }

    @Override
    public Lesson update(Lesson entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return lessonRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
