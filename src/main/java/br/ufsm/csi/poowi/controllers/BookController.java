package br.ufsm.csi.poowi.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@WebServlet("/book")
public class BookController extends HttpServlet {
    private final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        String id = req.getParameter("id");

        String route = "/WEB-INF/views/book.jsp";

        if (option != null && !option.isEmpty()) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

                String redirectTo = "/book?";

                if (id != null && !id.isEmpty())
                    redirectTo += "id=" + id + "&";

                redirectTo += "option=" + option;

                session.setAttribute("redirectTo", redirectTo);

                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            if (option.equals("edit")) {
                route = "/WEB-INF/views/edit_book.jsp";

                if (id == null || id.isEmpty()) {
                    // redirectionar para a lista caso id ausente
                    resp.sendRedirect(req.getContextPath() + "/books");

                    return;
                }
            }

            if (option.equals("new"))
                route = "/WEB-INF/views/new_book.jsp";
        } else if (id == null || id.isEmpty()) {
            // redirectionar para a lista caso id ausente
            resp.sendRedirect(req.getContextPath() + "/books");

            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(route);

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

        String option = req.getParameter("option");

        String name = req.getParameter("name");
        String author = req.getParameter("author");

        if (option.equals("add")) {
            boolean success = dao.createBook(name, author);

            if (success) {
                req.setAttribute("message", "Livro criado com sucesso!");

                resp.sendRedirect(req.getContextPath() + "/books");

                return;
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/book.jsp");

        rd.forward(req, resp);
    }
}
