package com.example.ElearningAndExamJLPT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VocabularyDTO {
    private String text;
    private String meaning;
    private String pronunciation;
    private String spelling;
    private String example;
    private String audio;
    private Long vocabularyFolder_id;
}
