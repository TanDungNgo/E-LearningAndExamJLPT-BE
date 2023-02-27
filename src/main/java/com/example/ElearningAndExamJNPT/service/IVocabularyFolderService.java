package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.entity.VocabularyFolder;

import java.util.List;

public interface IVocabularyFolderService extends IService<VocabularyFolder,Long> {
    List<VocabularyFolder> searchVocabularyFolders(String query);
}
