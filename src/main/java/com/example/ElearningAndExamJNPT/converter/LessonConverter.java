package com.example.ElearningAndExamJNPT.converter;

import com.example.ElearningAndExamJNPT.dto.LessonDTO;
import com.example.ElearningAndExamJNPT.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonConverter {
    public Lesson toLessonEntity(LessonDTO dto) {
        Lesson entity = new Lesson();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setRate(dto.getRate());
        entity.setUrlVideo(dto.getUrlVideo());
        return entity;
    }
}
