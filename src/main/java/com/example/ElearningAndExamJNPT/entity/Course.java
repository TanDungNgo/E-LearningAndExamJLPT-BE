package com.example.ElearningAndExamJNPT.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course extends BaseEntity{
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    @Min(0)
    @Max(5)
    private Double rate;
    private Double price;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Lob
    private String banner;
}
