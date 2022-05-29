package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufsm.csi.poowi.model.User;

public class UserDAO {
    public User getUser(String email) {
        User user = null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                user.setPermission(resultSet.getInt("permission"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
