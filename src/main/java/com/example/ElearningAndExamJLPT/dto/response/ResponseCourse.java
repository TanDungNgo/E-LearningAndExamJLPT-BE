package com.example.ElearningAndExamJLPT.dto.response;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCourse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Level level;
    private String type;
    private String banner;
    private String duration;
    private String teacherName;
    private String teacherAvatar;
}
