package com.example.ElearningAndExamJLPT.dto.response;

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
public class ResponseExam {
    private Long id;
    private String name;
    private Level level;
    private Integer durationLanguageKnowledge;
    private Integer durationReading;
    private Integer durationListening;
    private List<ResponseQuestion> languageKnowledgeQuestions;
    private List<ResponseQuestion> readingQuestions;
    private List<ResponseQuestion> listeningQuestions;
}
