package com.example.ElearningAndExamJNPT.dto;

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
    private String pronunciation;
    private String spelling;
    private String example;
    private String audio;
    private Long vocabularyFolder_id;
}
