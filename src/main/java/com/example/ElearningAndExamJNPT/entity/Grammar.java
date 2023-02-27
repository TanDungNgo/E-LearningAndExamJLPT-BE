package com.example.ElearningAndExamJNPT.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grammars")
public class Grammar extends BaseEntity{
    @NotBlank
    @Size(min = 3, max = 50)
    private String text;
    @NotBlank
    @Size(min = 3, max = 255)
    private String explanation ;
    @NotBlank
    @Size(min = 3, max = 255)
    private String example;
    @NotBlank
    @Size(min = 3, max = 50)
    private String means;
    @Enumerated(EnumType.STRING)
    private Level level;
}
