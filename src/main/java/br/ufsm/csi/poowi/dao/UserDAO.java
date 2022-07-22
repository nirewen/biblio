package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.DBConnect;
import br.ufsm.csi.poowi.util.UserException;

public class UserDAO {
    private User fromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setDateOfBirth(resultSet.getDate("date_of_birth"));
        user.setPermission(resultSet.getInt("permission"));

        return user;
    }

    public User getUser(int id) {
        User user = null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = this.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUser(String email) {
        User user = null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = this.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean createUser(String email, String password) throws UserException {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "INSERT INTO users (email, password, permission) VALUES (?, ?, 16)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            // doc: https://www.postgresql.org/docs/14/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                throw new UserException(UserException.Type.DUPLICATE_USER, "Usuário já registrado");
            }
        }

        return false;
    }

    public boolean updateUser(User newUser)
            throws UserException {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "UPDATE users SET email = ?, password = ?, name = ?, date_of_birth = ? WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, newUser.getEmail());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getName());
            preparedStatement.setDate(4, newUser.getDateOfBirth());
            preparedStatement.setInt(5, newUser.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            // doc: https://www.postgresql.org/docs/14/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                throw new UserException(UserException.Type.DUPLICATE_USER, "Um usuário com esse email já existe");
            }
        }

        return false;
    }
}
