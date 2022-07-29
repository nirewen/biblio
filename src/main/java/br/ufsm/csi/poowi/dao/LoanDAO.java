package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.Loan;
import br.ufsm.csi.poowi.model.User;

@Component
public class LoanDAO {
    private DataSource dataSource;

    public LoanDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static Loan fromResultSet(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();

        loan.setId(resultSet.getInt("id"));
        loan.setUserId(resultSet.getInt("user_id"));
        loan.setBookId(resultSet.getInt("book_id"));
        loan.setDate(resultSet.getDate("date"));
        loan.setDevolutionDate(resultSet.getDate("devolution_date"));
        loan.setActive(resultSet.getBoolean("active"));
        loan.setBook(BookDAO.fromResultSet(resultSet));

        return loan;
    }

    public Loan getLoan(User user, Book book) {
        Loan loan = null;

        if (user == null)
            return null;

        try (Connection con = this.dataSource.getConnection()) {
            String sql = "SELECT * FROM loans WHERE book_id = ? AND user_id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(2, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                loan = fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    public Loan getLoan(int id) {
        Loan loan = null;

        try (Connection con = this.dataSource.getConnection()) {
            String sql = "SELECT * FROM loans WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                loan = fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    public boolean createLoan(Loan loan) {
        try (Connection con = this.dataSource.getConnection()) {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
            MapSqlParameterSource paramSource = new MapSqlParameterSource();

            paramSource.addValue("user_id", loan.getUserId());
            paramSource.addValue("book_id", loan.getBookId());
            paramSource.addValue("date", loan.getDate());
            paramSource.addValue("devolution", loan.getDevolutionDate());

            String sql = "INSERT INTO loans (user_id, book_id, date, devolution_date) VALUES (:user_id, :book_id, :date, :devolution) ON CONFLICT (book_id, user_id) DO UPDATE SET active = true, date = :date, devolution_date = :devolution;";

            jdbcTemplate.queryForRowSet(sql, paramSource);

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
        try (Connection con = this.dataSource.getConnection()) {
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
        try (Connection con = this.dataSource.getConnection()) {
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

        try (Connection con = this.dataSource.getConnection()) {
            String sql = "SELECT * FROM loans LEFT JOIN books ON loans.book_id = books.id WHERE user_id = ? ORDER BY loans.active DESC, loans.devolution_date DESC;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                loans.add(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }
}
