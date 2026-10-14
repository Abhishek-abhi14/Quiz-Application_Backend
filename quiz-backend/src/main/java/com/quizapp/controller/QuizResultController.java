
package com.quizapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizapp.dto.QuizResultResponse;
import com.quizapp.entity.QuizResult;
import com.quizapp.repository.QuizResultRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quiz-results")
public class QuizResultController {

    private final QuizResultRepository quizResultRepository;

    public QuizResultController(
            QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }

    @PostMapping
    public QuizResultResponse submitResult(
            @RequestBody QuizSubmissionRequest request) {

        int totalQuestions = request.totalQuestions();
        int correctAnswers = request.correctAnswers();

        if (totalQuestions <= 0) {
            throw new IllegalArgumentException(
                    "Total questions must be greater than zero");
        }

        if (correctAnswers < 0 ||
                correctAnswers > totalQuestions) {
            throw new IllegalArgumentException(
                    "Correct answers must be between zero and total questions");
        }

        double percentage =
                (correctAnswers * 100.0) / totalQuestions;

        QuizResult result = new QuizResult(
                totalQuestions,
                correctAnswers,
                percentage
        );

        QuizResult saved = quizResultRepository.save(result);

        return new QuizResultResponse(
                saved.getId(),
                saved.getTotalQuestions(),
                saved.getCorrectAnswers(),
                saved.getPercentage(),
                saved.getSubmittedAt()
        );
    }

    public record QuizSubmissionRequest(
            int totalQuestions,
            int correctAnswers
    ) {}
}
