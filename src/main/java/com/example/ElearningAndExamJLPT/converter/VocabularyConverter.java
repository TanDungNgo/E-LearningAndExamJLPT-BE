package com.example.ElearningAndExamJLPT.converter;

import com.example.ElearningAndExamJLPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJLPT.entity.Vocabulary;
import com.example.ElearningAndExamJLPT.service.impl.VocabularyFolderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VocabularyConverter {
    @Autowired
    private VocabularyFolderServiceImpl vocabularyFolderService;

    public Vocabulary toVocabularyEntity(VocabularyDTO dto) {
        Vocabulary entity = new Vocabulary();
        entity.setText(dto.getText());
        entity.setPronunciation(dto.getPronunciation());
        entity.setSpelling(dto.getSpelling());
        entity.setExample(dto.getExample());
        entity.setAudio(dto.getAudio());
        entity.setVocabularyFolder(vocabularyFolderService.getById(dto.getVocabularyFolder_id()).get());
        return entity;
    }
}
