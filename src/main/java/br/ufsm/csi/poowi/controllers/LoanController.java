package br.ufsm.csi.poowi.controllers;

import java.sql.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.service.BookService;
import br.ufsm.csi.poowi.service.LoanService;
import br.ufsm.csi.poowi.util.LoanException;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@Controller
@RequestMapping("/loan")
public class LoanController extends HttpServlet {
    @Autowired
    private BookService bookService;
    @Autowired
    private LoanService loanService;

    @GetMapping("/{book_id}")
    protected String loanPage(HttpSession session, Model model, @PathVariable(name = "book_id") String id) {
        User user = (User) session.getAttribute("user");

        if (id == null || id.isEmpty()) {
            return "redirect:/books";
        }

        if (user == null) {
            session.setAttribute("redirectTo", "/loan/" + id);
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            return "redirect:/login";
        }

        Book book = this.bookService.getBook(Integer.parseInt(id));

        if (book == null) {
            // TODO: Redirect to 404
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("loan", new Loan());

        return "new_loan";
    }

    @GetMapping("/{loan_id}/postpone")
    public String postponeLoan(HttpSession session, Model model,
            @PathVariable(value = "loan_id", required = true) String id) {
        Loan loan = this.loanService.getLoan(Integer.parseInt(id));

        if (loan == null) {
            return "redirect:/dashboard";
        }

        Book book = this.bookService.getBook(loan.getBookId());

        model.addAttribute("loan", loan);
        model.addAttribute("book", book);

        return "edit_loan";
    }

    @GetMapping("/{loan_id}/return")
    public String returnBook(HttpSession session, Model model,
            @PathVariable(value = "loan_id", required = true) Integer id) {
        this.loanService.deleteLoan(id);

        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/new")
    protected String newLoan(HttpSession session, Model model, @PathVariable(value = "id") Integer id,
            @ModelAttribute Loan loan) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            String redirectTo = "/loan";

            redirectTo += "/" + id + "/new";

            session.setAttribute("redirectTo", redirectTo);
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            return "redirect:/login";
        }

        Date devolution = loan.getDevolutionDate();

        Book book = this.bookService.getBook(id);

        if (book == null) {
            // TODO: Redirect to 404
            return "redirect:/books";
        }

        loan.setUserId(user.getId());
        loan.setBookId(book.getId());
        loan.setDevolutionDate(devolution);

        boolean success = this.loanService.createLoan(loan);

        if (success) {
            return "redirect:/dashboard";
        } else {
            session.setAttribute("error",
                    new LoanException(LoanException.Type.BOOK_ALREADY_LOANED, "Livro já alugado!"));
        }

        session.removeAttribute("error");

        return "redirect:/loan/" + id;
    }

    @PostMapping("/{loan_id}/postpone")
    public String editLoan(HttpSession session, Model model, @PathVariable(value = "loan_id") String loanId,
            @ModelAttribute Loan loan) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("redirectTo", "/loan" + "/" + loanId + "/postpone");
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            return "redirect:/login";
        }

        boolean success = this.loanService.editLoan(loan);

        if (success) {
            return "redirect:/dashboard";
        }

        return "edit_loan";
    }
}
