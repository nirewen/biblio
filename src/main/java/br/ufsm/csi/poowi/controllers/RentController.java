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

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.dao.RentDAO;
import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Rent;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@WebServlet("/rent")
public class RentController extends HttpServlet {
    private static final BookDAO dao = new BookDAO();
    private static final RentDAO rentDao = new RentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/rent?id=" + id);

            resp.sendRedirect(req.getContextPath() + "/login");

            return;
        }

        Book book = dao.getBook(Integer.parseInt(id));

        if (book == null) {
            // TODO: Redirect to 404
            resp.sendRedirect(req.getContextPath() + "/books");

            return;
        }

        req.setAttribute("book", book);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/new_rental.jsp");

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String id = req.getParameter("id");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/rent?id=" + id);

            resp.sendRedirect(req.getContextPath() + "/login");

            return;
        }

        Book book = dao.getBook(Integer.parseInt(id));

        if (book == null) {
            // TODO: Redirect to 404
            resp.sendRedirect(req.getContextPath() + "/books");

            return;
        }

        Rent rent = new Rent();

        rent.setUser(user.getId());
        rent.setBook(book.getId());
        rent.setDate(new Date(new java.util.Date().getTime()));
        rent.setDevolutionDate(Date.valueOf(req.getParameter("devolution")));

        boolean success = rentDao.createRent(rent);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");

            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/new_rental.jsp");

        rd.forward(req, resp);
    }
}
