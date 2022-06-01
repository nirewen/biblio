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
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");

            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/signup.jsp");

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            boolean success = dao.createUser(email, password);

            if (success) {
                req.setAttribute("message", "Usuário criado com sucesso!");

                resp.sendRedirect(req.getContextPath() + "/login");

                return;
            }
        } catch (UserException e) {
            req.setAttribute("error", e);
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/signup.jsp");

        rd.forward(req, resp);
    }
}
