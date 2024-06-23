package org.example.repository.implementations;

import org.example.entity.Admin;
import org.example.exception.MisExcepciones;

public interface View<T> {

    T pedirEntero(T t);
    T pedirDouble(T t);
    T pedirDato(T t)throws MisExcepciones;
     void displayInfo(T t) ;

    T modifyObject (T t);


    }

