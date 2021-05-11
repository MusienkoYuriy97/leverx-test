package com.test.servlet;

import com.test.service.QuestionService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/test", name = "TestServlet")
public class TestServlet extends HomeServlet{
    QuestionService service = new QuestionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("que", service.getAll());
        String[] as = req.getParameterValues("checked");
        req.setAttribute("message", service.checkTest(as));
        getServletContext().getRequestDispatcher("/pages/test.jsp").forward(req,resp);
    }
}
