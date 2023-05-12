package com.example.ElearningAndExamJLPT.dto;

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
public class ArticleDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 100)
    private String title;
    @NotBlank(message = "Description is mandatory")
    @Size(min = 3, max = 255)
    private String description;
    @NotBlank(message = "Content is mandatory")
    private String content;
    private String image;
}
