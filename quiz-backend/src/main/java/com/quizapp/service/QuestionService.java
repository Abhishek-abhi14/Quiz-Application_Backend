package com.quizapp.service;

import com.quizapp.dto.QuestionResponse;
import com.quizapp.entity.Question;
import com.quizapp.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public QuestionResponse getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
        return toResponse(question);
    }

    private QuestionResponse toResponse(Question q) {
        // Intentionally omit the correct answer from public API responses.
        return new QuestionResponse(q.getId(), q.getQuestionText(), q.getOptionA(),
                q.getOptionB(), q.getOptionC(), q.getOptionD(), q.getSubject());
    }
}
