package com.example.ElearningAndExamJLPT.dto.response;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private Double rate;
    private Integer numberOfStudent;
    private String teacherName;
    private String teacherAvatar;
    private List<ResponseLesson> lessons;
}
