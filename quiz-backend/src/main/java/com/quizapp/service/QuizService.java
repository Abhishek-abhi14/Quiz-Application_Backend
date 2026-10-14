package com.quizapp.service;

import com.quizapp.dto.AnswerSubmission;
import com.quizapp.dto.QuizResultResponse;
import com.quizapp.dto.QuizSubmissionRequest;
import com.quizapp.entity.Question;
import com.quizapp.entity.QuizResult;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class QuizService {
    private final QuestionRepository questionRepository;
    private final QuizResultRepository resultRepository;

    public QuizService(QuestionRepository questionRepository, QuizResultRepository resultRepository) {
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
    }

    @Transactional
    public QuizResultResponse submit(QuizSubmissionRequest request) {
        List<AnswerSubmission> answers = request.answers();
        Set<Long> submittedIds = new HashSet<>();
        int correct = 0;

        for (AnswerSubmission answer : answers) {
            if (!submittedIds.add(answer.questionId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Duplicate answer for question ID: " + answer.questionId());
            }
            Question question = questionRepository.findById(answer.questionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Unknown question ID: " + answer.questionId()));

            if (answer.selectedOption() != null &&
                    question.getCorrectAnswer().equals(answer.selectedOption().toUpperCase(Locale.ROOT))) {
                correct++;
            }
        }

        int total = answers.size();
        double percentage = total == 0 ? 0.0 : Math.round((correct * 10000.0 / total)) / 100.0;
        QuizResult saved = resultRepository.save(new QuizResult(total, correct, percentage));
        return toResponse(saved);
    }

    public QuizResultResponse getResult(Long id) {
        QuizResult result = resultRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz result not found"));
        return toResponse(result);
    }

    private QuizResultResponse toResponse(QuizResult r) {
        return new QuizResultResponse(r.getId(), r.getTotalQuestions(), r.getCorrectAnswers(),
                r.getPercentage(), r.getSubmittedAt());
    }
}
