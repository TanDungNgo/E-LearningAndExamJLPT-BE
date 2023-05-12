package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.Article;

import java.util.List;

public interface IArticleService extends IService<Article, Long> {
    List<Article> searchArticles(String query);
}
