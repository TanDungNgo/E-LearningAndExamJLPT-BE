package com.example.ElearningAndExamJLPT.dto;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private String title;
    private String text;
    private String image;
    private String audioFile;
    private List<String> answers;
    private Integer correctAnswer;
    private String explanation;
    private Integer type;
    private Level level;
    private Long examId;
}
