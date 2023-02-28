package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.converter.LessonConverter;
import com.example.ElearningAndExamJNPT.dto.LessonDTO;
import com.example.ElearningAndExamJNPT.entity.Course;
import com.example.ElearningAndExamJNPT.entity.Lesson;
import com.example.ElearningAndExamJNPT.repository.ICourseRepository;
import com.example.ElearningAndExamJNPT.repository.ILessonRepository;
import com.example.ElearningAndExamJNPT.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private LessonConverter lessonConverter;
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
        return lessonRepository.save(entity);
    }

    @Override
    public Lesson save(LessonDTO dto) {
        Lesson lesson = lessonConverter.toLessonEntity(dto);
        Optional<Course> course = courseRepository.findById(dto.getCourse_id());
        lesson.setCourse(course.get());
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson update(Lesson entity) {
        return lessonRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
