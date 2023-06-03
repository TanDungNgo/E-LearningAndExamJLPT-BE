package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseComment;
import com.example.ElearningAndExamJLPT.entity.Comment;
import com.example.ElearningAndExamJLPT.entity.Lesson;

import java.util.List;
public interface ICommentService extends IService<Comment,Long> {
    List<ResponseComment> getCommentsByLessonId(Long lessonId);
    List<ResponseComment> getRepliesByComment(Long parentCommentId);
}
