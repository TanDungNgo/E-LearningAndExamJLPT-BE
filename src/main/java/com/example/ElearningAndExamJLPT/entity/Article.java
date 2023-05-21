package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
@Validated
public class Article extends BaseEntity{
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 100)
    private String title;
    @NotBlank(message = "Description is mandatory")
    @Size(min = 3, max = 255)
    private String description;
    @NotBlank(message = "Content is mandatory")
    @Lob
    private String content;
    @Lob
    private String image;
    @JsonIgnore
    private boolean status = true;
}
