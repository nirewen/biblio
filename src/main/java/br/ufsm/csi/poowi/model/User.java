package br.ufsm.csi.poowi.model;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private Date dateOfBirth;
    
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
    
    public int setId(int value) {
        return this.id = value;
    }

    public String setEmail(String value) {
        return this.email = value;
    }

    public String setPassword(String value) {
        return this.password = value;
    }

    public String setName(String value) {
        return this.name = value;
    }

    public Date setDateOfBirth(Date value) {
        return this.dateOfBirth = value;
    }

}
