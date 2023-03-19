package com.example.ElearningAndExamJNPT.dto;

import com.example.ElearningAndExamJNPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {
    private String name;
    private String description;
    private Double rate;
    private Double price;
    private Level level;
    private String banner;
}
