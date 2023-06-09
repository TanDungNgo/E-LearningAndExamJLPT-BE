package com.example.ElearningAndExamJLPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserExam {
    private Long id;
    private String examName;
    private String studentName;
    private String avatar;
    private String status;
    private Double score;
    private LocalDateTime examDate;
}
