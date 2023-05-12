package com.example.ElearningAndExamJLPT.dto;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VocabularyFolderDTO {
    private Level level;
    private String title;

}
