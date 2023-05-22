package com.example.ElearningAndExamJLPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseQuestion {
    private Long id;
    private String text;
    private String image;
    private String audioFile;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
