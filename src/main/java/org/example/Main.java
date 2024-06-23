package org.example;
import org.example.controller.AdminController;
import org.example.controller.BookController;
import org.example.entity.Book;
import org.example.menu.MenuMain;
import org.example.repository.AdminRepository;
import org.example.repository.BookRepository;
import org.example.repository.ClienteRepository;

import javax.swing.*;
import java.io.IOException;

import static java.awt.SystemColor.menu;

public class Main {
    private static final MenuMain menu = new MenuMain();

    static {
        menu.cargarJson();
    }
    public static void main(String[] args) throws IOException {

        /////////////////////////////////////////////////////////////////
        ///////////////////////SECTOR PRUEBA
        //////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////




        try {
            menu.mainFlow();
        } finally {
            menu.finalizarPrograma();
        }
    }

}