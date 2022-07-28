package br.ufsm.csi.poowi.model;

import java.sql.Date;

public class Loan {
    private int id;
    private int userId;
    private int bookId;
    private User user;
    private Book book;
    private Date date;
    private Date devolutionDate;
    private boolean active;

    public int getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public int getUserId() {
        return this.userId;
    }

    public Book getBook() {
        return this.book;
    }

    public int getBookId() {
        return this.bookId;
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

    public void setUserId(int value) {
        this.userId = value;
    }

    public void setUser(User value) {
        this.user = value;
    }

    public void setBookId(int value) {
        this.bookId = value;
    }

    public void setBook(Book value) {
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
