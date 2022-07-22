package br.ufsm.csi.poowi.controllers;

import java.sql.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.dao.LoanDAO;
import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.LoanException;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@Controller
@RequestMapping("/loan")
public class LoanController extends HttpServlet {
    private static final BookDAO bookDAO = new BookDAO();
    private static final LoanDAO loanDAO = new LoanDAO();

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

        Book book = bookDAO.getBook(Integer.parseInt(id));

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
        Loan loan = loanDAO.getLoan(Integer.parseInt(id));

        if (loan == null) {
            return "redirect:/dashboard";
        }

        Book book = bookDAO.getBook(loan.getBookId());

        model.addAttribute("loan", loan);
        model.addAttribute("book", book);

        return "edit_loan";
    }

    @GetMapping("/{loan_id}/return")
    public String returnBook(HttpSession session, Model model,
            @PathVariable(value = "loan_id", required = true) Integer id) {
        loanDAO.deleteLoan(id);

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

        Book book = bookDAO.getBook(id);

        if (book == null) {
            // TODO: Redirect to 404
            return "redirect:/books";
        }

        loan.setUserId(user.getId());
        loan.setBookId(book.getId());
        loan.setDevolutionDate(devolution);

        boolean success = loanDAO.createLoan(loan);

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

        boolean success = loanDAO.editLoan(loan);

        if (success) {
            return "redirect:/dashboard";
        }

        return "edit_loan";
    }
}
