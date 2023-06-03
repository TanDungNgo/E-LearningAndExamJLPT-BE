package com.example.ElearningAndExamJLPT.entity;

import com.example.ElearningAndExamJLPT.entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exam_results")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private User studentId;
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam examId;
    private String status;
    private Double score;
    private String answers;
    private LocalDateTime examDate;

    @JsonGetter("examId")
    public String getExamIdName() {
        if (examId != null) {
            return examId.getName();
        }
        return null;
    }
}
