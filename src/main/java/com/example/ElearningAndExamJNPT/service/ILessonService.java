package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.dto.LessonDTO;
import com.example.ElearningAndExamJNPT.entity.Lesson;

import java.util.List;

public interface ILessonService extends IService<Lesson, Long> {
    List<Lesson> searchLessons(String query);
}
