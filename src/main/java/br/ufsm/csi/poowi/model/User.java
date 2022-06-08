package br.ufsm.csi.poowi.model;

import java.sql.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private Date dateOfBirth;
    private int permission;

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public int getPermission() {
        return this.permission;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setDateOfBirth(Date value) {
        this.dateOfBirth = value;
    }

    public void setPermission(int value) {
        this.permission = value;
    }

}
