package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.converter.VocabularyConverter;
import com.example.ElearningAndExamJNPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJNPT.entity.Vocabulary;
import com.example.ElearningAndExamJNPT.entity.VocabularyFolder;
import com.example.ElearningAndExamJNPT.repository.IVocabularyFolderRepository;
import com.example.ElearningAndExamJNPT.repository.IVocabularyRepository;
import com.example.ElearningAndExamJNPT.service.IVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VocabularyServiceImpl implements IVocabularyService {
    @Autowired
    private IVocabularyRepository vocabularyRepository;
    @Autowired
    private IVocabularyFolderRepository vocabularyFolderRepository;
    @Autowired
    private VocabularyConverter vocabularyConverter;
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
        return vocabularyRepository.save(entity);
    }

    @Override
    public Vocabulary update(Vocabulary entity) {
        return vocabularyRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        vocabularyRepository.deleteById(id);
    }

    @Override
    public Vocabulary save(VocabularyDTO dto) {
        Vocabulary entity = vocabularyConverter.toVocabularyEntity(dto);
        VocabularyFolder vocabularyFolder = vocabularyFolderRepository.findById(dto.getVocabularyFolder_id()).get();
        entity.setVocabularyFolder(vocabularyFolder);
        return vocabularyRepository.save(entity);
    }

    @Override
    public List<Vocabulary> searchVocabularies(String query) {
        return vocabularyRepository.searchVocabularies(query);
    }
}
