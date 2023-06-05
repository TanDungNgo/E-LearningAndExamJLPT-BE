package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.entity.UserLesson;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.ILessonRepository;
import com.example.ElearningAndExamJLPT.repository.IUserLessonRepository;
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
    @Autowired
    private IUserLessonRepository userLessonRepository;
    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return lessonRepository.findLessonByDeletedFalseAndId(id);
    }

    @Override
    public Lesson save(Lesson entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return lessonRepository.save(entity);
    }

    @Override
    public List<Lesson> searchLessons(String query) {
        return lessonRepository.searchLessons(query);
    }

    @Override
    public Lesson markVideoAsWatched(Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        UserLesson userLesson = userLessonRepository.findByLessonAndUser(lesson,user);
        if(userLesson != null){
            userLesson.setCompleted(true);
            userLessonRepository.save(userLesson);
            return lesson;
        }
        return null;
    }

    @Override
    public Lesson update(Lesson entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return lessonRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Lesson lesson = lessonRepository.findLessonByDeletedFalseAndId(id).get();
        lesson.setDeleted(true);
        lessonRepository.save(lesson);
//        lessonRepository.deleteById(id);
    }
}
