package com.quizapp.dto;

import java.time.LocalDateTime;

public record QuizResultResponse(
        Long id,
        int totalQuestions,
        int correctAnswers,
        double percentage,
        LocalDateTime submittedAt
) {}
