package com.example.ElearningAndExamJLPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVocabulary {
    private Long id;
    private String text;
    private String meaning;
    private String pronunciation;
    private String spelling;
    private String example;
    private String audio;
}
