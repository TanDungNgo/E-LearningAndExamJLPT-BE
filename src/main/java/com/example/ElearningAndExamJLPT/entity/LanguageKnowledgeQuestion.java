package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "language_knowledge_questions")
@Validated
public class LanguageKnowledgeQuestion extends BaseEntity{
    private String text;
    @NotNull(message = "Level is mandatory")
    @Enumerated(EnumType.STRING)
    private Level level;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @JsonIgnore
    @JoinColumn(name = "correct_answer")
    private Integer correctAnswer;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonBackReference
    private Exam exam;
    @JsonIgnore
    private boolean status = true;
}
