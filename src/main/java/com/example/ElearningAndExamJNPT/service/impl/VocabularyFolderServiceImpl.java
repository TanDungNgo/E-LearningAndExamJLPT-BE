package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.entity.VocabularyFolder;
import com.example.ElearningAndExamJNPT.repository.IVocabularyFolderRepository;
import com.example.ElearningAndExamJNPT.service.IVocabularyFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VocabularyFolderServiceImpl implements IVocabularyFolderService{
    @Autowired
    private IVocabularyFolderRepository vocabularyFolderRepository;
    @Override
    public List<VocabularyFolder> getAll() {
        return vocabularyFolderRepository.findAll();
    }

    @Override
    public Optional<VocabularyFolder> getById(Long id) {
        return vocabularyFolderRepository.findById(id);
    }

    @Override
    public VocabularyFolder save(VocabularyFolder entity) {
        return vocabularyFolderRepository.save(entity);
    }

    @Override
    public VocabularyFolder update(VocabularyFolder entity) {
        return vocabularyFolderRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        vocabularyFolderRepository.deleteById(id);
    }

    @Override
    public List<VocabularyFolder> searchVocabularyFolders(String query) {
        return vocabularyFolderRepository.searchVocabularyFolders(query);
    }
}
