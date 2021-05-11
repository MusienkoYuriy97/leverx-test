package com.test.dao;

import com.test.entity.Question;
import java.util.List;

public interface QuestionDao {
    List<Question> getAllQuestions();
    void saveQuestion(Question question);
}
