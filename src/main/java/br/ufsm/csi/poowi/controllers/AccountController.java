package br.ufsm.csi.poowi.controllers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@WebServlet("/account")
public class AccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/account");

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/account.jsp");

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/account");

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String email = StringUtils.defaultIfEmpty(req.getParameter("email"), user.getEmail());
        String password = StringUtils.defaultIfEmpty(req.getParameter("new_password"), user.getPassword());
        String currentPassword = req.getParameter("password");
        String name = StringUtils.defaultIfEmpty(req.getParameter("name"), user.getName());
        String dateOfBirthStr = StringUtils.defaultIfEmpty(req.getParameter("date_of_birth"),
                user.getDateOfBirth().toString());

        Date dateOfBirth = Date.valueOf(dateOfBirthStr);

        if (!user.getPassword().equals(currentPassword)) {
            req.setAttribute("error", new UserException(Type.INCORRECT_CREDENTIALS,
                    "Senha atual incorreta"));

            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/account.jsp");

            rd.forward(req, resp);

            return;
        }

        UserDAO dao = new UserDAO();

        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);

        try {
            dao.updateUser(user);

            req.setAttribute("message", "Usuário atualizado");
            req.setAttribute("user", user);
        } catch (UserException e) {
            req.setAttribute("error", e);
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/account.jsp");

        rd.forward(req, resp);
    }
}
