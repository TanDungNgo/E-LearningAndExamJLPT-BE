package com.example.ElearningAndExamJNPT.converter;

import com.example.ElearningAndExamJNPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJNPT.entity.Vocabulary;
import org.springframework.stereotype.Component;

@Component
public class VocabularyConverter {
    public Vocabulary toVocabularyEntity(VocabularyDTO dto) {
        Vocabulary entity = new Vocabulary();
        entity.setText(dto.getText());
        entity.setPronunciation(dto.getPronunciation());
        entity.setSpelling(dto.getSpelling());
        entity.setExample(dto.getExample());
        entity.setAudio(dto.getAudio());
        return entity;
    }
}
