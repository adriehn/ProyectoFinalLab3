package org.example.repository.implementations;

public interface CRUD<T> {

    T create();

    T read(T t);

    T update(T t);

    void delete(T t);

}
