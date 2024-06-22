package org.example.controller;

import org.example.entity.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomArrayListTraversal {
    private ArrayList<Book> books;
    private Set<Integer> visitedIndices;
    private Random random;
    private int visitCount;

    public RandomArrayListTraversal(ArrayList<Book> books) {
        this.books = books;
        this.visitedIndices = new HashSet<>();
        this.random = new Random();
        this.visitCount = 0;
    }

    public Book getNextRandomBook() {
        if (visitCount == books.size()) {
            // All elements have been visited, reset
            visitedIndices.clear();
            visitCount = 0;
        }

        int index;
        do {
            index = random.nextInt(books.size());
        } while (visitedIndices.contains(index));

        visitedIndices.add(index);
        visitCount++;
        return books.get(index);
    }
}