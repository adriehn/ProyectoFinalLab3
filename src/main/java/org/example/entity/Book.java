package org.example.entity;

import java.util.Objects;

public class Book {
    private static Integer id = 0;
    private boolean status = true;
    private String nameBook;
    private String author;
    private String publisher;
    private String genero;
    private String language;
    private String synopsis;
    private Double rate;
    private Integer stock;
    private final Integer idBook;
    private Integer sold;
    private Integer totalRatings; // Total de puntuaciones recibidas
    private Double sumRatings;    // Suma total de todas las puntuaciones asignadas


    public boolean isStatus() {
        return status;
    }

    public void setStatus() {
        this.status = !status;
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



    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public Double getSumRatings() {
        return sumRatings;
    }

    public void setSumRatings(Double sumRatings) {
        this.sumRatings = sumRatings;
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
        this.totalRatings = 0;
        this.sumRatings = 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(idBook, book.idBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook);
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
