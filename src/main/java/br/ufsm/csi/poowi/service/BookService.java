package br.ufsm.csi.poowi.service;

import java.util.List;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.model.Book;

public class BookService {
    private final BookDAO dao = new BookDAO();

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
