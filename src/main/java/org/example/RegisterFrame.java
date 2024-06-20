package org.example;

import org.example.menu.MenuMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private final MenuMain menuMain;

    public RegisterFrame(MenuMain menuMain) {
        this.menuMain = menuMain;
        initUI();
    }

    private void initUI() {
        setTitle("Registro Cliente");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        // Add registration fields and buttons here
        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(100, 80, 100, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                menuMain.clienteController.createPersona();
                JOptionPane.showMessageDialog(panel, "Registrado exitosamente!", "Registro", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }
}

