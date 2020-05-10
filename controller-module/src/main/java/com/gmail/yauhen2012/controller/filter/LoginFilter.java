package com.gmail.yauhen2012.controller.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.UserDTO;

import static com.gmail.yauhen2012.controller.constant.ServletConstant.ADMIN_ROLE;
import static com.gmail.yauhen2012.controller.constant.ServletConstant.USER_ROLE;

public class LoginFilter implements Filter {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String userName = (String) session.getAttribute("sessionUserName");
        String password = (String) session.getAttribute("sessionPassword");

        Optional<UserDTO> userDTO = userService.findAll()
                .stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst();

        if (userDTO.isPresent() && password.equals(userDTO.get().getPassword())) {
            String role = userDTO.get().getRole();
            if (role.equals(USER_ROLE)) {
                session.setAttribute("role", USER_ROLE);
                String path = req.getContextPath() + "/userPage";
                response.sendRedirect(path);
            }
            if (role.equals(ADMIN_ROLE)) {
                session.setAttribute("role", ADMIN_ROLE);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            session.invalidate();
            String path = req.getContextPath() + "/login";
            response.sendRedirect(path);
        }

    }

    @Override
    public void destroy() {
    }

}
