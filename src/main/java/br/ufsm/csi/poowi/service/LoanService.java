package br.ufsm.csi.poowi.service;

import java.util.List;

import br.ufsm.csi.poowi.dao.LoanDAO;
import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;

public class LoanService {
    private LoanDAO dao = new LoanDAO();

    public Loan getLoan(User user, Book book) {
        return dao.getLoan(user, book);
    }

    public Loan getLoan(int id) {
        return dao.getLoan(id);
    }

    public boolean createLoan(Loan loan) {
        return dao.createLoan(loan);
    }

    public boolean editLoan(Loan loan) {
        return dao.editLoan(loan);
    }

    public boolean deleteLoan(int id) {
        return dao.deleteLoan(id);
    }

    public List<Loan> getLoanList(int user) {
        return dao.getLoanList(user);
    }
}
