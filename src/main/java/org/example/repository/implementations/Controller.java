package org.example.repository.implementations;
import java.util.Optional;

public interface Controller<T> {

    Optional<T> login (String dni, String password);
    void inicio (T t);

    T searchLibro();

}
