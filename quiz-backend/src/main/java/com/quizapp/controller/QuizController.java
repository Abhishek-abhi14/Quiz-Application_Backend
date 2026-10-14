package com.quizapp.controller;

import com.quizapp.dto.QuizResultResponse;
import com.quizapp.dto.QuizSubmissionRequest;
import com.quizapp.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/submit")
    public QuizResultResponse submitQuiz(@Valid @RequestBody QuizSubmissionRequest request) {
        return quizService.submit(request);
    }

    @GetMapping("/results/{id}")
    public QuizResultResponse getResult(@PathVariable Long id) {
        return quizService.getResult(id);
    }
}
