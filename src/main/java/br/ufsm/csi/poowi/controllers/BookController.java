package br.ufsm.csi.poowi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.service.BookService;
import br.ufsm.csi.poowi.service.LoanService;
import br.ufsm.csi.poowi.util.BookException;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@Controller
@RequestMapping("/book")
public class BookController extends HttpServlet {
    private BookService bookService;
    private LoanService loanService;

    public BookController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    private final ArrayList<String> VALID_EXTENSIONS = new ArrayList<String>() {
        {
            add("jpg");
            add("jpeg");
            add("png");
            add("webp");
        }
    };

    @GetMapping("/{id}")
    protected String getBookById(HttpSession session, Model model, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");

        Book book = bookService.getBook(id);

        if (book == null) {
            return "redirect:/books";
        }

        Loan loan = loanService.getLoan(user, book);

        if (loan != null)
            model.addAttribute("loan", loan);

        model.addAttribute("book", book);

        return "book";
    }

    @GetMapping("/new")
    protected String newBookPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/book/new");

            return "redirect:/login";
        }

        model.addAttribute("book", new Book());

        return "new_book";
    }

    @PostMapping("/new")
    protected String newBook(HttpSession session, @RequestParam("coverFile") MultipartFile coverFile,
            @ModelAttribute("book") Book book)
            throws IOException {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/book/new");

            return "redirect:/login";
        }

        String fileName = coverFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (!VALID_EXTENSIONS.contains(ext)) {
            session.setAttribute("error", new BookException(BookException.Type.INVALID_COVER_TYPE,
                    "Tipo de arquivo para capa inválido!"));

            return "redirect:/book/new";
        }

        InputStream coverIS = coverFile.getInputStream();
        byte[] imageBytes = new byte[(int) coverFile.getSize()];

        coverIS.read(imageBytes, 0, imageBytes.length);
        coverIS.close();

        String cover = "data:image/" + ext + ";base64," + Base64.encodeBase64String(imageBytes);

        book.setCover(cover);

        bookService.createBook(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    protected String editBookPage(HttpSession session, Model model, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/book/" + id + "/edit");

            return "redirect:/login";
        }

        Book book = bookService.getBook(id);

        model.addAttribute("book", book);

        return "edit_book";
    }

    @PostMapping("/{id}/edit")
    protected String editBook(HttpSession session, MultipartFile coverFile, @ModelAttribute("book") Book book)
            throws IOException {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/book/" + book.getId() + "/edit");

            return "redirect:/login";
        }

        String fileName = coverFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (!fileName.isEmpty()) {
            if (!VALID_EXTENSIONS.contains(ext)) {
                session.setAttribute("error", new BookException(BookException.Type.INVALID_COVER_TYPE,
                        "Tipo de arquivo para capa inválido!"));

                return "redirect:/book/" + book.getId() + "/edit";
            }

            InputStream coverIS = coverFile.getInputStream();
            byte[] imageBytes = new byte[(int) coverFile.getSize()];

            coverIS.read(imageBytes, 0, imageBytes.length);
            coverIS.close();

            String cover = "data:image/" + ext + ";base64," + Base64.encodeBase64String(imageBytes);

            book.setCover(cover);
        } else {
            Book oldBook = bookService.getBook(book.getId());

            book.setCover(oldBook.getCover());
        }

        bookService.updateBook(book.getId(), book);

        return "redirect:/book/" + book.getId();
    }
}
