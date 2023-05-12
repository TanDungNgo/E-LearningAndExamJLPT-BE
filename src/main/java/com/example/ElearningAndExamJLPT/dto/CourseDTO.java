package com.example.ElearningAndExamJLPT.dto;

import com.example.ElearningAndExamJLPT.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {
    @NotBlank(message = "Course name is mandatory")
    @Size(min = 3, max = 100)
    private String name;
    @NotBlank(message = "Course description is mandatory")
    @Size(min = 3, max = 255)
    private String description;
    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;
    @NotNull(message = "Course level is mandatory")
    private Level level;
    @NotNull(message = "Course type is mandatory")
    private String type;
    private String banner;
    private String duration;
}
