package com.quizapp.config;

import com.quizapp.entity.Question;
import com.quizapp.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner loadSampleQuestions(QuestionRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Question(
                        "Which component stores electrical charge?",
                        "Resistor", "Capacitor", "Diode", "Fuse", "B", "Electronics"));
                repository.save(new Question(
                        "What is the capital of India?",
                        "Mumbai", "Chennai", "New Delhi", "Hyderabad", "C", "General Knowledge"));
                repository.save(new Question(
                        "Which planet is known as the Red Planet?",
                        "Venus", "Jupiter", "Saturn", "Mars", "D", "Science"));
                repository.save(new Question(
                        "Which language runs directly in a web browser?",
                        "JavaScript", "SQL", "C", "Bash", "A", "Technology"));
                repository.save(new Question(
                        "What does CPU stand for?",
                        "Computer Power Utility", "Central Processing Unit",
                        "Control Program Unit", "Central Print Unit", "B", "Computer Science"));
            }
        };
    }
}
