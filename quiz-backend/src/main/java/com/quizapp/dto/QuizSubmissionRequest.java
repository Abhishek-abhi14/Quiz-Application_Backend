package com.quizapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record QuizSubmissionRequest(
        @NotEmpty(message = "Submit at least one answer")
        List<@Valid AnswerSubmission> answers
) {}
