package org.example.menu;

import org.example.controller.AdminController;
import org.example.controller.BookController;
import org.example.controller.ClienteController;
import org.example.entity.Admin;
import org.example.entity.Cliente;
import org.example.exception.MisExcepciones;
import org.example.repository.AdminRepository;
import org.example.repository.BookRepository;
import org.example.repository.ClienteRepository;

import java.util.Optional;
import java.util.Scanner;

public class MenuMain {
    private final Scanner scanner = new Scanner(System.in);
    public final ClienteController clienteController = new ClienteController();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    private final BookRepository bookRepository = new BookRepository();
    private final BookController bookController = new BookController(bookRepository);
    public AdminRepository adminRepository = new AdminRepository();
    public AdminController adminController = new AdminController(adminRepository,bookController);
    public final String requestDniMessage = "Ingrese su numero de DNI (sin puntos, ni coma): ";
    public static final String requestPasswordMessage = "Ingrese su contraseña: ";
    public MenuMain(AdminController adminController)
    {
        this.adminController = adminController;
        cargarJson();
    }


    public MenuMain() {
    }

    public void mainFlow() {
        boolean exit = false;

        cargarJson();

        while (!exit) {
            String loginMenu = """
                    Bienvenido!
                    1. Loguearse
                    2. Loguearse como ADM
                    3. Registrarse
                    4. Salir
                    """;
            System.out.println(loginMenu);
            int menuOption = scanner.nextInt();
            scanner.nextLine();

            switch (menuOption) {
                case 1 -> loginFlow();
                case 2 -> loginFlowAdm();
                case 3 -> registerFlow();
                case 4 -> exit = true;
                default -> System.out.println("Opción invalida");
            }
        }

        finalizarPrograma();
    }
    private void loginFlow() {//Inicio session cliente
        System.out.println(requestDniMessage);
        String userDniInput = scanner.nextLine();
        System.out.println(requestPasswordMessage);
        String passwordInput = scanner.nextLine();

        try{
            Optional<Cliente> user = clienteController.login(userDniInput, passwordInput);
            user.ifPresentOrElse(Cliente -> clienteController.inicio(Cliente),()->MisExcepciones.usuarioNoEncontrado());
        }
        catch (MisExcepciones e)
        {
            System.out.printf(e.getLocalizedMessage() );
        }

    }

    private void loginFlowAdm() {
        System.out.println(requestDniMessage);
        String userDniInput = scanner.nextLine();
        System.out.println(requestPasswordMessage);
        String passwordInput = scanner.nextLine();

        try {
            Optional<Admin> admin = adminController.login(userDniInput, passwordInput);
            if (admin.isPresent()) {
                Admin a = admin.get();
                adminController.inicio(a);
            } else {
                throw new MisExcepciones("Usuario no encontrado");
            }
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerFlow() {

        clienteController.createPersona();

    }

    public void finalizarPrograma() {
        bookRepository.saveLibros();
        clienteRepository.saveClientes();
        adminRepository.saveAdm();
    }

    public void cargarJson() {
        bookRepository.loadLibros();
        clienteRepository.loadClientes();
        adminController.loadAdm();
    }

}