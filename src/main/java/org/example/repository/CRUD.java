package org.example.repository;

public interface CRUD<T> {

    void create(T t);

    T read(T t);

    void update(T t);

    void delete(T t);///Se llama delete para respetas las siglas CRUD, pero en realidad deshabilita

}
