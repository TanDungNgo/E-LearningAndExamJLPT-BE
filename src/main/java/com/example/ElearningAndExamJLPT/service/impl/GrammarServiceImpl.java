package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseGrammar;
import com.example.ElearningAndExamJLPT.entity.Grammar;
import com.example.ElearningAndExamJLPT.repository.IGrammarRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IGrammarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrammarServiceImpl implements IGrammarService {

    @Autowired
    private IGrammarRepository grammarRepository;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<Grammar> getAll() {
        return grammarRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Grammar> getById(Long id) {
        return Optional.ofNullable(grammarRepository.findGrammarByDeletedFalseAndId(id))
                .orElseThrow(() -> new UsernameNotFoundException("Grammar by " + id + " can not found"));
    }

    @Override
    public Grammar save(Grammar entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return grammarRepository.save(entity);
    }

    @Override
    public Grammar update(Grammar entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return grammarRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
//        grammarRepository.deleteById(id);
        Grammar grammar = grammarRepository.findGrammarByDeletedFalseAndId(id).get();
        grammar.setDeleted(true);
        grammarRepository.save(grammar);
    }

    @Override
    public List<Grammar> searchGrammars(String query) {
        List<Grammar> grammars = grammarRepository.searchGrammars(query);
        return grammars;
    }

    @Override
    public List<ResponseGrammar> getAllGrammars() {
        List<ResponseGrammar> responseGrammars = new ArrayList<>();
        List<Grammar> grammars = grammarRepository.findAllByDeletedFalse();
        for (Grammar grammar : grammars) {
            ResponseGrammar responseGrammar = new ResponseGrammar();
            responseGrammar.setId(grammar.getId());
            responseGrammar.setText(grammar.getText());
            responseGrammar.setExplanation(grammar.getExplanation());
            responseGrammar.setMeans(grammar.getMeans());
            responseGrammar.setLevel(grammar.getLevel());
            responseGrammar.setExample(grammar.getExample());
            responseGrammars.add(responseGrammar);
        }
        return responseGrammars;
    }

    @Override
    public ResponseGrammar getGrammar(Grammar grammar) {
        ResponseGrammar responseGrammar = new ResponseGrammar();
        responseGrammar.setId(grammar.getId());
        responseGrammar.setText(grammar.getText());
        responseGrammar.setExplanation(grammar.getExplanation());
        responseGrammar.setMeans(grammar.getMeans());
        responseGrammar.setLevel(grammar.getLevel());
        responseGrammar.setExample(grammar.getExample());
        return responseGrammar;
    }
}
