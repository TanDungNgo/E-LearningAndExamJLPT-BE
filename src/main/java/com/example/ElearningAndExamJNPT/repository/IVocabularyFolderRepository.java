package com.example.ElearningAndExamJNPT.repository;

import com.example.ElearningAndExamJNPT.entity.VocabularyFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVocabularyFolderRepository extends JpaRepository<VocabularyFolder, Long> {
    @Query("SELECT f FROM VocabularyFolder f WHERE " +
            "f.level LIKE CONCAT('%',:query, '%')" +
            "Or f.title LIKE CONCAT('%', :query, '%')")
    List<VocabularyFolder> searchVocabularyFolders(String query);
}
