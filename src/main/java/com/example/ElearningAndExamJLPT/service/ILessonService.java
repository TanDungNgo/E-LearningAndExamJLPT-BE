package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.Lesson;

import java.util.List;

public interface ILessonService extends IService<Lesson, Long> {
    List<Lesson> searchLessons(String query);
}
