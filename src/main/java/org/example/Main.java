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

public class Main {
    //private static final MenuMain menu = new MenuMain();

    public static void main(String[] args) throws IOException {

        /////////////////////////////////////////////////////////////////
        ///////////////////////SECTOR PRUEBA
        //////////////////////////////////////////////////////////////////
        AdminRepository adminRepository = new AdminRepository();
        AdminController adminController =  new AdminController(adminRepository);
        MenuMain menuMain = new MenuMain(adminController);
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(menuMain);
            mainFrame.setVisible(true);
        });


        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////


        //menu.mainFlow();

    }

}