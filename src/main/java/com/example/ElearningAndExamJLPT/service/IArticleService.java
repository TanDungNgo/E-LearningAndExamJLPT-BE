package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseArticle;
import com.example.ElearningAndExamJLPT.entity.Article;

import java.util.List;

public interface IArticleService extends IService<Article, Long> {
    List<ResponseArticle> searchArticles(String query);
    List<ResponseArticle> getAllArticle();
    ResponseArticle getArticle(Article article);
}
