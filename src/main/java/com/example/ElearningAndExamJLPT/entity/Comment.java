package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@Validated
public class Comment extends BaseEntity {
    private String content;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @JsonBackReference
    private Lesson lesson;
    @JoinColumn(name = "parent_comment_id")
    private Long parentCommentId;
}
