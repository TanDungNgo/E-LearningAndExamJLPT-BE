package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJNPT.entity.Vocabulary;

import java.util.List;

public interface IVocabularyService extends IService<Vocabulary, Long> {
    List<Vocabulary> searchVocabularies(String query);
}
