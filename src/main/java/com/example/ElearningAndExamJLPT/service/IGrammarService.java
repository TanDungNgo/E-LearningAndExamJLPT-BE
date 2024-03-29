package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseGrammar;
import com.example.ElearningAndExamJLPT.entity.Grammar;

import java.util.List;

public interface IGrammarService extends IService<Grammar, Long>{
    List<ResponseGrammar> searchGrammars(String query);
    List<ResponseGrammar> getAllGrammars();
    ResponseGrammar getGrammar(Grammar grammar);
}
