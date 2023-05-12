package com.example.ElearningAndExamJLPT.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exams")
@Validated
public class Exam extends BaseEntity{
    @NotBlank(message = "Exam name is mandatory")
    @Size(min = 3, max = 100)
    private String name;
    @NotNull(message = "Exam level is mandatory")
    @Enumerated(EnumType.STRING)
    private Level level;
    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;
    private boolean status;

    @OneToMany(mappedBy = "exam")
    private List<LanguageKnowledgeQuestion> languageKnowledgeQuestions = new ArrayList<>();
    @OneToMany(mappedBy = "exam")
    private List<ReadingQuestion> readingQuestions = new ArrayList<>();
    @OneToMany(mappedBy = "exam")
    private List<ListeningQuestion> listeningQuestions = new ArrayList<>();
}
