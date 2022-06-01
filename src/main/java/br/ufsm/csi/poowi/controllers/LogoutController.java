package br.ufsm.csi.poowi.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufsm.csi.poowi.dao.UserException;
import br.ufsm.csi.poowi.dao.UserException.Type;
import br.ufsm.csi.poowi.model.User;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            resp.sendRedirect(req.getContextPath() + "/login");

            return;
        }

        session.removeAttribute("user");

        resp.sendRedirect(req.getContextPath());
    }
}
