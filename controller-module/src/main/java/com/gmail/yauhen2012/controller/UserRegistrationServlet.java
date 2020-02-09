package com.gmail.yauhen2012.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.yauhen2012.controller.validator.Validation;
import com.gmail.yauhen2012.controller.validator.impl.ValidationImpl;
import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.AddUserDTO;

public class UserRegistrationServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private Validation validation = ValidationImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/registration.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        String isValidName = validation.nameValidation(name);
        String isValidPassword = validation.passwordValidation(password);
        String isValidRole = validation.roleValidation(role);

        if (isValidName == null && isValidPassword == null && isValidRole == null) {
            AddUserDTO addUserDTO = new AddUserDTO();
            addUserDTO.setUserName(name);
            addUserDTO.setPassword(password);
            addUserDTO.setRole(role);

            userService.add(addUserDTO);
            String path = req.getContextPath() + "/successfullyAdded.jsp";
            resp.sendRedirect(path);
        } else {
            req.setAttribute("isValidName", isValidName);
            req.setAttribute("isValidPassword", isValidPassword);
            req.setAttribute("isValidRole", isValidRole);

            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/registration.jsp");
            dispatcher.forward(req, resp);
        }

    }

}

