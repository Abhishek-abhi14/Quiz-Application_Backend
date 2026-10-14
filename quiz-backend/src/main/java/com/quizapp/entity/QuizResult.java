package com.quizapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int totalQuestions;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private double percentage;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    protected QuizResult() {}

    public QuizResult(int totalQuestions, int correctAnswers, double percentage) {
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.percentage = percentage;
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public double getPercentage() { return percentage; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}
