package org.example.controller;

import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.BookRepository;
import org.example.view.BookView;

import java.util.List;


public class BookController {

    private final BookRepository bookRepository;
    private final BookView bookView = new BookView();

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getListaLibros() {
        return bookRepository.getListaLibros();
    }

    public void terminateBook()//dar de baja o alta un libro
    {
        Integer idBuscar = bookView.pedirEntero("Ingresar id del libro: ");

        Book book = bookRepository.searchLibroId(idBuscar);
        bookRepository.delete(book);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Metodo que llama un usuario para asignarle puntaje a un libro que devuelve.
    public void addRating(Book book, double rating) {
        // Actualiza la suma total de puntuaciones
        double newSumRatings = book.getSumRatings() + rating;
        book.setSumRatings(newSumRatings);

        // Incrementar el total de puntuaciones recibidas
        int newTotalRatings = book.getTotalRatings() + 1;
        book.setTotalRatings(newTotalRatings);

        // Calcula y actualiza el promedio de puntuaciones
        double newRate = newSumRatings / newTotalRatings;
        book.setRate(newRate);
        bookRepository.saveBooks();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    public Book searchBookId() {
        Integer idBuscar = bookView.pedirEntero("Ingresar id del libro: ");

        return bookRepository.searchLibroId(idBuscar);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 1 del inicio del Administrador.
    public void creatBook() {
        Book newBook = bookView.creatBook();
        bookRepository.create(newBook);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 2 del inicio del Administrador.
    public void editBook() {
        Book bookEdit = searchBookId();
        if (bookEdit != null) {
            boolean exit = true;
            while (exit) {
                exit = bookView.editarLibro(bookEdit);
            }
            bookRepository.update(bookEdit);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 3 del inicio del Administrador.
    public void toListBooks() {
        List<Book> bookList = getListaLibros();
        if (bookList.isEmpty()) {
            throw new MisExcepciones("No existen libros");
        } else {
            bookView.viewBooks(bookList);
        }

    }

    public void viewBook(Book book) {//Muestra un solo libro

        bookView.displayInfo(book);

    }

    public void viewBooks(List<Book> books) {//Muestra una coleccion de libros

        bookView.viewBooks(books);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Metodo que usa el usuario para solicitar un libro
    public boolean checkStock(Book Book) {
        return Book.getStock() > 0;
    }

    public void loadBooks() {
        bookRepository.loadBooks();

    }

    public void saveBooks() {
        bookRepository.saveBooks();
    }
}