package br.ufsm.csi.poowi.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.model.Book;

@WebServlet("/books")
public class BooksController extends HttpServlet {
    private static final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = dao.getBookList();

        req.setAttribute("books", books);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/books.jsp");

        rd.forward(req, resp);
    }
}
