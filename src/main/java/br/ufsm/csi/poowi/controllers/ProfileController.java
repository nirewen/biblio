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

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            String redirectTo = "/profile";

            if (req.getParameter("edit") != null)
                redirectTo += "?edit";

            session.setAttribute("redirectTo", redirectTo);

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (req.getParameter("edit") != null)
            req.setAttribute("edit", true);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/profile.jsp");

        rd.forward(req, resp);

        session.removeAttribute("message");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/profile");

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String email = StringUtils.defaultIfEmpty(req.getParameter("email"), user.getEmail());
        String password = StringUtils.defaultIfEmpty(req.getParameter("new_password"), user.getPassword());
        String currentPassword = req.getParameter("password");
        String name = StringUtils.defaultIfEmpty(req.getParameter("name"), user.getName());
        Date dateOfBirth = user.getDateOfBirth();

        if (req.getParameter("date_of_birth") != null && !req.getParameter("date_of_birth").isEmpty()) {
            String dateOfBirthStr = req.getParameter("date_of_birth");

            dateOfBirth = Date.valueOf(dateOfBirthStr);
        }

        if (!user.getPassword().equals(currentPassword)) {
            req.setAttribute("error", new UserException(Type.INCORRECT_CREDENTIALS,
                    "Senha atual incorreta"));

            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/profile.jsp");

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

            session.setAttribute("user", user);
            session.setAttribute("message", "Usuário atualizado");

            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (UserException e) {
            req.setAttribute("error", e);
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/profile.jsp");

        req.setAttribute("edit", true);

        rd.forward(req, resp);
    }
}
