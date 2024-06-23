package org.example.repository;

public interface Logueo<T> {
    void Register(T t);

    T findUser(T t);


}
