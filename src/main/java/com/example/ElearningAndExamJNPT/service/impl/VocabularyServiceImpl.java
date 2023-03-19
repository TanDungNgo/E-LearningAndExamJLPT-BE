package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.converter.VocabularyConverter;
import com.example.ElearningAndExamJNPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJNPT.entity.Vocabulary;
import com.example.ElearningAndExamJNPT.entity.VocabularyFolder;
import com.example.ElearningAndExamJNPT.repository.IUserRepository;
import com.example.ElearningAndExamJNPT.repository.IVocabularyFolderRepository;
import com.example.ElearningAndExamJNPT.repository.IVocabularyRepository;
import com.example.ElearningAndExamJNPT.service.IVocabularyService;
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
        return vocabularyRepository.findAll();
    }

    @Override
    public Optional<Vocabulary> getById(Long id) {
        return vocabularyRepository.findById(id);
    }

    @Override
    public Vocabulary save(Vocabulary entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return vocabularyRepository.save(entity);
    }

    @Override
    public Vocabulary update(Vocabulary entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return vocabularyRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        vocabularyRepository.deleteById(id);
    }

    @Override
    public List<Vocabulary> searchVocabularies(String query) {
        return vocabularyRepository.searchVocabularies(query);
    }
}
