package com.example.ElearningAndExamJLPT.dto;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {
    @NotBlank(message = "Exam name is mandatory")
    @Size(min = 3, max = 100)
    private String name;
    @NotNull(message = "Exam level is mandatory")
    @Enumerated(EnumType.STRING)
    private Level level;
    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;
}
