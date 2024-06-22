package org.example.controller;

import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.BookRepository;
import org.example.view.BookView;

import java.util.List;


public class BookController {

    BookRepository bookRepository;
    BookView bookView = new BookView();

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    public void toListBooks() {
        List<Book> bookList = getListaLibros();
        if (bookList.isEmpty()) {
            throw new MisExcepciones("No existen libros");
        } else {
            BookView.viewBooks(bookList);
        }

    }

    public List<Book> getListaLibros() {
        return bookRepository.getListaLibros();
    }

    public void  terminateBook()//dar de baja logica
    {
        Integer idBuscar =  bookView.pedirEntero("Ingresar id del libro: ");

        Book book = bookRepository.searchLibroId(idBuscar);

        book.setStatus();
    }

    public void addRating(Book book, double rating) {
        // Actualizar la suma total de puntuaciones
        double newSumRatings = book.getSumRatings() + rating;
        book.setSumRatings(newSumRatings);

        // Incrementar el total de puntuaciones recibidas
        int newTotalRatings = book.getTotalRatings() + 1;
        book.setTotalRatings(newTotalRatings);

        // Calcular y actualizar el promedio de puntuaciones
        double newRate = newSumRatings / newTotalRatings;
        book.setRate(newRate);
        bookRepository.saveLibros();
    }

    public Book searchBookId()
    {
        Integer idBuscar = bookView.pedirEntero("Ingresar id del libro: ");

        return bookRepository.searchLibroId(idBuscar);
    }

    public void editBook(){
        Book bookEdit = searchBookId();

        bookView.editarLibro(bookEdit);

    }

    public void creatBook() {
        Book newBook = bookView.creatBook();
        bookRepository.addList(newBook);
    }

    public void viewBook(Book book) {

        BookView.viewBook(book);

    }

    public void viewBooks(List<Book> books) {

        BookView.viewBooks(books);

    }

    public boolean checkStock(Book Book) {
        return Book.getStock() > 0;
    }
}