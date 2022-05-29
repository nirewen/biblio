package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConnect {
    private static final String DRIVER = "org.postgresql.Driver";
    private String URL;
    private String USER;
    private String PASS;

    public DBConnect() {
        Dotenv dotenv = Dotenv.load();

        this.URL = dotenv.get("POSTGRES_URL");
        this.USER = dotenv.get("POSTGRES_USER");
        this.PASS = dotenv.get("POSTGRES_PASS");
    }

    public Connection getConnection() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            
            con = DriverManager.getConnection(this.URL, this.USER, this.PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
