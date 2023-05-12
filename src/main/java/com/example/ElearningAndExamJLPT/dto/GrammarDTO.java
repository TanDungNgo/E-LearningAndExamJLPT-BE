package com.example.ElearningAndExamJLPT.dto;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrammarDTO {
    @NotBlank(message = "Grammar text is mandatory")
    @Size(min = 3, max = 50)
    private String text;
    @NotBlank(message = "Grammar explanation is mandatory")
    @Size(min = 3, max = 255)
    private String explanation ;
    @NotBlank(message = "Grammar example is mandatory")
    @Size(min = 3, max = 255)
    private String example;
    @NotBlank(message = "Grammar means is mandatory")
    @Size(min = 3, max = 50)
    private String means;
    private Level level;
}
