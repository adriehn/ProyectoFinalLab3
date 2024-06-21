package org.example.controller;

import org.example.entity.Admin;
import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.AdminRepository;
import org.example.repository.implementations.Controller;
import org.example.view.AdminView;

import java.util.List;
import java.util.Optional;

public class AdminController implements Controller {


    AdminRepository adminRepository;
    AdminView adminView = new AdminView();

    BookController bookController;


    public AdminController(AdminRepository adminRepository, BookController bookController) {
        this.bookController = bookController;
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> login(String dni, String password) {

        ///Si la llamada a findUser devuelve null, "ofNullable" lo encapsula como un "optional" y lo verificamos en el primer if.
        ///Optional es una clase que utiliza genéricos y verifica si tiene o no un valor dentro. Evitando el "NullPointerException".
        Optional<Admin> requestedUser = Optional.ofNullable(adminRepository.findUser(dni));

        if (requestedUser.isEmpty()) {
            return Optional.empty();
        }
        if (requestedUser.get().getPassword().equals(password)) {
            return requestedUser;
        } else {
            return Optional.empty();

        }

    }

    @Override
    public void inicio(Object o) {
        if (o instanceof Admin)
        {
            boolean exit = false;

            while (!exit) {
                Integer option = adminView.opcionesAdmin();

                switch (option) {
                    case 1 -> bookController.creatBook(); // Agregar
                    case 2 -> bookController.editBook(); // Editar
                    case 3 -> toListBook();
                    case 4 -> searchLibro();
                    case 5 -> bookController.terminateBook(); // Dar de baja
                    case 6 -> exit = true;
                    default -> System.out.println("Opción inválida");
                }
            }
        }
    }


    @Override
    public Book searchLibro() {
        Book libro = bookController.searchBookId();

        if (libro != null) {
            bookController.viewBook(libro);
        }
        return libro;
    }

    public void toListBook() {
        try {
            bookController.toListBooks(); // Listar
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
        }
    }


    public void loadAdm() {
        adminRepository.loadAdm();
    }
}