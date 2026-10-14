package com.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AnswerSubmission(
        @NotNull Long questionId,
        @Pattern(regexp = "[ABCDabcd]", message = "selectedOption must be A, B, C, or D")
        String selectedOption
) {}
