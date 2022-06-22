package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufsm.csi.poowi.model.Rent;
import br.ufsm.csi.poowi.util.DBConnect;

public class RentDAO {
    private final BookDAO bookDAO = new BookDAO();

    public Rent fromResultSet(ResultSet resultSet) throws SQLException {
        Rent rent = new Rent();

        rent.setId(resultSet.getInt("id"));
        rent.setUser(resultSet.getInt("user_id"));
        rent.setBook(bookDAO.getBook(resultSet.getInt("book_id")));
        rent.setDate(resultSet.getDate("date"));
        rent.setDevolutionDate(resultSet.getDate("devolution_date"));
        rent.setActive(resultSet.getBoolean("active"));

        return rent;
    }

    public Rent getRent(int id) {
        Rent rent = null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM rentals WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rent = this.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rent;
    }

    public boolean createRent(Rent rent) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "INSERT INTO rentals (user_id, book_id, date, devolution_date) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, rent.getUser());
            preparedStatement.setInt(2, rent.getBook().getId());
            preparedStatement.setDate(3, rent.getDate());
            preparedStatement.setDate(4, rent.getDevolutionDate());

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

    public boolean editRent(Rent rent) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "UPDATE rentals SET devolution_date = ? WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, rent.getDate());
            preparedStatement.setInt(2, rent.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteRent(int id) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "DELETE FROM rentals WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Rent> getRentals(int user) {
        List<Rent> rentals = new ArrayList<>();

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM rentals LEFT JOIN books ON rentals.book_id = books.id WHERE user_id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentals.add(this.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentals;
    }
}
