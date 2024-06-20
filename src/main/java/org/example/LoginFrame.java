package org.example;

import org.example.entity.Admin;
import org.example.entity.Cliente;
import org.example.menu.MenuMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginFrame extends JFrame {
    private final MenuMain menuMain;
    private final boolean isAdmin;

    public LoginFrame(MenuMain menuMain, boolean isAdmin) {
        this.menuMain = menuMain;
        this.isAdmin = isAdmin;
        initUI();
    }

    private void initUI() {
        setTitle(isAdmin ? "Login Admin" : "Login Cliente");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel dniLabel = new JLabel("DNI:");
        dniLabel.setBounds(10, 20, 80, 25);
        panel.add(dniLabel);

        JTextField dniField = new JTextField(20);
        dniField.setBounds(100, 20, 165, 25);
        panel.add(dniField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String dni = dniField.getText();
                String password = new String(passwordField.getPassword());

                if (isAdmin) {
                    Optional<Admin> admin = menuMain.adminController.login(dni, password);
                    if (admin.isPresent()) {
                        menuMain.adminController.inicio(admin.get());
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Admin no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    Optional<Cliente> cliente = menuMain.clienteController.login(dni, password);
                    if (cliente.isPresent()) {
                        menuMain.clienteController.inicio(cliente.get());
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
