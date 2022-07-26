package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.DBConnect;

public class LoanDAO {
    public Loan fromResultSet(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();

        loan.setId(resultSet.getInt("id"));
        loan.setUserId(resultSet.getInt("user_id"));
        loan.setBookId(resultSet.getInt("book_id"));
        loan.setDate(resultSet.getDate("date"));
        loan.setDevolutionDate(resultSet.getDate("devolution_date"));
        loan.setActive(resultSet.getBoolean("active"));

        return loan;
    }

    public Loan getLoan(User user, Book book) {
        Loan loan = null;

        if (user == null)
            return null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM loans WHERE book_id = ? AND user_id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(2, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                loan = this.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    public Loan getLoan(int id) {
        Loan loan = null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM loans WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                loan = this.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    public boolean createLoan(Loan loan) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "INSERT INTO loans (user_id, book_id, date, devolution_date) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, loan.getUserId());
            preparedStatement.setInt(2, loan.getBookId());
            preparedStatement.setDate(3, loan.getDate());
            preparedStatement.setDate(4, loan.getDevolutionDate());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            // doc: https://www.postgresql.org/docs/14/errcodes-appendix.html
            if (!e.getSQLState().equals("23505")) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean editLoan(Loan loan) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "UPDATE loans SET devolution_date = ? WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, loan.getDevolutionDate());
            preparedStatement.setInt(2, loan.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteLoan(int id) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "UPDATE loans SET active = false WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Loan> getLoanList(int user) {
        List<Loan> loans = new ArrayList<>();

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM loans LEFT JOIN books ON loans.book_id = books.id WHERE user_id = ? ORDER BY loans.active DESC, loans.devolution_date DESC;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                loans.add(this.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }
}
