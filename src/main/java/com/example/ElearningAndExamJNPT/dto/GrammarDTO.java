package com.example.ElearningAndExamJNPT.dto;

import com.example.ElearningAndExamJNPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrammarDTO {
    private String text;
    private String explanation ;
    private String example;
    private String means;
    private Level level;
}
