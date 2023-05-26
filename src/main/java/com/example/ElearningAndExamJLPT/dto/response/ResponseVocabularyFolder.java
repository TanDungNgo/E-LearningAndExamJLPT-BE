package com.example.ElearningAndExamJLPT.dto.response;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVocabularyFolder {
    private Long id;
    private Level level;
    private String title;
    private Integer count;
    private List<ResponseVocabulary> vocabularies;
}
