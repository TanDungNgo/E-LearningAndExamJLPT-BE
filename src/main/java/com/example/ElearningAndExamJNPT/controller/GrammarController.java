package com.example.ElearningAndExamJNPT.controller;

import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.Grammar;
import com.example.ElearningAndExamJNPT.service.impl.GrammarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grammars")
public class GrammarController {
    @Autowired
    private GrammarServiceImpl grammarService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllGrammar() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", grammarService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody Grammar grammar) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert grammar successfully", grammarService.save(grammar))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Grammar> foundGrammar = grammarService.getById(id);
        return foundGrammar.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query grammar successfully", foundGrammar)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find grammar with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody Grammar newGrammar) {
        Grammar updatedGrammar = grammarService.getById(id)
                .map(grammar -> {
                    grammar.setText(newGrammar.getText());
                    grammar.setMeans(newGrammar.getMeans());
                    grammar.setExplanation(newGrammar.getExplanation());
                    grammar.setExample(newGrammar.getExample());
                    grammar.setLevel(newGrammar.getLevel());
                    return grammarService.save(grammar);
                }).orElseGet(() -> {
                    newGrammar.setId(id);
                    return grammarService.save(newGrammar);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update grammar successfully", updatedGrammar)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<Grammar> foundGrammar = grammarService.getById(id);
        if (foundGrammar.isPresent()) {
            grammarService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete grammar successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find grammar with id = " + id, "")
            );
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Grammar>> searchGrammars(@RequestParam("query") String query){
        return ResponseEntity.ok(grammarService.searchGrammars(query));
    }
}
