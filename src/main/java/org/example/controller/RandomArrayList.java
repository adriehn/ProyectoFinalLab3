package org.example.controller;

import org.example.entity.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomArrayList {
    private ArrayList<Book> books;
    private Set<Integer> visitedIndices; //al ser un Set , evitamos indices duplicados
    private Random random;
    private int visitCount;

    public RandomArrayList(ArrayList<Book> books) {
        this.books = books;
        this.visitedIndices = new HashSet<>();
        this.random = new Random();
        this.visitCount = 0;
    }

    public Book getNextRandomBook() {
        ///Metodo para obtener un indice aleatorio que no este repetido
        //ese indice lo utiliza para devolver un libro

        if (visitCount == books.size()) {//si se visitaron todos los indices se reinicia, para continuar la ejecucion
            visitedIndices.clear();
            visitCount = 0;
        }

        int index;
        do {
            index = random.nextInt(books.size());//genera un valor aleatorio entre 0 y el size
        } while (visitedIndices.contains(index)); //Si el indice ya existe en el Set, se repite el bucle

        visitedIndices.add(index);
        visitCount++; //usamos un contador, porque si no se establece un limite, el do-while podria estar infinitamente
                      //generando numeros aleatorios y corroborando.
        return books.get(index);
    }
}