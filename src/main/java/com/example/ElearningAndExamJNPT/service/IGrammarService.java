package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.entity.Grammar;

import java.util.List;

public interface IGrammarService extends IService<Grammar, Long>{
    List<Grammar> searchGrammars(String query);
}
