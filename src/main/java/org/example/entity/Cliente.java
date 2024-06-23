package org.example.entity;

import java.util.*;

public class Cliente extends Persona {

    private final ArrayList<Book> currentlyBorrowedBook;//
    private final Map<Integer, Book> returnHistory;
    private final Set<Book> listFavBook;
    private final List<Messages> listMessages;


    public Cliente(String dni, String name, String lastName, Integer age, String email, String phone, String address, String password, boolean rol) {
        super(dni, name, lastName, age, email, phone, address, password, rol);
        this.currentlyBorrowedBook = new ArrayList<>();
        this.returnHistory = new HashMap<>();
        this.listFavBook = new HashSet<>();
        this.listMessages = new ArrayList<>();
    }

    public List<Messages> getListMessages() {
        return listMessages;
    }



    public ArrayList<Book> getCurrentlyBorrowedBook() {
        return currentlyBorrowedBook;
    }



    public Map<Integer, Book> getReturnHistory() {
        return returnHistory;
    }


    public Set<Book> getListFavBook() {
        return listFavBook;
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
