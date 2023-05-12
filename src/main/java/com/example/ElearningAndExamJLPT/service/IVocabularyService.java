package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.Vocabulary;

import java.util.List;

public interface IVocabularyService extends IService<Vocabulary, Long> {
    List<Vocabulary> searchVocabularies(String query);
}
