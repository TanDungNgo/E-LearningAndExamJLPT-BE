package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.ArticleDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Article;
import com.example.ElearningAndExamJLPT.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleServiceImpl articleService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllArticle() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query article successfully", articleService.getAllArticle())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getArticleById(@PathVariable("id") Long id) {
        Optional<Article> foundArticle = articleService.getById(id);
        return foundArticle.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query article successfully", articleService.getArticle(foundArticle.get())
                        )) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find article with id = " + id, "")
                );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> createArticle(@RequestBody ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDescription(articleDTO.getDescription());
        article.setImage(articleDTO.getImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert article successfully", articleService.save(article))
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

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseObject> updateArticleById(@PathVariable("id") Long id, @RequestBody ArticleDTO article) {
        Optional<Article> foundArticle = articleService.getById(id);
        if (foundArticle.isPresent()) {
            Article newArticle = foundArticle.get();
            newArticle.setTitle(article.getTitle());
            newArticle.setContent(article.getContent());
            newArticle.setDescription(article.getDescription());
            newArticle.setImage(article.getImage());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update article successfully", articleService.save(newArticle))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find article with id = " + id, "")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteArticleById(@PathVariable("id") Long id) {
        Optional<Article> foundArticle = articleService.getById(id);
        if (foundArticle.isPresent()) {
            articleService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete article successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find article with id = " + id, "")
            );
        }
    }
}
