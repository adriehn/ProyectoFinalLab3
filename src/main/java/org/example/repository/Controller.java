package org.example.repository;
import java.util.Optional;

public interface Controller<T> {

    Optional<T> login (String dni, String password);
    void inicio (T t);

    void searchLibro(T t);

}
