package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.Grammar;

import java.util.List;

public interface IGrammarService extends IService<Grammar, Long>{
    List<Grammar> searchGrammars(String query);
}
