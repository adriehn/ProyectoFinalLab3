package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Book;
import org.example.entity.Persona;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.CRUD;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BookRepository implements CRUD {

    private final String BOOK_PATH = "src/main/resources/books.json";
    private final String PATH_FILE_IDBOOK = "src/main/resources/idBooks.json";

    Gson gson = new Gson();

    public static List<Book> listaLibros = new ArrayList<>();


    public BookRepository() {
        loadLibros();
    }

    public List<Book> getListaLibros() {
        return listaLibros;
    }

    public void setListaLibros(List<Book> listaLibros) {
        this.listaLibros = listaLibros;
    }

    public void addList(Book libro)
    {
        listaLibros.add(libro);
        saveLibros();
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

    @Override
    public Object create() {
        return null;
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
    public Object update(Object o) {
        return null;
    }

    @Override
    public void delete(Object o) {

    }

    public void loadLibros() {
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

    public void saveLibros() {
        try (Writer writer = new FileWriter(BOOK_PATH)) {
            gson.toJson(getListaLibros(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void cargarIdBook() {
        try (Reader reader = new FileReader(PATH_FILE_IDBOOK)) {
            Type typelist = new TypeToken<Integer>() {
            }.getType();
            Book.setId(gson.fromJson(reader, typelist));
            if(Book.getId() == null)
            {
                Book.setId(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.fillInStackTrace());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarIDBook() {

        try (Writer writer = new FileWriter(PATH_FILE_IDBOOK)) {
            gson.toJson((Integer) Book.getId(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}