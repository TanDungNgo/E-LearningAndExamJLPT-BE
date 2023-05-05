package com.example.ElearningAndExamJNPT.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "listening_questions")
@Validated
public class ListeningQuestion extends BaseEntity {
    @NotNull(message = "audio_file is mandatory")
    @Lob
    private String audio_file;
    @Lob
    private String image;
    private String text;
    @NotNull(message = "Level is mandatory")
    @Enumerated(EnumType.STRING)
    private Level level;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer correct_answer;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonBackReference
    private Exam exam;
}
