package org.example;

import org.example.menu.MenuMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private final MenuMain menuMain;

    public MainFrame(MenuMain menuMain) {
        this.menuMain = menuMain;
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JButton loginButton = new JButton("Loguearse");
        loginButton.setBounds(130, 30, 140, 30);
        panel.add(loginButton);

        JButton adminLoginButton = new JButton("Loguearse como ADM");
        adminLoginButton.setBounds(130, 70, 140, 30);
        panel.add(adminLoginButton);

        JButton registerButton = new JButton("Registrarse");
        registerButton.setBounds(130, 110, 140, 30);
        panel.add(registerButton);

        JButton exitButton = new JButton("Salir");
        exitButton.setBounds(130, 150, 140, 30);
        panel.add(exitButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new LoginFrame(menuMain, false).setVisible(true);
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new LoginFrame(menuMain, true).setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //new RegisterFrame(menuMain).setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                menuMain.finalizarPrograma();
                System.exit(0);
            }
        });
    }


}
