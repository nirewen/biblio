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

    public int setId(int value) {
        return this.id = value;
    }

    public String setName(String value) {
        return this.name = value;
    }

    public String setSynopsis(String value) {
        return this.synopsis = value;
    }

    public int setPages(int value) {
        return this.pages = value;
    }

    public float setChapters(float value) {
        return this.chapters = value;
    }

    public String setAuthor(String value) {
        return this.author = value;
    }

    public String setPublisher(String value) {
        return this.publisher = value;
    }

    public int setYear(int value) {
        return this.year = value;
    }

}
