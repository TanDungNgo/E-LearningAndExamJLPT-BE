package com.example.ElearningAndExamJLPT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonDTO {
    private String name;
    private String description;
    private String urlVideo;
    private Double rate;
    private Long course_id;
}
