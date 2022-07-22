package br.ufsm.csi.poowi.model;

import java.sql.Date;

public class Loan {
    private int id;
    private int user;
    private int book;
    private Date date;
    private Date devolutionDate;
    private boolean active;

    public int getId() {
        return this.id;
    }

    public int getUser() {
        return this.user;
    }

    public int getBook() {
        return this.book;
    }

    public Date getDate() {
        return this.date;
    }

    public Date getDevolutionDate() {
        return this.devolutionDate;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setUser(int value) {
        this.user = value;
    }

    public void setBook(int value) {
        this.book = value;
    }

    public void setDate(Date value) {
        this.date = value;
    }

    public void setDevolutionDate(Date value) {
        this.devolutionDate = value;
    }

    public void setActive(boolean value) {
        this.active = value;
    }
}
