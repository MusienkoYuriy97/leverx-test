package com.test.servlet;

import com.test.exception.InputException;
import com.test.service.QuestionService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/input", name = "InputQuestionServlet")
public class InputQuestionServlet extends HttpServlet {
    QuestionService service = new QuestionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/pages/input.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String a = req.getParameter("a");
        String b = req.getParameter("b");
        String c = req.getParameter("c");
        String d = req.getParameter("d");
        String rightAnswer = req.getParameter("answerNumbers");

        try {
            service.save(title,a,b,c,d,rightAnswer);
            req.setAttribute("message","Вопрос успешно добавлен");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp.sendRedirect("/");
        } catch (InputException e) {
            req.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/pages/input.jsp").forward(req,resp);
        }
    }
}
