package br.ufsm.csi.poowi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsm.csi.poowi.dao.LoanDAO;
import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;

@Service
public class LoanService {
    @Autowired
    private LoanDAO dao;

    public Loan getLoan(User user, Book book) {
        return dao.getLoan(user, book);
    }

    public Loan getLoan(int id) {
        Loan loan = dao.getLoan(id);

        return loan;
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
        List<Loan> loans = dao.getLoanList(user);

        return loans;
    }
}
