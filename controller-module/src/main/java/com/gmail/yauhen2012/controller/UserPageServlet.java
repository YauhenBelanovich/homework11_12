package com.gmail.yauhen2012.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.UserDTO;

import static com.gmail.yauhen2012.controller.constant.ServletConstant.USER_ROLE;

public class UserPageServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String role = (String) session.getAttribute("role");
        if (role == USER_ROLE) {
            List<UserDTO> userDTOList = userService.findAll();
            req.setAttribute("userDtoList", userDTOList);

            ServletContext context = getServletContext();
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/WEB-INF/pages/userPage.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            String path = req.getContextPath() + "/adminPage";
            resp.sendRedirect(path);
        }
    }

}
