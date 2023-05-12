package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT c FROM Article c WHERE " +
            "c.title LIKE CONCAT('%',:query, '%')" +
            "Or c.description LIKE CONCAT('%', :query, '%')")
    List<Article> searchArticles(String query);
}
