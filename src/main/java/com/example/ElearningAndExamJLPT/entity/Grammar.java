package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grammars")
public class Grammar extends BaseEntity{
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
    @Enumerated(EnumType.STRING)
    private Level level;
    @JsonIgnore
    private boolean status = true;
    @JsonIgnore
    private boolean deleted = false;
}
