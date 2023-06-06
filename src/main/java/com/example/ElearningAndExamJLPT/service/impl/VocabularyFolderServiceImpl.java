package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseVocabulary;
import com.example.ElearningAndExamJLPT.dto.response.ResponseVocabularyFolder;
import com.example.ElearningAndExamJLPT.entity.VocabularyFolder;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.repository.IVocabularyFolderRepository;
import com.example.ElearningAndExamJLPT.service.IVocabularyFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VocabularyFolderServiceImpl implements IVocabularyFolderService {
    @Autowired
    private IVocabularyFolderRepository vocabularyFolderRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<VocabularyFolder> getAll() {
        return vocabularyFolderRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<VocabularyFolder> getById(Long id) {
        return vocabularyFolderRepository.findVocabularyFolderByDeletedFalseAndId(id);
    }

    @Override
    public VocabularyFolder save(VocabularyFolder entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return vocabularyFolderRepository.save(entity);
    }

    @Override
    public VocabularyFolder update(VocabularyFolder entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return vocabularyFolderRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {

//        vocabularyFolderRepository.deleteById(id);
        VocabularyFolder vocabularyFolder = vocabularyFolderRepository.findVocabularyFolderByDeletedFalseAndId(id).get();
        vocabularyFolder.setDeleted(true);
        vocabularyFolderRepository.save(vocabularyFolder);
    }

    @Override
    public List<VocabularyFolder> searchVocabularyFolders(String query) {
//        return vocabularyFolderRepository.searchVocabularyFolders(query);
        return null;
    }

    @Override
    public List<ResponseVocabularyFolder> getAllVocabularyFolders() {
        List<ResponseVocabularyFolder> vocabularyFolders = new ArrayList<>();
        vocabularyFolderRepository.findAllByDeletedFalse().forEach(vocabularyFolder -> {
            ResponseVocabularyFolder responseVocabularyFolder = new ResponseVocabularyFolder();
            responseVocabularyFolder.setId(vocabularyFolder.getId());
            responseVocabularyFolder.setTitle(vocabularyFolder.getTitle());
            responseVocabularyFolder.setLevel(vocabularyFolder.getLevel());
            responseVocabularyFolder.setCount(vocabularyFolder.getVocabularies().size());
            List<ResponseVocabulary> vocabularies = new ArrayList<>();
            vocabularyFolder.getVocabularies().forEach(vocabulary -> {
                ResponseVocabulary responseVocabulary = new ResponseVocabulary();
                responseVocabulary.setId(vocabulary.getId());
                responseVocabulary.setAudio(vocabulary.getAudio());
                responseVocabulary.setMeaning(vocabulary.getMeaning());
                responseVocabulary.setText(vocabulary.getText());
                responseVocabulary.setPronunciation(vocabulary.getPronunciation());
                responseVocabulary.setSpelling(vocabulary.getSpelling());
                responseVocabulary.setExample(vocabulary.getExample());
                vocabularies.add(responseVocabulary);
            });
            responseVocabularyFolder.setVocabularies(vocabularies);
            vocabularyFolders.add(responseVocabularyFolder);
        });
        return vocabularyFolders;
    }

    @Override
    public ResponseVocabularyFolder getVocabularyFolder(VocabularyFolder vocabularyFolder) {
        ResponseVocabularyFolder responseVocabularyFolder = new ResponseVocabularyFolder();
        responseVocabularyFolder.setId(vocabularyFolder.getId());
        responseVocabularyFolder.setTitle(vocabularyFolder.getTitle());
        responseVocabularyFolder.setLevel(vocabularyFolder.getLevel());
        responseVocabularyFolder.setCount(vocabularyFolder.getVocabularies().size());
        List<ResponseVocabulary> vocabularies = new ArrayList<>();
        vocabularyFolder.getVocabularies().forEach(vocabulary -> {
            ResponseVocabulary responseVocabulary = new ResponseVocabulary();
            responseVocabulary.setId(vocabulary.getId());
            responseVocabulary.setAudio(vocabulary.getAudio());
            responseVocabulary.setMeaning(vocabulary.getMeaning());
            responseVocabulary.setText(vocabulary.getText());
            responseVocabulary.setPronunciation(vocabulary.getPronunciation());
            responseVocabulary.setSpelling(vocabulary.getSpelling());
            responseVocabulary.setExample(vocabulary.getExample());
            vocabularies.add(responseVocabulary);
        });
        responseVocabularyFolder.setVocabularies(vocabularies);
        return responseVocabularyFolder;
    }

    @Override
    public List<ResponseVocabularyFolder> getNextVocabularyFolders(Long id) {
        List<ResponseVocabularyFolder> vocabularyFolders = new ArrayList<>();
        vocabularyFolderRepository.findAllByDeletedFalse().forEach(vocabularyFolder -> {
            ResponseVocabularyFolder responseVocabularyFolder = new ResponseVocabularyFolder();
            responseVocabularyFolder.setId(vocabularyFolder.getId());
            responseVocabularyFolder.setTitle(vocabularyFolder.getTitle());
            responseVocabularyFolder.setLevel(vocabularyFolder.getLevel());
            responseVocabularyFolder.setCount(vocabularyFolder.getVocabularies().size());
            List<ResponseVocabulary> vocabularies = new ArrayList<>();
            vocabularyFolder.getVocabularies().forEach(vocabulary -> {
                ResponseVocabulary responseVocabulary = new ResponseVocabulary();
                responseVocabulary.setId(vocabulary.getId());
                responseVocabulary.setAudio(vocabulary.getAudio());
                responseVocabulary.setMeaning(vocabulary.getMeaning());
                responseVocabulary.setText(vocabulary.getText());
                responseVocabulary.setPronunciation(vocabulary.getPronunciation());
                responseVocabulary.setSpelling(vocabulary.getSpelling());
                responseVocabulary.setExample(vocabulary.getExample());
                vocabularies.add(responseVocabulary);
            });
            responseVocabularyFolder.setVocabularies(vocabularies);
            vocabularyFolders.add(responseVocabularyFolder);
        });

        int currentIndex = -1;
        for (int i = 0; i < vocabularyFolders.size(); i++) {
            if (vocabularyFolders.get(i).getId().equals(id)) {
                currentIndex = i;
                break;
            }
        }
        if (currentIndex == -1) {
            return vocabularyFolders;
        }
        // Lấy 4 "vocabulary folders" kế tiếp từ danh sách đã tìm thấy
        int nextIndex = currentIndex + 1;
        int endIndex = currentIndex + 5;
        if (endIndex > vocabularyFolders.size()) {
            endIndex = endIndex % vocabularyFolders.size();
        }

        if (nextIndex < endIndex) {
            return vocabularyFolders.subList(nextIndex, endIndex);
        } else {
            List<ResponseVocabularyFolder> nextVocabularyFolders = new ArrayList<>();
            nextVocabularyFolders.addAll(vocabularyFolders.subList(nextIndex, vocabularyFolders.size()));
            nextVocabularyFolders.addAll(vocabularyFolders.subList(0, endIndex));
            return nextVocabularyFolders;
        }
    }
}
