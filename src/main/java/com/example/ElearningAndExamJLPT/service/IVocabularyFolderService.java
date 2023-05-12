package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.VocabularyFolder;

import java.util.List;

public interface IVocabularyFolderService extends IService<VocabularyFolder,Long> {
    List<VocabularyFolder> searchVocabularyFolders(String query);
}
