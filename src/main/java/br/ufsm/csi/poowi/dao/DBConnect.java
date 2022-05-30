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

        this.URL = "jdbc:postgresql://" + dotenv.get("PGHOST") + ":" + dotenv.get("PGPORT") + "/" + dotenv.get("PGDBNAME");
        this.USER = dotenv.get("PGUSER");
        this.PASS = dotenv.get("PGPASS");
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
