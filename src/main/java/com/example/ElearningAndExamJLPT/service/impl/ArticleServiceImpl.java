package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseArticle;
import com.example.ElearningAndExamJLPT.entity.Article;
import com.example.ElearningAndExamJLPT.repository.IArticleRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleRepository articleRepository;
    @Autowired
    private IUserRepository userRepository;


    @Override
    public List<Article> searchArticles(String query) {
        return articleRepository.searchArticles(query);
    }

    @Override
    public List<ResponseArticle> getAllArticle() {
        List<Article> articles = articleRepository.findAllByDeletedFalse();
        List<ResponseArticle> responseArticles = new ArrayList<>();
        for (Article article : articles) {
            ResponseArticle responseArticle = new ResponseArticle();
            responseArticle.setId(article.getId());
            responseArticle.setTitle(article.getTitle());
            responseArticle.setDescription(article.getDescription());
            responseArticle.setImage(article.getImage());
            responseArticle.setContent(article.getContent());
            responseArticle.setCreatedDate(String.valueOf(article.getCreatedDate()));
            responseArticle.setModifiedDate(String.valueOf(article.getModifiedDate()));
            responseArticle.setCreatedBy(article.getCreatedBy().getFirstname() + " " + article.getCreatedBy().getLastname());
            responseArticle.setAvatarCreatedBy(article.getCreatedBy().getAvatar());
            responseArticles.add(responseArticle);
        }
        return responseArticles;

    }

    @Override
    public ResponseArticle getArticle(Article article) {
        ResponseArticle responseArticle = new ResponseArticle();
        responseArticle.setId(article.getId());
        responseArticle.setTitle(article.getTitle());
        responseArticle.setDescription(article.getDescription());
        responseArticle.setImage(article.getImage());
        responseArticle.setContent(article.getContent());
        responseArticle.setCreatedDate(String.valueOf(article.getCreatedDate()));
        responseArticle.setModifiedDate(String.valueOf(article.getModifiedDate()));
        responseArticle.setCreatedBy(article.getCreatedBy().getFirstname() + " " + article.getCreatedBy().getLastname());
        responseArticle.setAvatarCreatedBy(article.getCreatedBy().getAvatar());
        return responseArticle;
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Article> getById(Long id) {
        return articleRepository.findArticleByDeletedFalseAndId(id);
    }

    @Override
    public Article save(Article entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return articleRepository.save(entity);
    }

    @Override
    public Article update(Article entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return articleRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
//        articleRepository.deleteById(id);
        Article article = articleRepository.findById(id).get();
        article.setDeleted(true);
        articleRepository.save(article);
    }
}
