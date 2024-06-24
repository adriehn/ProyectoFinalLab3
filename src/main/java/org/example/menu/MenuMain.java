package org.example.menu;

import org.example.controller.AdminController;
import org.example.controller.BookController;
import org.example.controller.ClienteController;
import org.example.controller.PersonaController;
import org.example.entity.Admin;
import org.example.entity.Cliente;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.AdminRepository;
import org.example.repository.implementations.BookRepository;
import org.example.repository.implementations.ConfigRepository;

import java.util.Optional;
import java.util.Scanner;

public class MenuMain {
    private final Scanner scanner = new Scanner(System.in);
    public final ClienteController clienteController = new ClienteController();

    private final BookRepository bookRepository = new BookRepository();
    private final BookController bookController = new BookController(bookRepository);
    private final AdminRepository adminRepository = new AdminRepository();
    public final AdminController adminController = new AdminController(adminRepository, bookController);
    public final String requestDniMessage = "Ingrese su numero de DNI (sin puntos, ni coma): ";
    public static final String requestPasswordMessage = "Ingrese su contraseña: ";

    private final PersonaController personaController = new PersonaController();

    public MenuMain() {
    }

    public void mainFlow() {///Primer vista de menu que se ejecuta
        boolean exit = false;


        while (!exit) {
            String loginMenu = """
                    Bienvenido!
                    1. Loguearse
                    2. Loguearse como ADM
                    3. Registrarse
                    4. Salir""";
            System.out.print("Ingresar opcion: ");
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

    }

    private void loginFlow() {//Inicio session cliente
        System.out.println(requestDniMessage);
        String userDniInput = scanner.nextLine();
        System.out.println(requestPasswordMessage);
        String passwordInput = scanner.nextLine();

        ///una forma de usar el Optional, si el objeto Optional tiene un objeto Cliente, se jecuta la accion de ir al "inicio"
        ///si no ejecuta la excepcion y el catch la captura
        try {
            Optional<Cliente> user = clienteController.login(userDniInput, passwordInput);
            user.ifPresentOrElse(Cliente -> clienteController.inicio(Cliente), () -> MisExcepciones.usuarioNoEncontrado());
        } catch (MisExcepciones e) {
            System.out.printf(e.getLocalizedMessage());
        }

    }



    private void loginFlowAdm() {
        System.out.println(requestDniMessage);
        String userDniInput = scanner.nextLine();
        System.out.println(requestPasswordMessage);
        String passwordInput = scanner.nextLine();

        //Otra forma de usar Optional, se le consulta a Optional si dentro de su objeto contiene algo de tipo Admin
        ///si el booleano es true, se realizan las acciones del if.
        try {
            Optional<Admin> admin = adminController.login(userDniInput, passwordInput);
            if (admin.isPresent()) {
                Admin a = admin.get();
                adminController.inicio(a);
            } else {
                MisExcepciones.usuarioNoEncontrado();
            }
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerFlow() {
        personaController.createPersona();
    }

    public void finalizarPrograma() {
        bookController.saveBooks();
        clienteController.saveClientes();
        adminController.saveAdm();
        ConfigRepository.saveIDPersonas();
        ConfigRepository.saveIDBook();
    }

    public void cargarJson() {
        bookController.loadBooks();
        clienteController.loadClient();
        adminController.loadAdm();
        ConfigRepository.loadIdPersonas();
        ConfigRepository.loadIdBook();
    }

}