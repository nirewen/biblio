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
import br.ufsm.csi.poowi.dao.LoanDAO;
import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.LoanException;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@WebServlet("/loan")
public class LoanController extends HttpServlet {
    private static final BookDAO bookDAO = new BookDAO();
    private static final LoanDAO loanDAO = new LoanDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String id = req.getParameter("id");
        String postpone = req.getParameter("postpone");
        String returnBook = req.getParameter("return");

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        if (user == null) {
            String redirectTo = "/loan?";

            if (postpone != null)
                redirectTo += "postpone";

            redirectTo += "&" + id;

            session.setAttribute("redirectTo", redirectTo);
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            resp.sendRedirect(req.getContextPath() + "/login");

            return;
        }

        String route = "/WEB-INF/views/new_loan.jsp";

        Book book = bookDAO.getBook(Integer.parseInt(id));

        if (postpone != null) {
            route = "/WEB-INF/views/edit_loan.jsp";

            Loan loan = loanDAO.getLoan(Integer.parseInt(id));

            if (loan == null) {
                resp.sendRedirect(req.getContextPath() + "/dashboard");

                return;
            }

            book = loan.getBook();

            req.setAttribute("loan", loan);
        }

        if (returnBook != null) {
            loanDAO.deleteLoan(Integer.parseInt(id));

            resp.sendRedirect(req.getContextPath() + "/dashboard");

            return;
        }

        if (book == null) {
            // TODO: Redirect to 404
            resp.sendRedirect(req.getContextPath() + "/books");

            return;
        }

        req.setAttribute("book", book);

        RequestDispatcher rd = req.getRequestDispatcher(route);

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String id = req.getParameter("id");
        String loanId = req.getParameter("loan_id");
        String edit = req.getParameter("edit");
        String devolution = req.getParameter("devolution");

        if (user == null) {
            String redirectTo = "/loan?";

            if (edit != null)
                redirectTo += "edit";

            redirectTo += "&" + id;

            session.setAttribute("redirectTo", redirectTo);
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            resp.sendRedirect(req.getContextPath() + "/login");

            return;
        }

        Book book = bookDAO.getBook(Integer.parseInt(id));

        if (book == null) {
            // TODO: Redirect to 404
            resp.sendRedirect(req.getContextPath() + "/books");

            return;
        }

        Loan loan = null;

        if (loanId != null)
            loan = loanDAO.getLoan(Integer.parseInt(loanId));

        if (loan == null) {
            loan = new Loan();

            loan.setUser(user.getId());
            loan.setBook(book);
            loan.setDate(new Date(new java.util.Date().getTime()));
        }

        loan.setDevolutionDate(Date.valueOf(devolution));

        if (edit != null) {
            boolean success = loanDAO.editLoan(loan);

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/dashboard");

                return;
            }
        } else {
            boolean success = loanDAO.createLoan(loan);

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/dashboard");

                return;
            } else {
                session.setAttribute("error",
                        new LoanException(LoanException.Type.BOOK_ALREADY_LOANED, "Livro já alugado!"));
            }
        }

        req.setAttribute("book", book);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/new_loan.jsp");

        rd.forward(req, resp);
        session.removeAttribute("error");
    }
}
