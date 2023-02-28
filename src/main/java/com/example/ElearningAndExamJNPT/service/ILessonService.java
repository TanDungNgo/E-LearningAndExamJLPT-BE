package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.dto.LessonDTO;
import com.example.ElearningAndExamJNPT.entity.Lesson;

public interface ILessonService extends IService<Lesson, Long> {
    Lesson save(LessonDTO dto);
}
