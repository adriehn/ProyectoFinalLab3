package org.example.controller;

import org.example.entity.Admin;
import org.example.entity.Book;
import org.example.entity.Cliente;
import org.example.repository.AdminRepository;
import org.example.view.AdminView;

import java.util.List;
import java.util.Optional;

public class AdminController {


    AdminRepository adminRepository;
    AdminView adminView = new AdminView();
    BookController bookController = new BookController();

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public void inicio(Admin admin) {
        boolean exit = false;

        Integer option = adminView.opcionesAdmin();

        if(!exit) {

            switch (option) {
                case 1 -> bookController.creatBook();//Agregar
                case 2 -> System.out.println();//Editar
                case 3 -> bookController.toListBooks();//Listar
                case 4 -> System.out.println();//Buscar
                case 5 -> System.out.println();//Dar de baja
                case 6 -> exit = true;
                default -> System.out.println("Opción invalida");
            }
        }
    }


    public Optional<Admin> login(String dni, String password) {
        return Optional.ofNullable(adminRepository.findUser(dni))
                .filter(Admin -> Admin.getPassword().equals(password))
                .map(administrativo -> {
                    System.out.println("Ingreso exitoso");
                    return administrativo;
                });
    }

    public void loadAdm()
    {
        adminRepository.loadAdm();
    }
}
