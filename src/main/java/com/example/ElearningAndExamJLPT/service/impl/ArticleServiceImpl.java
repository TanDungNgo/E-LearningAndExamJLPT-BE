package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Article;
import com.example.ElearningAndExamJLPT.repository.IArticleRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> getById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article save(Article entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return articleRepository.save(entity);
    }

    @Override
    public Article update(Article entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return articleRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
