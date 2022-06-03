package br.ufsm.csi.poowi.model;

public class Book {
    private int id;
    private String name;
    private String synopsis;
    private int pages;
    private float chapters;
    private String author;
    private String publisher;
    private int year;
    private String cover;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public int getPages() {
        return this.pages;
    }

    public float getChapters() {
        return this.chapters;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public int getYear() {
        return this.year;
    }

    public String getCover() {
        return this.cover;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setSynopsis(String value) {
        this.synopsis = value;
    }

    public void setPages(int value) {
        this.pages = value;
    }

    public void setChapters(float value) {
        this.chapters = value;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public void setPublisher(String value) {
        this.publisher = value;
    }

    public void setYear(int value) {
        this.year = value;
    }

    public void setCover(String value) {
        this.cover = value;
    }

}
