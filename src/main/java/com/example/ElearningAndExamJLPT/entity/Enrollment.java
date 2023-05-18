package com.example.ElearningAndExamJLPT.entity;

import com.example.ElearningAndExamJLPT.entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private User studentId;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course courseId;

    private LocalDateTime registrationDate;
}
