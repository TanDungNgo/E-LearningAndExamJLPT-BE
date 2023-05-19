package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.Comment;
import com.example.ElearningAndExamJLPT.entity.Lesson;

import java.util.List;
public interface ICommentService extends IService<Comment,Long> {
    List<Comment> getCommentsByLessonId(Long lessonId);
    List<Comment> getRepliesByComment(Long parentCommentId);
}
