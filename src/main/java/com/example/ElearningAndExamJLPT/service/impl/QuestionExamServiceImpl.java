package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.QuestionDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.LanguageKnowledgeQuestion;
import com.example.ElearningAndExamJLPT.entity.ListeningQuestion;
import com.example.ElearningAndExamJLPT.entity.ReadingQuestion;
import com.example.ElearningAndExamJLPT.repository.*;
import com.example.ElearningAndExamJLPT.service.IQuestionExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionExamServiceImpl implements IQuestionExamService {
    @Autowired
    private IExamRepository examRepository;
    @Autowired
    private ILanguageKnowledgeQuestionRepository languageKnowledgeQuestionRepository;
    @Autowired
    private IReadingQuestionRepository readingQuestionRepository;
    @Autowired
    private IListeningQuestionRepository listeningQuestionRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Object saveQuestionExam(QuestionDTO questionDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        if(questionDTO.getType() == 0)
        {
            LanguageKnowledgeQuestion languageKnowledgeQuestion = new LanguageKnowledgeQuestion();
            languageKnowledgeQuestion.setTitle(questionDTO.getTitle());
            languageKnowledgeQuestion.setText(questionDTO.getText());
            languageKnowledgeQuestion.setLevel(questionDTO.getLevel());
            languageKnowledgeQuestion.setExam(examRepository.findExamByDeletedFalseAndId(questionDTO.getExamId()).get());
            languageKnowledgeQuestion.setCorrectAnswer(questionDTO.getCorrectAnswer());
            languageKnowledgeQuestion.setExplanation(questionDTO.getExplanation());
            languageKnowledgeQuestion.setOption1(questionDTO.getAnswers().get(0) == null ? null : questionDTO.getAnswers().get(0));
            languageKnowledgeQuestion.setOption2(questionDTO.getAnswers().get(1) == null ? null : questionDTO.getAnswers().get(1));
            languageKnowledgeQuestion.setOption3(questionDTO.getAnswers().get(2) == null ? null : questionDTO.getAnswers().get(2));
            languageKnowledgeQuestion.setOption4(questionDTO.getAnswers().get(3) == null ? null : questionDTO.getAnswers().get(3));
            languageKnowledgeQuestion.setCreatedDate(now);
            languageKnowledgeQuestion.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
            return languageKnowledgeQuestionRepository.save(languageKnowledgeQuestion);
        }
        else if(questionDTO.getType() == 1)
        {
            ReadingQuestion readingQuestion = new ReadingQuestion();
            readingQuestion.setTitle(questionDTO.getTitle());
            readingQuestion.setText(questionDTO.getText());
            readingQuestion.setLevel(questionDTO.getLevel());
            readingQuestion.setExam(examRepository.findExamByDeletedFalseAndId(questionDTO.getExamId()).get());
            readingQuestion.setCorrectAnswer(questionDTO.getCorrectAnswer());
            readingQuestion.setExplanation(questionDTO.getExplanation());
            readingQuestion.setOption1(questionDTO.getAnswers().get(0) == null ? null : questionDTO.getAnswers().get(0));
            readingQuestion.setOption2(questionDTO.getAnswers().get(1) == null ? null : questionDTO.getAnswers().get(1));
            readingQuestion.setOption3(questionDTO.getAnswers().get(2) == null ? null : questionDTO.getAnswers().get(2));
            readingQuestion.setOption4(questionDTO.getAnswers().get(3) == null ? null : questionDTO.getAnswers().get(3));
            readingQuestion.setImage(questionDTO.getImage());
            readingQuestion.setCreatedDate(now);
            readingQuestion.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
            return readingQuestionRepository.save(readingQuestion);
        }
        else if(questionDTO.getType() == 2)
        {
            ListeningQuestion listeningQuestion = new ListeningQuestion();
            listeningQuestion.setTitle(questionDTO.getTitle());
            listeningQuestion.setText(questionDTO.getText());
            listeningQuestion.setLevel(questionDTO.getLevel());
            listeningQuestion.setExam(examRepository.findExamByDeletedFalseAndId(questionDTO.getExamId()).get());
            listeningQuestion.setCorrectAnswer(questionDTO.getCorrectAnswer());
            listeningQuestion.setExplanation(questionDTO.getExplanation());
            listeningQuestion.setOption1(questionDTO.getAnswers().get(0) == null ? null : questionDTO.getAnswers().get(0));
            listeningQuestion.setOption2(questionDTO.getAnswers().get(1) == null ? null : questionDTO.getAnswers().get(1));
            listeningQuestion.setOption3(questionDTO.getAnswers().get(2) == null ? null : questionDTO.getAnswers().get(2));
            listeningQuestion.setOption4(questionDTO.getAnswers().get(3) == null ? null : questionDTO.getAnswers().get(3));
            listeningQuestion.setAudioFile(questionDTO.getAudioFile());
            listeningQuestion.setImage(questionDTO.getImage());
            listeningQuestion.setCreatedDate(now);
            listeningQuestion.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
            return listeningQuestionRepository.save(listeningQuestion);
        }
        return null;
    }

    @Override
    public List<ResponseQuestion> getAllQuestionExam(Exam exam) {
        List<ResponseQuestion> responseQuestions = new ArrayList<>();
        List<LanguageKnowledgeQuestion> languageKnowledgeQuestions = languageKnowledgeQuestionRepository.findByDeletedFalseAndExam(exam);
        List<ReadingQuestion> readingQuestions = readingQuestionRepository.findByDeletedFalseAndExam(exam);
        List<ListeningQuestion> listeningQuestions = listeningQuestionRepository.findByDeletedFalseAndExam(exam);
        for(LanguageKnowledgeQuestion languageKnowledgeQuestion : languageKnowledgeQuestions)
        {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(languageKnowledgeQuestion.getId());
            responseQuestion.setTitle(languageKnowledgeQuestion.getTitle());
            responseQuestion.setText(languageKnowledgeQuestion.getText());
            responseQuestion.setCorrectAnswer(languageKnowledgeQuestion.getCorrectAnswer());
            responseQuestion.setExplanation(languageKnowledgeQuestion.getExplanation());
            responseQuestion.setOption1(languageKnowledgeQuestion.getOption1());
            responseQuestion.setOption2(languageKnowledgeQuestion.getOption2());
            responseQuestion.setOption3(languageKnowledgeQuestion.getOption3());
            responseQuestion.setOption4(languageKnowledgeQuestion.getOption4());
            responseQuestions.add(responseQuestion);
        }
        for (ReadingQuestion readingQuestion : readingQuestions)
        {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(readingQuestion.getId());
            responseQuestion.setTitle(readingQuestion.getTitle());
            responseQuestion.setText(readingQuestion.getText());
            responseQuestion.setCorrectAnswer(readingQuestion.getCorrectAnswer());
            responseQuestion.setExplanation(readingQuestion.getExplanation());
            responseQuestion.setOption1(readingQuestion.getOption1());
            responseQuestion.setOption2(readingQuestion.getOption2());
            responseQuestion.setOption3(readingQuestion.getOption3());
            responseQuestion.setOption4(readingQuestion.getOption4());
            responseQuestion.setImage(readingQuestion.getImage());
            responseQuestions.add(responseQuestion);
        }
        for (ListeningQuestion listeningQuestion : listeningQuestions)
        {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(listeningQuestion.getId());
            responseQuestion.setTitle(listeningQuestion.getTitle());
            responseQuestion.setText(listeningQuestion.getText());
            responseQuestion.setCorrectAnswer(listeningQuestion.getCorrectAnswer());
            responseQuestion.setExplanation(listeningQuestion.getExplanation());
            responseQuestion.setOption1(listeningQuestion.getOption1());
            responseQuestion.setOption2(listeningQuestion.getOption2());
            responseQuestion.setOption3(listeningQuestion.getOption3());
            responseQuestion.setOption4(listeningQuestion.getOption4());
            responseQuestion.setImage(listeningQuestion.getImage());
            responseQuestion.setAudioFile(listeningQuestion.getAudioFile());
            responseQuestions.add(responseQuestion);
        }
        return responseQuestions;
    }
}