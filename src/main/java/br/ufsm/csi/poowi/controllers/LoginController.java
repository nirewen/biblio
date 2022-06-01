package br.ufsm.csi.poowi.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.dao.UserException;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.service.UserService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");

            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/login.jsp");

        rd.forward(req, resp);

        session.removeAttribute("error");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        UserService us = new UserService();

        User user = dao.getUser(email);

        if (us.autenticado(user, password)) {
            HttpSession session = req.getSession();

            session.setAttribute("user", user);

            resp.sendRedirect(req.getContextPath() + "/dashboard");

            return;
        } else {
            UserException error = new UserException(UserException.Type.INCORRECT_CREDENTIALS,
                    "Email ou senha incorretos");
            req.setAttribute("error", error);
            req.setAttribute("email", email);
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/login.jsp");

        rd.forward(req, resp);
    }
}
