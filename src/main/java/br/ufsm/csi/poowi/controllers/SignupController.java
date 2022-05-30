package br.ufsm.csi.poowi.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.dao.UserException;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/signup.jsp");

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String path = "/WEB-INF/views/signup.jsp";

        try {
            boolean success = dao.createUser(email, password);

            if (success) {
                path = "/WEB-INF/views/login.jsp";

                req.setAttribute("message", "Usuário criado com sucesso!");
            }
        } catch (UserException e) {
            req.setAttribute("error", e);
        }

        RequestDispatcher rd = req.getRequestDispatcher(path);

        rd.forward(req, resp);
    }
}
