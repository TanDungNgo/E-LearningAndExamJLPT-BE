package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.GrammarDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Grammar;
import com.example.ElearningAndExamJLPT.service.impl.GrammarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/grammars")
public class GrammarController {
    @Autowired
    private GrammarServiceImpl grammarService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllGrammar() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", grammarService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> createGrammar(@RequestBody @Valid GrammarDTO grammarDTO) {
        Grammar grammar = new Grammar();
        grammar.setText(grammarDTO.getText());
        grammar.setMeans(grammarDTO.getMeans());
        grammar.setExplanation(grammarDTO.getExplanation());
        grammar.setLevel(grammarDTO.getLevel());
        grammar.setExample(grammarDTO.getExample());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert grammar successfully", grammarService.save(grammar))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
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
    public ResponseEntity<ResponseObject> update(@PathVariable("id") final Long id, @RequestBody final GrammarDTO newGrammar) {
        Grammar updatedGrammar = grammarService.getById(id)
                .map(grammar -> {
                    grammar.setText(newGrammar.getText());
                    grammar.setMeans(newGrammar.getMeans());
                    grammar.setExplanation(newGrammar.getExplanation());
                    grammar.setExample(newGrammar.getExample());
                    grammar.setLevel(newGrammar.getLevel());
                    return grammarService.update(grammar);
                }).orElseGet(() -> {
                    Grammar grammar = new Grammar();
                    grammar.setText(newGrammar.getText());
                    grammar.setMeans(newGrammar.getMeans());
                    grammar.setExplanation(newGrammar.getExplanation());
                    grammar.setLevel(newGrammar.getLevel());
                    grammar.setExample(newGrammar.getExample());
                    grammar.setId(id);
                    return grammarService.save(grammar);
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
    public ResponseEntity<List<Grammar>> searchGrammars(@RequestParam("query") String query) {
        return ResponseEntity.ok(grammarService.searchGrammars(query));
    }
}
