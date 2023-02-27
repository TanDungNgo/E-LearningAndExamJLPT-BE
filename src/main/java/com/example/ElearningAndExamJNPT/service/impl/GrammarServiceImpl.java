package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.entity.Grammar;
import com.example.ElearningAndExamJNPT.repository.IGrammarRepository;
import com.example.ElearningAndExamJNPT.service.IGrammarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrammarServiceImpl implements IGrammarService {

    @Autowired
    private IGrammarRepository grammarRepository;
    @Override
    public List<Grammar> getAll() {
        return grammarRepository.findAll();
    }

    @Override
    public Optional<Grammar> getById(Long id) {
        return Optional.ofNullable(grammarRepository.findById(id))
                .orElseThrow(() -> new UsernameNotFoundException("Grammar by " + id + " can not found"));
    }

    @Override
    public Grammar save(Grammar entity) {
        return grammarRepository.save(entity);
    }

    @Override
    public Grammar update(Grammar entity) {
        return grammarRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        grammarRepository.deleteById(id);
    }
}
