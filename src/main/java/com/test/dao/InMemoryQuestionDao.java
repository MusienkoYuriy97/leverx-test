package com.test.dao;

import com.test.entity.Question;
import java.util.*;

public class InMemoryQuestionDao implements QuestionDao{
    private static List<Question> questions = new ArrayList<>();

    @Override
    public List<Question> getAllQuestions() {
        return questions;
    }

    public void saveQuestion(Question question){
        questions.add(question);
    }

    static {
        questions.add(createQuestion("Какой город является столицей Беларуси?",
                "Минск","Милан","Брест","Могилев",
                1));

        questions.add(createQuestion("Какой город является столицей Англии?",
                "Минск","Милан","Лондон","Могилев",
                3));

        questions.add(createQuestion("Какой город является столицей Италии?",
                "Минск","Милан","Лондон","Могилев",
                2));
        questions.add(createQuestion("Какой город является столицей Испании?",
                "Минск","Милан","Лондон","Мадрид",
                4));
        questions.add(createQuestion("Какой город является столицей США?",
                "Вашингтон","Милан","Лондон","Могилев",
                1));
    }

    private static Question createQuestion(String title, String a, String b, String c, String d, Integer... rightAnswers){
        Question question = new Question();
        question.setTitle(title);
        question.setAnswers(fillAnswers(a,b,c,d));
        question.setRightAnswers(new HashSet<>(Arrays.asList(rightAnswers)));
        return question;
    }

    private static List<String> fillAnswers(String... strings) {
        return new ArrayList<>(Arrays.asList(strings));
    }
}
