package com.test.service;

import com.test.dao.QuestionDao;
import com.test.dao.InMemoryQuestionDao;
import com.test.entity.Question;
import com.test.exception.InputException;
import java.util.*;

public class QuestionService {
    private QuestionDao questions = new InMemoryQuestionDao();

    public List<Question> getAll() {
        return questions.getAllQuestions();
    }

    /**
     * Это основной метод сохранения данных о вопросе
     @param title - Заголовок вопроса,
     @param a,b,c,d - Текст вопроса,
     @param numbers - Номера правильных ответов через ","
     Метод проверяет корректность введенных пользователем данных и
     сохраняет вопрос в список через questionDao
     @exception InputException - при неккоректных данных выбрасывается ошибка в конструктор
     передается message.
     */
    public void save(String title, String a, String b, String c, String d, String numbers) throws InputException {
        if (title == null || title.equals("")){
            throw new InputException("Вы не ввели текст вопроса. Попробуйте добавить вопрос заново.");
        }
        for (Question question : getAll()) {
            if (question.getTitle().equals(title)){
                throw new InputException("Вопрос с таким названием уже существует.");
            }
        }
        if (numbers == null && numbers.equals("")) {
            throw new InputException("Поле может содержать только уникальные цифры 1, 2, 3, 4, разделенные запятой. " +
                    "Попробуйте добавить вопрос заново.");
        }
        List<String> answers = getAnswersList(a,b,c,d);
        Set<Integer> rightAnswers = getRightAnswerNumbersSet(numbers);
        questions.saveQuestion(new Question(title,answers,rightAnswers));
    }

    /**
     * Вспомогательный закрытый метод для метода save необходим для переобразования массива String в список строк
     * и проверки пустых полей
     @d
     @param answers - Массив вопросов
     @exception InputException - если переменная emptyField отлична от 0
     @return answers - Список вопросов
     */
    private List<String> getAnswersList(String... answers) throws InputException {
        List<String> answersList = new ArrayList<>();
        List<Integer> emptyField = new ArrayList<>();
        int count = 0;
        for (String answ : answers) {
            count++;
            if (answ == null || answ.equals("")){
                emptyField.add(count);
                continue;
            }
            answersList.add(answ);
        }
        if (!emptyField.isEmpty()){
            throw new InputException("Вы не ввели текст " + emptyField + " вариантов ответа. " +
                    "Попробуйте добавить вопрос заново.");
        }
        return answersList;
    }

    /**
     * Вспомогательный закрытый метод для метода save проверяет введённые данные номеров правильных ответов на вопрос
     @param numbers - Номера правильных ответов введенных через ","
     @exception InputException - ошибка корректности введенных данных
     @return rightAnswer - Список уникальных цифр соответствующих правильным вариантам ответа на вопрос
     */
    private Set<Integer> getRightAnswerNumbersSet(String numbers) throws InputException {
        Set<Integer> answerNumbers = new HashSet<>();

        for (String number : numbers.split(",")) {
            try {
                answerNumbers.add(Integer.parseInt(number));
            }catch (NumberFormatException ex){
                throw new InputException("Поле может содержать только уникальные цифры 1, 2, 3, 4, разделенные запятой. " +
                        "Попробуйте добавить вопрос заново.");
            }
        }
        if (answerNumbers.size() != numbers.split(",").length){
            throw new InputException("Поле может содержать только уникальные цифры 1, 2, 3, 4, разделенные запятой. " +
                    "Попробуйте добавить вопрос заново.");
        }
        for (Integer number : answerNumbers) {
            if (number < 1 || number > 4){
                throw new InputException("Поле может содержать только уникальные цифры 1, 2, 3, 4, разделенные запятой. " +
                        "Попробуйте добавить вопрос заново.");
            }
        }
        return answerNumbers;
    }

    /**
     * Основной метод проверки ответов которые пользователь отметил проходя тест
     @param answers - Массив строк - элемент массива - строка из двух цифр прим:
     answers[0] = "12" 
     где 1 это номер варианта ответа, а 2 -  порядковый номер вопроса на который отвечал пользователь,
     соответственно если в массиве есть "12","32" - это означает что на 2-й вопрос выбрано два
     варианта ответа 1,3.
     Если какой-то из номеров вопороса не пришел в список значит ответы для этого вопроса
     не были заполнены и пользователю выводится ошибка заполнения.
     @return String с сообщением в зависимости от количества правильных ответов.
     */
    public String checkTest(String[] answers) {
        if (answers == null){
            return null;
        }

        Set<Integer> questionNum = new HashSet<>();
        for (String a : answers) {
            questionNum.add(Integer.parseInt(a) % 10);
        }

        if (questionNum.size() != getAll().size()){
            return "Все вопросы должны иметь хотя бы один выбранный вариант ответа. Пройдите тест заново.";
        }

        Map<Integer,Set<Integer>> userAnswers = userAnswerNumbers(answers);
        Map<Integer,String> wrongAnswers = new HashMap<>();
        for (int i = 0; i < getAll().size(); i++) {
            if (!getAll().get(i).getRightAnswers().equals(userAnswers.get(i+1))){
                wrongAnswers.put(i+1, getAll().get(i).getTitle());
            }
        }
        
        if (wrongAnswers.isEmpty()){
            return String.format("Ваш результат %s из %<s. Вы молодец!", getAll().size());
        }

        StringBuilder response = new StringBuilder("Вы неправильно ответили на вопросы:<br>");
        for (Map.Entry<Integer, String> entry : wrongAnswers.entrySet()) {
            response.append(entry.getKey()+". ");
            response.append(entry.getValue()+".<br>");
        }
        int result = getAll().size()-wrongAnswers.size();
        response.append(String.format("Ваш результат %s из %s.",
                result,
                getAll().size()));

        return String.valueOf(response);
    }


    /**
     * Вспомогательный закрытый метод для метода checkTest
     @param answers - Массив строк(см метод checkTest)
     @return HasMap - ключ(номер вопроса), значение(номера чекнутых ответов)
     */
    private Map<Integer,Set<Integer>> userAnswerNumbers(String[] answers){
        Map<Integer,Set<Integer>> userAnswers = new HashMap<>();
        for (String answer : answers) {
            int q = Integer.parseInt(answer) % 10;
            int n = Integer.parseInt(answer)/10;
            if (userAnswers.get(q) == null){
                Set<Integer> set = new HashSet<>();
                set.add(n);
                userAnswers.put(q,set);
            }else {
                userAnswers.get(q).add(n);
            }
        }
        return userAnswers;
    }
}