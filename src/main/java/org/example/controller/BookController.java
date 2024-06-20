package org.example.controller;

import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.BookRepository;
import org.example.view.BookView;

import java.util.List;


public class BookController {

    BookRepository bookRepository = new BookRepository();
    BookView bookView = new BookView();


    public void toListBooks()
    {
        List<Book> bookList = this.bookRepository.getListaLibros();
        if(bookList.isEmpty())
        {
            throw new MisExcepciones("No existen libros");
        }
        else
        {
            bookView.viewBooks(bookList);
        }

    }

    public void creatBook()
    {
        Book newBook = bookView.creatBook();
        bookRepository.addList(newBook);
    }

    public void viewBook(Book book){

        BookView.viewBook(book);

    }

    public void viewBooks(List<Book> books){

        BookView.viewBooks(books);

    }

    public boolean checkStock(Book Book)
    {
        if (Book.getStock() > 0)
        {
            return true;
        }
        else
        {
            MisExcepciones.libroSinStock();
        }
        return false;
    }
}
