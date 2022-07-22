package br.ufsm.csi.poowi.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.model.Book;

@Controller
@RequestMapping("/books")
public class BooksController {
    // TODO: use service
    private static final BookDAO dao = new BookDAO();

    @GetMapping
    protected String booksPage(Model model) {
        List<Book> books = dao.getBookList();

        model.addAttribute("books", books);

        return "books";
    }
}
