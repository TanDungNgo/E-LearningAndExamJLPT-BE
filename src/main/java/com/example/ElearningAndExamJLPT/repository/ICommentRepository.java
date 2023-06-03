package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Comment;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByLesson(Lesson lessonId);
    List<Comment> findByParentCommentId(Long parentCommentId);
}
