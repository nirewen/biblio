package br.ufsm.csi.poowi.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.service.BookService;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final BookService bookService = new BookService();

    @GetMapping
    protected String booksPage(Model model) {
        List<Book> books = bookService.getBookList();

        model.addAttribute("books", books);

        return "books";
    }
}
