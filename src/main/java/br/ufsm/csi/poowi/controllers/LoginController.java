package br.ufsm.csi.poowi.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.service.UserService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/login.jsp");

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService us = new UserService();

        String path = "/WEB-INF/views/login.jsp";
        User user = us.autenticado(email, password);

        if (user != null) {
            HttpSession session = req.getSession();

            session.setAttribute("user", user);

            path = "/WEB-INF/views/dashboard.jsp";
        } else {
            req.setAttribute("error", "USUÁRIO OU SENHA INCORRETOS");
        }

        RequestDispatcher rd = req.getRequestDispatcher(path);

        rd.forward(req, resp);
    }
}
