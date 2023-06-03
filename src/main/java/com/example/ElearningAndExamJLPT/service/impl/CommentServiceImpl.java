package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseComment;
import com.example.ElearningAndExamJLPT.entity.Comment;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.repository.ICommentRepository;
import com.example.ElearningAndExamJLPT.repository.ILessonRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return commentRepository.save(entity);
    }

    @Override
    public Comment update(Comment entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<ResponseComment> getCommentsByLessonId(Long lessonId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isPresent()) {
            List<Comment> comments = commentRepository.findByLesson(lesson.get());
            List<ResponseComment> responseComments = new ArrayList<>();
            for (Comment comment : comments) {
                ResponseComment responseComment = new ResponseComment();
                responseComment.setId(comment.getId());
                responseComment.setContent(comment.getContent());
                responseComment.setCreatedAt(String.valueOf(comment.getCreatedDate()));
                responseComment.setCreatedBy(comment.getCreatedBy().getFirstname());
                responseComment.setAvatar(comment.getCreatedBy().getAvatar());
                List<Comment> replies = commentRepository.findByParentCommentId(comment.getId());
                List<ResponseComment> responseReplies = new ArrayList<>();
                for (Comment reply : replies) {
                    ResponseComment responseReply = new ResponseComment();
                    responseReply.setId(reply.getId());
                    responseReply.setContent(reply.getContent());
                    responseReply.setCreatedAt(String.valueOf(reply.getCreatedDate()));
                    responseReply.setCreatedBy(reply.getCreatedBy().getFirstname());
                    responseReply.setAvatar(reply.getCreatedBy().getAvatar());
                    responseReplies.add(responseReply);
                }
                responseComment.setReplies(responseReplies);
                if(comment.getParentCommentId() == null){
                    responseComments.add(responseComment);
                }
            }
            return responseComments;
        }
        return null;
    }

    @Override
    public List<ResponseComment> getRepliesByComment(Long parentCommentId) {
//        return commentRepository.findByParentCommentId(parentCommentId);
        return null;
    }
}
