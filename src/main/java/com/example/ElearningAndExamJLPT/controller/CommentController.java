package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.CommentDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Comment;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.service.impl.CommentServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    LessonServiceImpl lessonService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllComments() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query comments successfully", commentService.getAll())
        );
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<ResponseObject> getCommentsByLessonId(@PathVariable("lessonId") Long lessonId) {
        Optional<Lesson> foundLesson = lessonService.getById(lessonId);
        if (foundLesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query comments successfully", commentService.getCommentsByLessonId(lessonId))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + lessonId, "")
            );
        }
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<ResponseObject> getRepliesByComment(@PathVariable(value = "commentId") Long commentId) {
        Optional<Comment> comment = commentService.getById(commentId);
        if (comment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query replies successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find comment with id = " + commentId, "")
            );
        }
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createComment(@RequestBody @Valid CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Optional<Lesson> lesson = lessonService.getById(commentDTO.getLessonId());
        if (lesson.isPresent()) {
            comment.setLesson(lesson.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Lesson with id = " + commentDTO.getLessonId(), "")
            );
        }
        if (commentDTO.getParentCommentId() != null) {
            Optional<Comment> parentComment = commentService.getById(commentDTO.getParentCommentId());
            if (parentComment.isPresent()) {
                comment.setParentCommentId(parentComment.get().getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Comment with id = " + commentDTO.getParentCommentId(), "")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query replies successfully", commentService.save(comment))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseObject> updateComment(
            @PathVariable(value = "commentId") Long commentId,
            @RequestBody CommentDTO commentDTO
    ) {
        Optional<Comment> foundComment = commentService.getById(commentId);
        if (foundComment.isPresent()) {
            Comment comment = foundComment.get();
            comment.setContent(commentDTO.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update comment successfully", commentService.save(comment))
            );
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find comment with id = " + commentId, "")
            );
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable(value = "commentId") Long commentId) {
        Optional<Comment> foundComment = commentService.getById(commentId);
        if (foundComment.isPresent()) {
            commentService.deleteById(commentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete comment successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find comment with id = " + commentId, "")
            );
        }
    }
}