package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.converter.VocabularyConverter;
import com.example.ElearningAndExamJLPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Vocabulary;
import com.example.ElearningAndExamJLPT.service.impl.VocabularyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vocabularies")
public class VocabularyController {
    @Autowired
    private VocabularyServiceImpl vocabularyService;
    @Autowired
    private VocabularyConverter vocabularyConverter;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllVocabulary() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", vocabularyService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody VocabularyDTO vocabularyDTO) {
        Vocabulary vocabulary = vocabularyConverter.toVocabularyEntity(vocabularyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert vocabulary successfully", vocabularyService.save(vocabulary))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Vocabulary> foundVocabulary = vocabularyService.getById(id);
        return foundVocabulary.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query vocabulary successfully", foundVocabulary)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find vocabulary with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes= "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody VocabularyDTO newVocabulary) {
        Vocabulary updatedVocabulary = vocabularyService.getById(id)
                .map(vocabulary -> {
                    vocabulary = vocabularyConverter.toVocabularyEntity(newVocabulary);
                    return vocabularyService.save(vocabulary);
                }).orElseGet(()->{
                    Vocabulary vocabulary = vocabularyConverter.toVocabularyEntity(newVocabulary);
                    vocabulary.setId(id);
                    return vocabularyService.save(vocabulary);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update vocabularyFolder successfully", updatedVocabulary)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<Vocabulary> foundVocabulary = vocabularyService.getById(id);
        if (foundVocabulary.isPresent()) {
            vocabularyService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete vocabulary successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find vocabulary with id = " + id, "")
            );
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Vocabulary>> searchVocabularies(@RequestParam("query") String query){
        return ResponseEntity.ok(vocabularyService.searchVocabularies(query));
    }
}
