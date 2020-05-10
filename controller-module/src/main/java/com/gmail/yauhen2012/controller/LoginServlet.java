package com.gmail.yauhen2012.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmail.yauhen2012.controller.validator.Validation;
import com.gmail.yauhen2012.controller.validator.impl.ValidationImpl;

public class LoginServlet extends HttpServlet {

    private Validation validation = ValidationImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("username");
        String password = req.getParameter("password");

        String isValidName = validation.nameValidation(name);
        String isValidPassword = validation.passwordValidation(password);

        if (isValidName == null && isValidPassword == null) {
            HttpSession session = req.getSession(true);

            session.setAttribute("sessionUserName", name);
            session.setAttribute("sessionPassword", password);
            String path = req.getContextPath() + "/adminPage";
            resp.sendRedirect(path);
        } else {
            req.setAttribute("isValidName", isValidName);
            req.setAttribute("isValidPassword", isValidPassword);

            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }

    }

}
