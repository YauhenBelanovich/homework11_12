package com.gmail.yauhen2012.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.yauhen2012.service.RoleService;
import com.gmail.yauhen2012.service.impl.RoleServiceImpl;
import com.gmail.yauhen2012.service.model.RoleDTO;

public class AdminPageServlet extends HttpServlet {

    private RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<RoleDTO> roleDTOList = roleService.findAll();
        req.setAttribute("roleDtoList", roleDTOList);

        ServletContext context = getServletContext();
        RequestDispatcher requestDispatcher = context.getRequestDispatcher("/WEB-INF/pages/adminPage.jsp");
        requestDispatcher.forward(req, resp);

    }

}
