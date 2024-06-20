package org.example.entity;

import java.util.*;

public class Cliente extends Persona {


    private ArrayList<Book> currentlyBorrowedBook;//
    private Map<Integer, Book> returnHistory;
    private Set<Book> listFavBook;

    public Cliente(String dni, String name, String lastName, Integer age, String email, String phone, String address, String password, boolean rol) {
        super(dni, name, lastName, age, email, phone, address, password, rol);
        this.currentlyBorrowedBook = new ArrayList<>();
        this.returnHistory = new HashMap();
        this.listFavBook = new HashSet<>();
    }

    public ArrayList<Book> getCurrentlyBorrowedBook() {
        return currentlyBorrowedBook;
    }

    public void setCurrentlyBorrowedBook(ArrayList<Book> currentlyBorrowedBook) {
        this.currentlyBorrowedBook = currentlyBorrowedBook;
    }

    public Map<Integer, Book> getReturnHistory() {
        return returnHistory;
    }

    public void setReturnHistory(Map<Integer, Book> returnHistory) {
        this.returnHistory = returnHistory;
    }

    public Set<Book> getListFavBook() {
        return listFavBook;
    }

    public void setListFavBook(Set<Book> listFavBook) {
        this.listFavBook = listFavBook;
    }

    @Override
    public String toString() {
        return        super.toString()+
                "cliente{" +
                "currentlyBorrowedBook=" + currentlyBorrowedBook +
                ", returnHistory=" + returnHistory +
                ", listFavBook=" + listFavBook +
                '}';
    }
}
