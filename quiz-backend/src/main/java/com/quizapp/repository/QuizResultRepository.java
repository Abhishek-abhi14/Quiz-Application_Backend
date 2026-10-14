
package com.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizapp.entity.QuizResult;

public interface QuizResultRepository
        extends JpaRepository<QuizResult, Long> {
}
