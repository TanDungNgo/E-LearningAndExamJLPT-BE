package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseVocabularyFolder;
import com.example.ElearningAndExamJLPT.entity.VocabularyFolder;

import java.util.List;

public interface IVocabularyFolderService extends IService<VocabularyFolder,Long> {
    List<VocabularyFolder> searchVocabularyFolders(String query);
    List<ResponseVocabularyFolder> getAllVocabularyFolders();
    ResponseVocabularyFolder getVocabularyFolder(VocabularyFolder vocabularyFolder);
    List<ResponseVocabularyFolder> getNextVocabularyFolders(Long id);
}
