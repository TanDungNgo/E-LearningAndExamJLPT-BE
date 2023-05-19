package com.example.ElearningAndExamJLPT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    @NotBlank(message = "Content is mandatory")
    private String content;
    @NotNull(message = "Lesson id is mandatory")
    private Long lessonId;
    private Long parentCommentId;
}
