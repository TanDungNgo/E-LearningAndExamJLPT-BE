package com.example.ElearningAndExamJLPT.entity;

import com.example.ElearningAndExamJLPT.entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_lessons")
public class UserLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    private int watchedTime; // Thời gian đã xem (đơn vị: giây)
    private boolean completed = false; // Đã hoàn thành hay chưa
}
