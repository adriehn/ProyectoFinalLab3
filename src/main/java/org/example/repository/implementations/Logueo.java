package org.example.repository.implementations;

public interface Logueo<T> {
    void Register(T t);

    T findUser(T t);


}
