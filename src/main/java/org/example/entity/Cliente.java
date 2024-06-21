package org.example.entity;

import java.util.*;

public class Cliente extends Persona {

private boolean userActive;
    private ArrayList<Book> currentlyBorrowedBook;//
    private Map<Integer, Book> returnHistory;
    private Set<Book> listFavBook;
    private List<Messages> listMessages;


    public Cliente(String dni, String name, String lastName, Integer age, String email, String phone, String address, String password, boolean rol) {
        super(dni, name, lastName, age, email, phone, address, password, rol);
        this.currentlyBorrowedBook = new ArrayList<>();
        userActive = true;
        this.returnHistory = new HashMap<>();
        this.listFavBook = new HashSet<>();
        this.listMessages = new ArrayList<>();
    }

    public List<Messages> getListMessages() {
        return listMessages;
    }

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) obj;
        // Aquí puedes comparar los atributos heredados y propios
        return super.equals(obj) && Objects.equals(this.getDni(), other.getDni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDni());
    }
}
