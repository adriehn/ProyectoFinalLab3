package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.CRUD;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BookRepository implements CRUD {

    private final String BOOK_PATH = "src/main/resources/books.json";

    private Gson gson = new Gson();

    public static List<Book> listaLibros = new ArrayList<>();


    public BookRepository() {
        loadBooks();
    }

    public List<Book> getListaLibros() {
        return listaLibros;
    }

    public void addList(Book libro) {
        listaLibros.add(libro);
        saveBooks();
    }

    public Book searchLibroId(Integer id) {
        for (Book libro : listaLibros) {
            if (libro.getIdBook().equals(id)) {
                System.out.println("libro encontrado");
                return libro;
            }
        }
        return null;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //Implementacion de <Interface> CRUD
    @Override
    public void create(Object o) {
        if (o instanceof Book) {
            addList((Book) o);
        }
    }

    @Override
    public Book read(Object o) {
        if (o instanceof Integer) {
            if ((Integer) o >= listaLibros.size()) {
                return listaLibros.get((Integer) o);
            } else {
                MisExcepciones.libroSinStock();
            }
        }
        return null;
    }

    @Override
    public void update(Object o) {
        if (o instanceof Book) {
            System.out.println("Actualizado Correctamente.");
            saveBooks();
        }
    }

    @Override
    public void delete(Object o) {
        if (o instanceof Book book) {
            book.setStatus();
        }
        saveBooks();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Manejo de Json


    public void loadBooks() {
        try (Reader reader = new FileReader(BOOK_PATH)) {
            Type userListType = new TypeToken<List<Book>>() {
            }.getType();
            listaLibros = gson.fromJson(reader, userListType);
            if (listaLibros == null) {
                listaLibros = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveBooks() {
        try (Writer writer = new FileWriter(BOOK_PATH)) {
            gson.toJson(getListaLibros(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}