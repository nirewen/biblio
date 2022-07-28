package br.ufsm.csi.poowi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.model.Book;

@Service
public class BookService {
    @Autowired
    private BookDAO dao;

    public Book getBook(int id) {
        return dao.getBook(id);
    }

    public boolean createBook(Book book) {
        return dao.createBook(book);
    }

    public boolean updateBook(int id, Book newBook) {
        return dao.updateBook(id, newBook);
    }

    public boolean deleteBook(int id) {
        return dao.deleteBook(id);
    }

    public List<Book> getBookList() {
        return dao.getBookList();
    }
}
