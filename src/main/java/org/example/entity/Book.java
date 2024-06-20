package org.example.entity;

public class Book {
    private static Integer id = 0;
    private String nameBook;
    private String author;
    private String publisher;
    private String genero;
    private String language;
    private String synopsis;
    private Double rate;
    private Integer stock;
    private Integer idBook;
    private Integer sold;

    public static Integer getId() {
        return id;
    }

    public static void setId(Integer id) {
        Book.id = id;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Book(String nameBook, String author, String publisher, String genero, String language, String synopsis, Integer stock, Integer sold) {
        this.nameBook = nameBook;
        this.author = author;
        this.publisher = publisher;
        this.genero = genero;
        this.language = language;
        this.synopsis = synopsis;
        this.rate = 0.0;
        this.stock = stock;
        this.idBook = ++id;
        this.sold = sold;

    }

    @Override
    public String toString() {
        return
                "|Titulo: " + nameBook +"\n"+
                        "| Autor: " + author +"|\n"+"|\n"+
                        "| Sinopsis: " + synopsis +"|\n"+"|\n"+
                        "| Editorial: " + publisher +
                        "| Genero: " + genero +
                        "| Idioma: " + language +"\n"+
                        "| Puntaje: " + rate +
                        "| Stock: " + stock +"\n"+
                        "| ISBN: " + idBook +
                        "| Vendidos: " + sold +
                        "\n"+"\n";
    }
}
