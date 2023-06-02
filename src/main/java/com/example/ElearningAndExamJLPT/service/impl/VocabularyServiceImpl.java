package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Vocabulary;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.repository.IVocabularyFolderRepository;
import com.example.ElearningAndExamJLPT.repository.IVocabularyRepository;
import com.example.ElearningAndExamJLPT.service.IVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VocabularyServiceImpl implements IVocabularyService {
    @Autowired
    private IVocabularyRepository vocabularyRepository;
    @Autowired
    private IVocabularyFolderRepository vocabularyFolderRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Vocabulary> getAll() {
        return vocabularyRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Vocabulary> getById(Long id) {
        return vocabularyRepository.findVocabularyByDeletedFalseAndId(id);
    }

    @Override
    public Vocabulary save(Vocabulary entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return vocabularyRepository.save(entity);
    }

    @Override
    public Vocabulary update(Vocabulary entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return vocabularyRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
//        vocabularyRepository.deleteById(id);
        Vocabulary vocabulary = vocabularyRepository.findVocabularyByDeletedFalseAndId(id).get();
        vocabulary.setDeleted(true);
        vocabularyRepository.save(vocabulary);
    }

    @Override
    public List<Vocabulary> searchVocabularies(String query) {
        return vocabularyRepository.searchVocabularies(query);
    }
}
