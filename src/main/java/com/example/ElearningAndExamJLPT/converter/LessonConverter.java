package com.example.ElearningAndExamJLPT.converter;

import com.example.ElearningAndExamJLPT.dto.LessonDTO;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonConverter {
    public Lesson toLessonEntity(LessonDTO dto) {
        Lesson entity = new Lesson();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUrlVideo(dto.getUrlVideo());
        return entity;
    }
}
